package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.model.SessionStatus;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AvailabilityRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.SessionRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepo;
    private final AccountRepository accRepo;
    private final AvailabilityRepository availabilityRepo;

    public Session scheduleSession(Long memberId, Long trainerId, Instant startTime, Instant endTime) {
        Account member = accRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Account trainer = accRepo.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // Ensure trainer doesn't have anything during time slot
        if (sessionRepo.existsByTrainerIdAndStartTimeLessThanAndEndTimeGreaterThan(trainerId, startTime, endTime)) {
            throw new IllegalStateException("Trainer is already booked during this time slot");
        }

        // Check trainer is scheduled for that time
        if (availabilityRepo.existsByTrainerIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(trainerId, startTime, endTime)) {
            throw new IllegalStateException("Trainer is not working during this time slot");
        }

        // Ensure member doesn't have anything during time slot
        if (sessionRepo.existsByMemberIdAndStartTimeLessThanAndEndTimeGreaterThan(memberId, startTime, endTime)) {
            throw new IllegalStateException("Trainer is already booked during this time slot");
        }

        Session session = Session.builder()
                .member(member)
                .trainer(trainer)
                .startTime(startTime)
                .endTime(endTime)
                .status(SessionStatus.SCHEDULED)
                .build();

        return sessionRepo.save(session);
    }

    /*
    Schedule View: See assigned PT sessions and classes.
    */
    public List<Session> getScheduledSessions(Long trainerId) {
        // get all current/upcoming sessions and classes
        return sessionRepo.findByTrainerIdAndStartTimeAfter(trainerId, Instant.now());
    }

    public List<Session> getUpcomingSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndStartTimeAfter(memberId, Instant.now());
    }

    public List<Session> getPastSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndEndTimeBefore(memberId, Instant.now());
    }
}
