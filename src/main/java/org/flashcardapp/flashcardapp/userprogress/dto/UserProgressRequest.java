package org.flashcardapp.flashcardapp.userprogress.dto;

import java.time.LocalDateTime;

public class UserProgressRequest {
    private LocalDateTime nextReviewDate;
    private Double easinessFactor;
    private int repetitions;
    private LocalDateTime lastReviewed;

    public UserProgressRequest(LocalDateTime nextReviewDate, Double easinessFactor, int repetitions, LocalDateTime lastReviewed) {
        this.nextReviewDate = nextReviewDate;
        this.easinessFactor = easinessFactor;
        this.repetitions = repetitions;
        this.lastReviewed = lastReviewed;
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

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
}
