package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Goal;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.GoalRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GoalService {
    private final AccountRepository accRepo;
    private final GoalRepository goalRepo;

    public List<Map<String, Object>> getActiveGoals(Long accountId) {
        accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Instant now = Instant.now();

        return goalRepo.findByAccountAccountId(accountId).stream()
                .filter(goal -> now.isAfter(goal.getStartDate()))
                .filter(goal -> now.isBefore(goal.getTargetDate()))
                .map(Goal::toSummary)
                .toList();
    }
}
