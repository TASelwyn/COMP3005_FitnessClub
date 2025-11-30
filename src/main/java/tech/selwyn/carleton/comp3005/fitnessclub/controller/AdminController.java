package tech.selwyn.carleton.comp3005.fitnessclub.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.RoomBookingDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoomBooking;
import tech.selwyn.carleton.comp3005.fitnessclub.security.UserDetailsImpl;
import tech.selwyn.carleton.comp3005.fitnessclub.service.AccountService;
import tech.selwyn.carleton.comp3005.fitnessclub.service.RoomService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AccountService accService;
    private final RoomService roomService;

    public AdminController(AccountService accService, RoomService roomService) {
        this.accService = accService;
        this.roomService = roomService;
    }

    @PostMapping("/bookRoom")
    public ResponseEntity<?>  bookRoom(@AuthenticationPrincipal UserDetailsImpl user, @Valid @RequestBody RoomBookingDto req) {
        RoomBooking booking = roomService.bookRoom(user.getAccountId(), req.roomId(), req.startTime(), req.endTime());

        return ResponseEntity.ok(Map.of(
                "message", "Successfully booked room",
                "bookingId", booking.getBookingId()));
    }
}
