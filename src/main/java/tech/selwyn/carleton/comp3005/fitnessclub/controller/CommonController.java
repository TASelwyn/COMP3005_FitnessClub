package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @GetMapping("/profile")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Map.of(
                "authenticated", auth.isAuthenticated(),
                "email", user.getEmail(),
                "accountId", user.getAccountId(),
                "firstName", user.getAccount().getFirstName(),
                "lastName", user.getAccount().getLastName(),
                "roles", roles
        );
    }
}
