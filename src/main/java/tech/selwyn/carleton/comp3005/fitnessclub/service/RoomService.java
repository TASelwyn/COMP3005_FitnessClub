package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.*;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.*;

import java.time.Instant;

@Service
@AllArgsConstructor
public class RoomService {
    private final AccountRepository accRepo;
    private final RoomRepository roomRepo;
    private final RoomBookingRepository roomBookingRepo;

    /*
    Room Booking: Assign rooms for sessions or classes. Prevent double-booking
    */
    @Transactional
    public RoomBooking bookRoom(Long accountId, Long roomId, Instant startTime, Instant endTime) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Room room = roomRepo.findByRoomId(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Invalid start and end time");
        }

        if (roomBookingRepo.existsByRoomRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(roomId, endTime, startTime)) {
            throw new IllegalStateException("Room is already booked during the selected time period. Overlaps are not allowed.");
        }

        RoomBooking booking = RoomBooking.builder()
                .room(room)
                .booker(acc)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return roomBookingRepo.save(booking);
    }

    @Transactional
    public void assignBookingToSession(Long bookingId, Long sessionId) {
        RoomBooking roomBooking = roomBookingRepo.findByBookingId(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // TODO ASSIGN BOOKING TO SESSION
        // Session session = sessionRepo.findBySessionId(sessionId).orElseThrow(() -> new IllegalArgumentException("Session not found"));
        // UPDATE ENTITY TO STORE: roomBooking.getBookingId()
    }

}
