package org.flashcardapp.flashcardapp.deck;

import org.apache.coyote.Response;
import org.flashcardapp.flashcardapp.deck.dto.CreateDeckRequest;
import org.flashcardapp.flashcardapp.deck.dto.DeckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
    private DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<DeckResponse> getAllDecks() {
        List<Deck> decks = deckService.getAllDecksForCurrentUser();

        List<DeckResponse> response = decks.stream().map(deck -> new DeckResponse(
                deck.getId(),
                deck.getName()
        )).collect(Collectors.toList());

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckResponse> getDeck(@PathVariable Long id) {
        Deck deck = deckService.getDeckById(id);

        DeckResponse response = new DeckResponse(
                deck.getId(),
                deck.getName()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DeckResponse> createDeck(@RequestBody CreateDeckRequest request) {
        Deck deck = deckService.createDeck(request);

        DeckResponse response = new DeckResponse(
                deck.getId(),
                deck.getName()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeckResponse> updateDeck(@PathVariable Long id, @RequestBody CreateDeckRequest request) {
        Deck updatedDeck = deckService.updateDeck(id, request);

        DeckResponse response = new DeckResponse(
                updatedDeck.getId(),
                updatedDeck.getName()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
