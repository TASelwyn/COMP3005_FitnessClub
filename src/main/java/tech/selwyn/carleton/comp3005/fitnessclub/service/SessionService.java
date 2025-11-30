package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.SessionRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepo;
    private final AccountRepository accRepo;

    public Session scheduleSession(Long memberId, Long trainerId, Instant startTime, Instant endTime) {
        Account member = accRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Account trainer = accRepo.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Session s = Session.builder()
                .member(member)
                .trainer(trainer)
                .startTime(startTime)
                .endTime(endTime)
                .status("SCHEDULED")
                .build();

        return sessionRepo.save(s);
    }

    /*
    Schedule View: See assigned PT sessions and classes.
    */
    public List<Session> getSchedule(Long memberId) {
        // get all current/upcoming sessions and classes
        // TODO DO THIS Make it return assigned PT Sessions/Classes
        return sessionRepo.findByMemberIdAndStartTimeAfter(memberId, Instant.now());
    }

    public List<Session> getUpcomingSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndStartTimeAfter(memberId, Instant.now());
    }

    public List<Session> getPastSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndEndTimeBefore(memberId, Instant.now());
    }
}
