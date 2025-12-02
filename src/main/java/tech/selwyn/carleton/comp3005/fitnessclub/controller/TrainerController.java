package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LookupMemberDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.SetAvailabilityDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Availability;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AvailabilityService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.SessionService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final AccountService accService;
    private final SessionService sessionService;
    private final AvailabilityService availabilityService;

    @PostMapping("/memberLookup")
    public ResponseEntity<?> memberLookup(@RequestBody LookupMemberDto req) {
        var results = accService.lookupMember(req.name());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "query", req.name(),
                "count", results.size(),
                "results", results
        ));
    }

    @PostMapping("/setAvailability")
    public ResponseEntity<?> setAvailability(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody SetAvailabilityDto req
    ) {
        Availability availability = availabilityService.setAvailability(user.getAccount().getId(), req.startTime(), req.endTime());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "availability", availability
        ));
    }

    @GetMapping("/getSchedule")
    public ResponseEntity<?> getScheduledSessions(@AuthenticationPrincipal UserDetailsImpl user) {
        var schedule = sessionService.getScheduledSessions(user.getAccount().getId());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "upcomingSessions", schedule,
                "upcomingClasses", "[]"
        ));
    }
}
