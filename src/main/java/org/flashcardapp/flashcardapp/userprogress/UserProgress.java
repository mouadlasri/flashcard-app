package org.flashcardapp.flashcardapp.userprogress;

import jakarta.persistence.*;
import org.flashcardapp.flashcardapp.flashcard.Flashcard;
import org.flashcardapp.flashcardapp.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "next_review_date")
    private LocalDateTime nextReviewDate;

    @Column(name = "easiness_factor")
    private Double easinessFactor;

    @Column(name = "repetitions")
    private int repetitions;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;

    public UserProgress() {}

    public UserProgress(LocalDateTime nextReviewDate, Double easinessFactor, int repetitions, LocalDateTime lastReviewed, User user, Flashcard flashcard) {
        this.nextReviewDate = nextReviewDate;
        this.easinessFactor = easinessFactor;
        this.repetitions = repetitions;
        this.lastReviewed = lastReviewed;
        this.user = user;
        this.flashcard = flashcard;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }


}
