package org.flashcardapp.flashcardapp.user;

import org.flashcardapp.flashcardapp.user.dto.UpdateUserRequest;
import org.flashcardapp.flashcardapp.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getLastLogin()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<UserResponse> updateUserEmail(@PathVariable Long id, @RequestBody UpdateUserRequest request, Authentication authentication) {
        User user = userService.updateUser(id, request);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getLastLogin()
        );

        return ResponseEntity.ok(response);
    }

}
