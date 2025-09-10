package org.flashcardapp.flashcardapp.flashcard;

import org.flashcardapp.flashcardapp.flashcard.dto.CreateFlashcardRequest;
import org.flashcardapp.flashcardapp.flashcard.dto.FlashcardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/decks/{deckId}/flashcards")
public class FlashcardController {
    private FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping
    public List<FlashcardResponse> getFlashcardsByDeck(@PathVariable Long deckId) {
        List<Flashcard> flashcards = flashcardService.getFlashcardsByDeckId(deckId);

        List<FlashcardResponse> response = flashcards.stream().map(flashcard -> new FlashcardResponse(
                flashcard.getId(),
                flashcard.getQuestion(),
                flashcard.getAnswer()
        )).collect(Collectors.toList());

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashcardResponse> getFlashcard(@PathVariable Long id) {
        Flashcard flashcard = flashcardService.getFlashcard(id);

        FlashcardResponse response = new FlashcardResponse(
                flashcard.getId(),
                flashcard.getQuestion(),
                flashcard.getAnswer()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FlashcardResponse> createFlashcard(@PathVariable Long deckId, @RequestBody CreateFlashcardRequest request) {
        Flashcard flashcard = flashcardService.createFlashcard(deckId, request);

        FlashcardResponse response = new FlashcardResponse(
                flashcard.getId(),
                flashcard.getQuestion(),
                flashcard.getAnswer()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashcardResponse> updateFlashcard(@PathVariable Long id, @RequestBody CreateFlashcardRequest request) {
        Flashcard updatedFlashcard = flashcardService.updateFlashcard(id, request);

        FlashcardResponse response = new FlashcardResponse(
                updatedFlashcard.getId(),
                updatedFlashcard.getQuestion(),
                updatedFlashcard.getAnswer()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return ResponseEntity.noContent().build();
    }

}
