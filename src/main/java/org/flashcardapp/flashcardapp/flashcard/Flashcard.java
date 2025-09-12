package org.flashcardapp.flashcardapp.flashcard;

import jakarta.persistence.*;
import org.flashcardapp.flashcardapp.deck.Deck;
import org.flashcardapp.flashcardapp.userprogress.UserProgress;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flashcard")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProgress> userProgress = new HashSet<>();

    public Flashcard() {}

    public Flashcard(String question, String answer, Deck deck) {
        this.question = question;
        this.answer = answer;
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Set<UserProgress> getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(Set<UserProgress> userProgress) {
        this.userProgress = userProgress;
    }
}
