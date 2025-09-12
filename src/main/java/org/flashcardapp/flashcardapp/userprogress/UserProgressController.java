package org.flashcardapp.flashcardapp.userprogress;

import org.flashcardapp.flashcardapp.user.User;
import org.flashcardapp.flashcardapp.user.UserService;
import org.flashcardapp.flashcardapp.userprogress.dto.UserProgressRequest;
import org.flashcardapp.flashcardapp.userprogress.dto.UserProgressResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserProgressController {
    private final UserService userService;
    private UserProgressService userProgressService;

    public UserProgressController(UserProgressService userProgressService, UserService userService) {
        this.userProgressService = userProgressService;
        this.userService = userService;
    }

    // get all progress records for a user
    @GetMapping("/users/{userId}/progress")
    public List<UserProgressResponse> getUserProgress(@PathVariable Long userId, Authentication authentication) {
        List<UserProgress> userProgress = userProgressService.getUserProgress(userId);

        validateUserAccess(authentication, userId);

        List<UserProgressResponse> response = userProgress.stream().map(progress -> new UserProgressResponse(
                progress.getId(),
                progress.getNextReviewDate(),
                progress.getEasinessFactor(),
                progress.getRepetitions(),
                progress.getLastReviewed(),
                progress.getFlashcard().getId()
        )).collect(Collectors.toList());

        return response;
    }

    // get specific progress record
    @GetMapping("/user-progress/{progressId}")
    public ResponseEntity<UserProgressResponse> getProgressRecord(@PathVariable Long progressId, Authentication authentication) {
        UserProgress userProgress = userProgressService.getProgressRecord(progressId);

        // validating user
        validateUserAccess(authentication, userProgress.getUser().getId());

        UserProgressResponse response = new UserProgressResponse(
                userProgress.getId(),
                userProgress.getNextReviewDate(),
                userProgress.getEasinessFactor(),
                userProgress.getRepetitions(),
                userProgress.getLastReviewed(),
                userProgress.getFlashcard().getId()
        );

        return ResponseEntity.ok(response);
    }

    // update specific progress record
    @PutMapping("/user-progress/{progressId}")
    public ResponseEntity<UserProgressResponse> updateProgressRecord(@PathVariable Long progressId, @RequestBody UserProgressRequest request, Authentication authentication) {
        UserProgress existingProgress = userProgressService.getProgressRecord(progressId);

        // validating user
        validateUserAccess(authentication, existingProgress.getUser().getId());

        UserProgress updatedProgress = userProgressService.updateProgress(progressId, request);

        UserProgressResponse response = new UserProgressResponse(
                updatedProgress.getId(),
                updatedProgress.getNextReviewDate(),
                updatedProgress.getEasinessFactor(),
                updatedProgress.getRepetitions(),
                updatedProgress.getLastReviewed(),
                updatedProgress.getFlashcard().getId()
        );

        return ResponseEntity.ok(response);
    }

    public void validateUserAccess(Authentication authentication, Long targetUserId) {
        String username = authentication.getName();

        User currentUser = userService.getUserByUsername(username);

        if (!currentUser.getId().equals(targetUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can only access your own progress data");
        }
    }

}
