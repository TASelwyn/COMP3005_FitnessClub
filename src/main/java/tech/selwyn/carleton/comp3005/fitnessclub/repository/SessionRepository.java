package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;

import java.time.Instant;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByMemberIdAndStartTimeAfter(Long memberId, Instant now);
    List<Session> findByTrainerIdAndStartTimeAfter(Long memberId, Instant now);

    boolean existsByTrainerIdAndStartTimeLessThanAndEndTimeGreaterThan(Long trainerId, Instant startTime, Instant endTime);
    boolean existsByMemberIdAndStartTimeLessThanAndEndTimeGreaterThan(Long trainerId, Instant startTime, Instant endTime);

    List<Session> findByMemberIdAndEndTimeBefore(Long memberId, Instant now);
}
