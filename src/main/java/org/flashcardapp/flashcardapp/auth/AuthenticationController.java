package org.flashcardapp.flashcardapp.auth;

import org.flashcardapp.flashcardapp.auth.dto.AuthenticationRequest;
import org.flashcardapp.flashcardapp.auth.dto.AuthenticationResponse;
import org.flashcardapp.flashcardapp.security.JwtTokenProvider;
import org.flashcardapp.flashcardapp.user.User;
import org.flashcardapp.flashcardapp.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            // authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // generate jwt token
            String jwtToken = jwtTokenProvider.generateToken(request.getUsername());

            // get user details
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            AuthenticationResponse response = new AuthenticationResponse(
                    jwtToken,
                    user.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        final String jwtToken = jwtTokenProvider.generateToken(savedUser.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(
                jwtToken,
                savedUser.getUsername()
        ));
    }

}
