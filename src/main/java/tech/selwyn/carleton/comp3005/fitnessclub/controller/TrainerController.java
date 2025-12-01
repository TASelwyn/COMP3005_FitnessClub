package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LookupMemberDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.SetAvailabilityDto;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AvailabilityService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final AccountService accService;
    private final AvailabilityService availabilityService;

    @GetMapping("/memberLookup")
    public ResponseEntity<?> memberLookup(@RequestParam LookupMemberDto req) {
        // TODO implement paging for members using req.limit() req.offset()
        var results = accService.lookupMember(req.name());

        return ResponseEntity.ok(Map.of(
                "query", req.name(),
                "count", results.size(),
                "results", results
        ));
    }

    @PostMapping("/setAvailability")
    public ResponseEntity<?> setAvailability(
            @RequestParam Long trainerId,
            @RequestBody SetAvailabilityDto req
    ) {
        availabilityService.setAvailability(trainerId, req.startTime(), req.endTime(), req.note());

        return ResponseEntity.ok(Map.of("status", "success"));
    }

}
