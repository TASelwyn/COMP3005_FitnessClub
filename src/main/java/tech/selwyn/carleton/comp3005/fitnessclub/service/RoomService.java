package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.*;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.*;

import java.time.Instant;

@Service
public class RoomService {
    private final AccountRepository accRepo;
    private final RoomRepository roomRepo;
    private final RoomBookingRepository roomBookingRepo;

    public RoomService(AccountRepository accRepo, RoomRepository roomRepo, RoomBookingRepository roomBookingRepo) {
        this.accRepo = accRepo;
        this.roomRepo = roomRepo;
        this.roomBookingRepo = roomBookingRepo;
    }

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
}
