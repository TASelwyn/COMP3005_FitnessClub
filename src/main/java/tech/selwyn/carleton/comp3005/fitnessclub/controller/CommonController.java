package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.UpdateProfileDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Goal;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.GoalService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.MetricService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/common")
public class CommonController {
    private final AccountService accountService;
    private final GoalService goalService;
    private final MetricService metricService;

    @GetMapping("/profile")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Map.of(
                "authenticated", auth.isAuthenticated(),
                "email", user.getEmail(),
                "accountId", user.getAccount().getId(),
                "firstName", user.getAccount().getFirstName(),
                "lastName", user.getAccount().getLastName(),
                "roles", roles
        );
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody UpdateProfileDto req) {
        // Update first/last Name
        accountService.updatePersonalInfo(user.getAccount().getId(), req.firstName(), req.lastName());

        // Update goal
        Goal primaryGoal = goalService.getPrimaryGoal(user.getAccount().getId());
        if (!req.goalTitle().equals(primaryGoal.getTitle())) {
            primaryGoal.setTitle(req.goalTitle());
        }
        if (!req.goalTargetValue().equals(primaryGoal.getTargetValue())) {
            primaryGoal.setTargetValue(req.goalTargetValue());
        }
        if (!req.goalTargetDate().equals(primaryGoal.getTargetDate())) {
            primaryGoal.setTargetDate(req.goalTargetDate());
        }

        // Update metric under primaryGoal
        if (!req.metricId().equals(primaryGoal.getMetric().getId())) {
            Metric metric = metricService.getMetric(req.metricId());
            primaryGoal.setMetric(metric);
        }


        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }
}
