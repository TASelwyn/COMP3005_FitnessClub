package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Goal;
import tech.selwyn.carleton.comp3005.fitnessclub.model.HealthFocus;
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

        HealthFocus focus = healthFocusRepo.findById(accountId).orElseGet(
                () -> {
                    // Auto build healthfocus if user hasn't been queried before
                    HealthFocus newFocus = HealthFocus.builder()
                            .account(account)
                            .primaryGoal(null)
                            .build();

                    return healthFocusRepo.save(newFocus);
        });

        return focus.getPrimaryGoal();
    }
}
