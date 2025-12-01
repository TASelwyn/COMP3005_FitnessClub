package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Session;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.SessionRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepo;
    private final AccountRepository accRepo;
    private final RoomService roomService;

    public Session scheduleSession(Long memberId, Long trainerId, Instant startTime, Instant endTime) {
        Account member = accRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Account trainer = accRepo.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // TODO Ensure trainer doesn't have anything during time slot
        //


        // TODO Check trainer is scheduled for that time (Must add functionality to store EmployeeSchedule, make new Entity for it)
        //
        // CHECK INDEPENDENTLY (check roomService for example)




        // TODO Ensure member doesn't have anything during time slot
        //






        // TODO WHEN BOTH TRAINER/MEMBER HAVE SCHEDULED A TIME TOGETHER, LOGIC MUST THEN DO A ROOM BOOKING FOR THEIR SESSION
        //roomService.bookRoom(accountId, roomId, startTime, endTime);
        //roomService.assignBookingToSession(bookingId, sessionId);

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
    public List<Session> getScheduledSessions(Long trainerId) {
        // get all current/upcoming sessions and classes
        // TODO DO THIS Make it return assigned PT Sessions/Classes
        return new ArrayList<>();
    }

    public List<Session> getUpcomingSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndStartTimeAfter(memberId, Instant.now());
    }

    public List<Session> getPastSessions(Long memberId) {
        return sessionRepo.findByMemberIdAndEndTimeBefore(memberId, Instant.now());
    }
}
