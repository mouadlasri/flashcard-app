package org.flashcardapp.flashcardapp.flashcard;

import org.flashcardapp.flashcardapp.deck.Deck;
import org.flashcardapp.flashcardapp.deck.DeckRepository;
import org.flashcardapp.flashcardapp.flashcard.dto.CreateFlashcardRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    private FlashcardRepository flashcardRepository;
    private DeckRepository deckRepository;

    public FlashcardService(FlashcardRepository flashcardRepository, DeckRepository deckRepository) {
        this.flashcardRepository = flashcardRepository;
        this.deckRepository = deckRepository;
    }

    public List<Flashcard> getFlashcardsByDeckId(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + deckId));


        List<Flashcard> flashcards = flashcardRepository.findByDeck_Id(deckId);

        return flashcards;
    }

    public Flashcard getFlashcardById(Long deckId, Long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found with ID: " + id));

        if (!flashcard.getDeck().getId().equals(deckId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flashcard does not belong to this deck");
        }
        return flashcard;
    }

    @Transactional
    public Flashcard createFlashcard(Long deckId, CreateFlashcardRequest request) {
        Deck deck = deckRepository.findById(deckId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + deckId));

        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(request.getQuestion());
        flashcard.setAnswer(request.getAnswer());
        flashcard.setDeck(deck);

        return flashcardRepository.save(flashcard);
    }

    @Transactional
    public Flashcard updateFlashcard(Long deckId, Long id, CreateFlashcardRequest request) {
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found with ID: " + id));

        // validate that the flashcard belongs to the correct deck
        if (!flashcard.getDeck().getId().equals(deckId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flashcard does not belong to this deck");
        }

        flashcard.setQuestion(request.getQuestion());
        flashcard.setAnswer(request.getAnswer());

        return flashcardRepository.save(flashcard);
    }

    public void deleteFlashcard(Long deckId, Long id) {
        Flashcard existingFlashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found with ID: " + id));

        // validate that flashcard belongs to the correct deck
        if (!existingFlashcard.getDeck().getId().equals(deckId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flashcard deos not belond to this deck");
        }

        flashcardRepository.deleteById(id);
    }
}
