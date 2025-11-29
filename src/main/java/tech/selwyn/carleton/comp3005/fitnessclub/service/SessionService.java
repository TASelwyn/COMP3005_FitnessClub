package tech.selwyn.carleton.comp3005.fitnessclub.service;

import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.SessionRepository;

import java.time.Instant;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepo;
    private final AccountRepository accRepo;

    public SessionService(SessionRepository sessionRepo, AccountRepository accRepo) {
        this.sessionRepo = sessionRepo;
        this.accRepo = accRepo;
    }

    public void scheduleSession(Long memberId, Long trainerId, Instant start, Instant end) {
        Account member = accRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Account trainer = accRepo.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Session s = Session.builder()
                .member(member)
                .trainer(trainer)
                .startTime(start)
                .endTime(end)
                .status("SCHEDULED")
                .build();

        sessionRepo.save(s);
    }

    public List<Session> getUpcomingSessions(Long memberId) {
        return sessionRepo.findByMember_AccountIdAndStartTimeAfter(memberId, Instant.now());
    }

    public List<Session> getPastSessions(Long memberId) {
        return sessionRepo.findByMember_AccountIdAndEndTimeBefore(memberId, Instant.now());
    }
}
