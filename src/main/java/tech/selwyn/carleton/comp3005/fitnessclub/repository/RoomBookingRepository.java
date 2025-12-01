package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoomBooking;

import java.time.Instant;
import java.util.Optional;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    boolean existsByRoomIdAndStartTimeLessThanAndEndTimeGreaterThan(Long roomId, Instant endTime, Instant startTime);
}
