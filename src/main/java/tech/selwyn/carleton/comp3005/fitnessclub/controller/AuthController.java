package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LoginRequestDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.RegisterRequestDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.security.JwtService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final AccountService accService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody @NotNull RegisterRequestDto req) {
        Account newAccount = accService.register(req.firstName(), req.lastName(), req.email(), req.password());
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @NotNull LoginRequestDto req) {
        try {
            // Validate login
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));

            // Generate token for user
            UserDetails user = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid login credentials provided"));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        req.logout();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
