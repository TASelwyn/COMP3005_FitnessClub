package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Availability;

import java.time.Instant;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    boolean existsByTrainerIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Long trainerId, Instant startTime, Instant endTime);

    // Find overlapping availabilities
    List<Availability> findByTrainerIdAndEndTimeAfterAndStartTimeBefore(
            Long trainerId, Instant start, Instant end);
}
