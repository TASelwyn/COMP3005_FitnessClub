package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LookupMemberDto;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.SessionService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final AccountService accService;
    private final SessionService sessionService;

    @GetMapping("/memberLookup")
    public ResponseEntity<?> memberLookup(@RequestParam LookupMemberDto req) {
        var results = accService.lookupMember(req.name());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "query", req.name(),
                "count", results.size(),
                "results", results
        ));
    }

    @GetMapping("/getSchedule")
    public ResponseEntity<?> getSchedule(@AuthenticationPrincipal UserDetailsImpl user) {
        var schedule = sessionService.getSchedule(user.getAccount().getId());

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "schedule", schedule
        ));
    }
}
