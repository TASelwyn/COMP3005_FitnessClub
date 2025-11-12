package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import lombok.AllArgsConstructor;
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
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LoginRequest;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.RegisterRequest;
import tech.selwyn.carleton.comp3005.fitnessclub.model.ClubAccount;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;

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
    public ResponseEntity<ClubAccount> register(@RequestBody @NotNull RegisterRequest req) {

        ClubAccount newClubAccount = accService.register(req.firstName(), req.lastName(), req.email(), req.password());
        return ResponseEntity.ok(newClubAccount);
    }

    @PostMapping("/login")
    public String login(@RequestBody @NotNull LoginRequest req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "Successfully logged in as " + auth.getName();
    }
}
