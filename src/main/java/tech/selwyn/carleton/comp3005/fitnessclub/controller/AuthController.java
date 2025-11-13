package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LoginRequestDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.RegisterRequestDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final AccountService accService;

    public AuthController(AuthenticationManager authManager, AccountService accService) {
        this.authManager = authManager;
        this.accService = accService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody @NotNull RegisterRequestDto req) {
        Account newAccount = accService.register(req.firstName(), req.lastName(), req.email(), req.password());
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @NotNull LoginRequestDto req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "user", req.email()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        req.logout();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
