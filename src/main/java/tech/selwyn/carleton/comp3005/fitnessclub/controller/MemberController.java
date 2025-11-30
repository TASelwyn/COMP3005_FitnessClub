package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LogMetricDto;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.DashboardService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.MetricService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MetricService metricService;
    private final DashboardService dashboardService;

    @PostMapping("/logMetric")
    public ResponseEntity<?> logMetric(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody LogMetricDto req) {
        metricService.logMetric(user.getAccount().getId(), req.metricId(), req.value());

        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }

    @GetMapping("/getHealthHistory")
    public ResponseEntity<?> getHealthHistory(@AuthenticationPrincipal UserDetailsImpl user) {
        var healthHistory = metricService.getHealthHistory(user.getAccount().getId());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "healthHistory", healthHistory
        ));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@AuthenticationPrincipal UserDetailsImpl user) {
        var dashboard = dashboardService.getDashboard(user.getAccount().getId());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "dashboard", dashboard
        ));
    }
}
