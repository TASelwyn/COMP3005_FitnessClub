package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashboardService {
    private final AccountRepository accRepo;
    private final MetricService metricService;
    private final GoalService goalService;
    private final SessionService sessionService;

    /*
    Dashboard: Show latest health stats, active goals, past class count, upcoming sessions.
    */
    public Map<String, Object> getDashboard(Long accountId) {
        accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Map<String, Object> response = new HashMap<>();

        // "latest health stats, active goals"
        var latestMetrics = metricService.getLatestMetricEntries(accountId);
        var activeGoals = goalService.getActiveGoals(accountId);

        // Adding metrics and goals in response
        response.put("latestMetrics", latestMetrics);
        response.put("activeGoals", activeGoals);

        // "past class count, upcoming sessions."
        var upcoming = sessionService.getUpcomingSessions(accountId);
        var past = sessionService.getPastSessions(accountId);
        var nextThree = upcoming.stream()
                .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                .limit(3)
                .map(s -> Map.of(
                        "sessionId", s.getId(),
                        "trainerName", s.getTrainer().getFirstName() + " " + s.getTrainer().getLastName(),
                        "startTime", s.getStartTime(),
                        "endTime", s.getEndTime()
                )).toList();

        response.put("upcomingSessionCount", upcoming.size());
        response.put("pastSessionCount", past.size());
        response.put("nextUpcomingSessions", nextThree);

        // returning our final map that contains everything
        return response;
    }
}
