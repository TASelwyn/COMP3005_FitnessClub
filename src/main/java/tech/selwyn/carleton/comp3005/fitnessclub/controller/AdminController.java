package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.AssignBookingDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.EquipmentIssueDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.EquipmentRepairDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentIssue;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentRepair;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.EquipmentService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.RoomService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final RoomService roomService;
    private final EquipmentService equipmentService;

    @PostMapping("/bookRoomForSession")
    public ResponseEntity<?> bookRoomForSession(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody AssignBookingDto req) {
        Session session = roomService.getBookingForSession(user.getAccount().getId(), req.sessionId());

        return ResponseEntity.ok(Map.of(
                "message", "Successfully booked room for session",
                "session", session));
    }

    @PostMapping("/logMaintenance")
    public ResponseEntity<?> logMaintenance(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody EquipmentIssueDto req) {
        EquipmentIssue equipmentIssue = equipmentService.logEquipmentIssue(user.getAccount().getId(), req.equipmentId(), req.issue());

        return ResponseEntity.ok(Map.of(
                "message", "Successfully logged issue",
                "equipmentIssueId", equipmentIssue.getId()));
    }

    @PostMapping("/logRepair")
    public ResponseEntity<?> logRepair(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody EquipmentRepairDto req) {
        EquipmentRepair equipmentRepair = equipmentService.logEquipmentRepair(user.getAccount().getId(), req.issueId(), req.notes());

        return ResponseEntity.ok(Map.of(
                "message", "Successfully logged repair",
                "equipmentRepairId", equipmentRepair.getId()));
    }
}
