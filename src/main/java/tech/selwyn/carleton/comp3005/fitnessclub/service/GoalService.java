package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Goal;
import tech.selwyn.carleton.comp3005.fitnessclub.model.HealthFocus;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.GoalRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.HealthFocusRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GoalService {
    private final AccountRepository accRepo;
    private final GoalRepository goalRepo;
    private final HealthFocusRepository healthFocusRepo;
    private final MetricService metricService;

    public List<Map<String, Object>> getActiveGoals(Long accountId) {
        accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Instant now = Instant.now();

        return goalRepo.findByAccountId(accountId).stream()
                .filter(goal -> now.isAfter(goal.getStartDate()))
                .filter(goal -> now.isBefore(goal.getTargetDate()))
                .map(Goal::toSummary)
                .toList();
    }

    public Goal getPrimaryGoal(Long accountId) {
        Account account = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));
        Metric metric = metricService.getMetric(1L);

        HealthFocus focus = healthFocusRepo.findById(accountId).orElseGet(
                () -> {
                    Goal primary = goalRepo.findById(accountId).orElseGet(
                            () -> {
                                Goal goal = Goal.builder()
                                        .account(account)
                                        .title("Unnamed goal")
                                        .targetValue((double) 0)
                                        .startDate(Instant.now())
                                        .targetDate(Instant.now().plusSeconds(6000))
                                        .metric(metric)
                                        .build();

                                return goalRepo.save(goal);
                            });

                    // Auto build healthfocus if user hasn't been queried before
                    HealthFocus newFocus = HealthFocus.builder()
                            .account(account)
                            .primaryGoal(primary)
                            .build();

                    return healthFocusRepo.save(newFocus);
        });

        return focus.getPrimaryGoal();
    }
}
