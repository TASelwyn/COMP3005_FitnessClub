package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;

import java.time.Instant;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByMember_AccountId(Long memberId);

    List<Session> findByMember_AccountIdAndStartTimeAfter(Long memberId, Instant now);

    List<Session> findByMember_AccountIdAndEndTimeBefore(Long memberId, Instant now);
}
