package org.flashcardapp.flashcardapp.userprogress;

import org.flashcardapp.flashcardapp.userprogress.dto.UserProgressRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserProgressService {
    private UserProgressRepository  userProgressRepository;

    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public UserProgress getProgressRecord(Long id) {
        UserProgress userProgress = userProgressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Progress not found with ID: " + id));

        return userProgress;
    }

    public List<UserProgress> getUserProgress(Long userId) {

        List<UserProgress> progress = userProgressRepository.findByUserId(userId);

        return progress;
    }

    @Transactional
    public UserProgress updateProgress(Long progressId, UserProgressRequest request) {
        UserProgress userProgress = userProgressRepository.findById(progressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Progress not found with ID: " + progressId));

        userProgress.setNextReviewDate(request.getNextReviewDate());
        userProgress.setEasinessFactor(request.getEasinessFactor());
        userProgress.setRepetitions(request.getRepetitions());
        userProgress.setLastReviewed(request.getLastReviewed());

        return userProgressRepository.save(userProgress);

    }
}
