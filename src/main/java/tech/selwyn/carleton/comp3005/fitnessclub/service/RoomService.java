package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Room;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoomBooking;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.RoomBookingRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.RoomRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.SessionRepository;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class RoomService {
    private final AccountRepository accRepo;
    private final RoomRepository roomRepo;
    private final RoomBookingRepository roomBookingRepo;
    private final SessionRepository sessionRepo;

    /*
    Room Booking: Assign rooms for sessions or classes. Prevent double-booking
    */
    public RoomBooking bookRandomRoom(Long accountId, Instant startTime, Instant endTime) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Invalid start and end time");
        }

        // Get available rooms
        List<Room> available = roomRepo.findAll().stream()
                .filter(r -> !roomBookingRepo.existsByRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        r.getId(), endTime, startTime))
                .toList();

        if (available.isEmpty()) {
            throw new IllegalStateException("No rooms available for the selected time");
        }

        // Get random room and claim it
        Room randomRoom = available.get(new Random().nextInt(available.size()));

        RoomBooking booking = RoomBooking.builder()
                .room(randomRoom)
                .booker(acc)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return roomBookingRepo.save(booking);
    }

    public Session assignRoomBookingForSession(Long accountId, Long sessionId) {
        // Ensure session exists
        Session session = sessionRepo.findById(sessionId).orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // Get room booking randomly
        RoomBooking booking = bookRandomRoom(accountId, session.getStartTime(), session.getEndTime());

        session.setBooking(booking);

        return sessionRepo.save(session);
    }

}

