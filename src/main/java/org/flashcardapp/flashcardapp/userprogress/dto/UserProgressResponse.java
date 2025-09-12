package org.flashcardapp.flashcardapp.userprogress.dto;

import java.time.LocalDateTime;

public class UserProgressResponse {
    private Long id;
    private LocalDateTime nextReviewDate;
    private Double easinessFactor;
    private LocalDateTime lastReviewed;
    private int repetitions;
    private Long flashcardId;

    public UserProgressResponse(Long id, LocalDateTime nextReviewDate, Double easinessFactor,  int repetitions, LocalDateTime lastReviewed, Long flashcardId) {
        this.id = id;
        this.nextReviewDate = nextReviewDate;
        this.easinessFactor = easinessFactor;
        this.repetitions = repetitions;
        this.lastReviewed = lastReviewed;
        this.flashcardId = flashcardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public Double getEasinessFactor() {
        return easinessFactor;
    }

    public void setEasinessFactor(Double easinessFactor) {
        this.easinessFactor = easinessFactor;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(Long flashcardId) {
        this.flashcardId = flashcardId;
    }
}
