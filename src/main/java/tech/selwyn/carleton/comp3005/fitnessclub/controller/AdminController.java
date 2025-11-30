package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.AssignBookingDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.EquipmentIssueDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.EquipmentRepairDto;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.RoomBookingDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentIssue;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentRepair;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoomBooking;
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

    @PostMapping("/bookRoom")
    public ResponseEntity<?> bookRoom(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody RoomBookingDto req) {
        RoomBooking booking = roomService.bookRoom(user.getAccount().getId(), req.roomId(), req.startTime(), req.endTime());

        return ResponseEntity.ok(Map.of(
                "message", "Successfully booked room for session",
                "bookingId", booking.getId()));
    }

    @PostMapping("/assignBookingToSession")
    public ResponseEntity<?> assignBookingToSession(@Valid @RequestBody AssignBookingDto req) {
        roomService.assignBookingToSession(req.bookingId(), req.sessionId());
        // TODO Might merge w/ bookRoom?

        return ResponseEntity.ok(Map.of(
                "message", "Successfully updated session with booking")
        );
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
