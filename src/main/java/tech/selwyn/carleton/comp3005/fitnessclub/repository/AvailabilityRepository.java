package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Availability;

import java.time.Instant;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    // Get all availability for a trainer
    List<Availability> findByTrainer_AccountIdOrderByStartTime(Long trainerId);

    // Find overlapping availabilities
    List<Availability> findByTrainer_AccountIdAndEndTimeAfterAndStartTimeBefore(
            Long trainerId, Instant start, Instant end);
}
