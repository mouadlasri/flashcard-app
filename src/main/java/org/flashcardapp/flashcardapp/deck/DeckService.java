package org.flashcardapp.flashcardapp.deck;

import org.flashcardapp.flashcardapp.deck.dto.CreateDeckRequest;
import org.flashcardapp.flashcardapp.user.User;
import org.flashcardapp.flashcardapp.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {
    private final Long USER_ID_TEST = 1L;
    private DeckRepository deckRepository;
    private UserRepository userRepository;

    public DeckService(DeckRepository deckRepository, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public List<Deck> getAllDecksForCurrentUser() {
        Optional<User> optionalUser = userRepository.findById(USER_ID_TEST);

        if(!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + USER_ID_TEST);
        }

        return deckRepository.findByUser_Id(optionalUser.get().getId());
    }

    public Deck getDeckById(@PathVariable Long id) {
        Optional<Deck> optionalDeck = deckRepository.findById(id);

        if (!optionalDeck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + id);
        }

        return optionalDeck.get();
    }

    @Transactional
    public Deck createDeck(CreateDeckRequest request) {
        User optionalUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + request.getUserId()));

        Deck deck = new Deck();
        deck.setName(request.getName());
        deck.setUser(optionalUser);

        return deckRepository.save(deck);
    }

    @Transactional
    public Deck updateDeck(Long id, CreateDeckRequest request) {
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + id));

        deck.setName(request.getName());

        return deckRepository.save(deck);
    }

    public void deleteDeck(Long id) {
        if (!deckRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + id);
        }
        deckRepository.deleteById(id);
    }
}
