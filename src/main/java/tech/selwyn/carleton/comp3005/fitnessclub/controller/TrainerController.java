package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.LookupMemberDto;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    private final AccountService accService;

    public TrainerController(AccountService accService) {
        this.accService = accService;
    }

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
}
