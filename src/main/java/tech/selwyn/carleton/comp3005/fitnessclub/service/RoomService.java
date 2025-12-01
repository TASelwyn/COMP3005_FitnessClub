package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Room;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoomBooking;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.RoomBookingRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.RoomRepository;

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
    public RoomBooking bookRoom(Long accountId, Long roomId, Instant startTime, Instant endTime) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Room room = roomRepo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Invalid start and end time");
        }

        if (roomBookingRepo.existsByRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(roomId, endTime, startTime)) {
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

    public void assignBookingToSession(Long bookingId, Long sessionId) {
        RoomBooking roomBooking = roomBookingRepo.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // TODO ASSIGN BOOKING TO SESSION
        // Session session = sessionRepo.findBySessionId(sessionId).orElseThrow(() -> new IllegalArgumentException("Session not found"));
        // UPDATE Session's booking ID With: roomBooking.getBookingId()
    }

}

