package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.UpdateProfileDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.*;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricEntryRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

@Service
public class AccountService {
    private final AccountRepository accRepo;
    private final PasswordEncoder encoder;
    private final MetricRepository metricRepo;
    private final MetricEntryRepository entryRepo;

    public AccountService(AccountRepository accRepo, PasswordEncoder encoder, MetricRepository metricRepo, MetricEntryRepository entryRepo) {
        this.accRepo = accRepo;
        this.encoder = encoder;
        this.metricRepo = metricRepo;
        this.entryRepo = entryRepo;
    }



    @Transactional
    public Account register(String firstName, String lastName, String email, String password) {
        if (accRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Account newAccount = new Account();
        newAccount.setEmail(email);

        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setPasswordHash(encoder.encode(password));
        newAccount.setRole(RoleType.ROLE_MEMBER.toString());

        return accRepo.save(newAccount);
    }

    public List<Map<String, Object>> lookupMember(String name) {
        return accRepo.searchMembersByName(name).stream()
                .map(Account::toSummary)
                .toList();
    }

    // Fetch member account
    public Account getAccountById(Long accountId) {
        return accRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    // Fetch active goals for dashboard
    public List<Goal> getActiveGoals(Long accountId) {
        Account account = getAccountById(accountId);
        List<Goal> goals = account.getGoals();

        Instant now = Instant.now();

        return goals.stream()
                .filter(goal -> now.isBefore(goal.getTargetDate()))
                .toList();
    }


@Transactional
public void updateProfile(
        Long accountId, // to load account
        String firstName, //  for basic profile changes
        String lastName,
        String email,
        String goalTitle, // for goals
        Double goalTargetValue,
        Instant goalTargetDate,
        Long metricId,       // for new health metric
        Double metricValue
) {
        // 1. load account
    Account acc = accRepo.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));


    // 2. Update personal info

    if (firstName != null) acc.setFirstName(firstName);
    if (lastName != null) acc.setLastName(lastName);
    if (email != null) acc.setEmail(email);

    // 3. Update goal info
    boolean goalProvided = goalTitle != null || goalTargetValue != null || goalTargetDate != null;

    if (goalProvided) {

        Goal goal = acc.getGoals().stream().findFirst().orElse(null);

        // If no goal exists then we are creating one
        if (goal == null) {
            goal = new Goal();
            goal.setAccount(acc);
            acc.getGoals().clear();
            acc.getGoals().add(goal);
        }

        // Update goal fields
        if (goalTitle != null) goal.setTitle(goalTitle);
        if (goalTargetValue != null) goal.setTargetValue(goalTargetValue);
        if (goalTargetDate != null) goal.setTargetDate(goalTargetDate);
    }

    // 4. log new metrics
    if (metricId != null && metricValue != null) {
        Metric metric = metricRepo.findByMetricId(metricId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown metric"));

        MetricEntry entry = MetricEntry.builder()
                .account(acc)
                .metric(metric)
                .value(metricValue)
                .timestamp(Instant.now())
                .build();

        entryRepo.save(entry);
    }

    // One save handles everything because of cascade
    accRepo.save(acc);
}



}
