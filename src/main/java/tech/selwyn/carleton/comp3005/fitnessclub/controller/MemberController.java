package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LogMetricDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.UpdateProfileDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.MetricService;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.service.SessionService;


import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final AccountService accService;
    private final MetricService metricEntryService;
    private final SessionService sessionService;

    public MemberController(AccountService accService, MetricService metricEntryService, SessionService sessionService) {
        this.accService = accService;
        this.metricEntryService = metricEntryService;
        this.sessionService = sessionService;
    }

    @PostMapping("/logMetric")
    public ResponseEntity<?> logMetric(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody LogMetricDto req) {
        metricEntryService.logMetric(userDetails.getAccountId(), req.metricId(), req.value());

        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }

    @GetMapping("/healthHistory")
    public ResponseEntity<?> getHealthHistory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Fetch all metric entries for the logged-in member
        var metricEntries = metricEntryService.getMetricHistory(userDetails.getAccountId());

        // Map each MetricEntry to a simple summary to return in the API
        var response = metricEntries.stream().map(entry -> Map.of(
                "metricId", entry.getMetric().getMetricId(),
                "metricName", entry.getMetric().getName(),
                "unit", entry.getMetric().getUnit(),
                "value", entry.getValue(),
                "timestamp", entry.getTimestamp()
        )).toList();

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "healthHistory", response
        ));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Map<String, Object> response = new HashMap<>();

        Long accountId = userDetails.getAccountId();

        // for sessions upcoming and past both
        var upcoming = sessionService.getUpcomingSessions(userDetails.getAccountId());
        var past = sessionService.getPastSessions(userDetails.getAccountId());


        // Fetch latest metric entries for this member
        var allMetrics = metricEntryService.getAllMetrics();
        // Get all metrics defined in the system
        var latestMetrics = allMetrics.stream().map(metric -> {
            var latestEntry = metricEntryService.getLatestMetric(accountId, metric.getMetricId());
            return Map.of(
                    "metricId", metric.getMetricId(),
                    "metricName", metric.getName(),
                    "unit", metric.getUnit(),
                    "latestValue", latestEntry != null ? latestEntry.getValue() : null,
                    "timestamp", latestEntry != null ? latestEntry.getTimestamp() : null
            );
        }).toList();

        //  Replace with actual goals retrieval once AccountService supports it
        var activeGoals = accService.getActiveGoals(accountId).stream().map(goal -> Map.of(
                "goalId", goal.getGoalId(),
                "title", goal.getTitle(),
                "targetValue", goal.getTargetValue(),
                "startDate", goal.getStartDate(),
                "targetDate", goal.getTargetDate()
        )).toList();

        // Adding metrics and goals in response
        response.put("latestMetrics", latestMetrics);
        response.put("activeGoals", activeGoals);

        var nextThree = upcoming.stream()
                .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                .limit(3)
                .map(s -> Map.of(
                        "sessionId", s.getSessionId(),
                        "trainerName", s.getTrainer().getFirstName() + " " + s.getTrainer().getLastName(),
                        "startTime", s.getStartTime(),
                        "endTime", s.getEndTime()
                )).toList();

        response.put("upcomingSessionCount", upcoming.size());
        response.put("pastSessionCount", past.size());
        response.put("nextUpcomingSessions", nextThree);
        // returning our final map that contains everything
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateProfileDto req
    ) {
        accService.updateProfile(
                userDetails.getAccountId(),
                req.firstName(),
                req.lastName(),
                req.email(),
                req.goalTitle(),
                req.goalTargetValue(),
                req.goalTargetDate(),
                req.metricId(),        // Add these fields to UpdateProfileDto
                req.metricValue()
        );

        return ResponseEntity.noContent().build();
    }


}
