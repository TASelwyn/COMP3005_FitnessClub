package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LogMetricDto;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.MetricService;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final AccountService accService;
    private final MetricService metricEntryService;

    public MemberController(AccountService accService, MetricService metricEntryService) {
        this.accService = accService;
        this.metricEntryService = metricEntryService;
    }

    @PostMapping("/logMetric")
    public ResponseEntity<?> logMetric(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody LogMetricDto req) {
        metricEntryService.logMetric(userDetails.getAccountId(), req.metricId(), req.value());

        return ResponseEntity.ok(Map.of(
                "status", "success"
        ));
    }
}
