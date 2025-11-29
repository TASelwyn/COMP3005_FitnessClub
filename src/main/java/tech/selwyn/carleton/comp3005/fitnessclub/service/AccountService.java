package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.UpdateProfileDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Goal;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoleType;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

@Service
public class AccountService {
    private final AccountRepository accRepo;
    private final PasswordEncoder encoder;

    public AccountService(AccountRepository accRepo, PasswordEncoder encoder) {
        this.accRepo = accRepo;
        this.encoder = encoder;
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

//    @Transactional
//    public void updateProfile(Long accountId, UpdateProfileDto dto) {
//        Account account = accRepo.findById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
//        if (dto.firstName() != null) account.setFirstName(dto.firstName());
//        if (dto.lastName() != null) account.setLastName(dto.lastName());
//        if (dto.email() != null) account.setEmail(dto.email());
//
//        // Update goal if provided
//        if (dto.goalTitle() != null && dto.goalTargetValue() != null && dto.goalTargetDate() != null) {
//            Goal goal = Goal.builder()
//                    .title(dto.goalTitle())
//                    .targetValue(dto.goalTargetValue())
//                    .startDate(Instant.now())
//                    .targetDate(dto.goalTargetDate())
//                    .account(account)
//                    .build();
//            account.getGoals().add(goal);
//        }
//
//        accRepo.save(account); // saves account and goals (cascade)
//    }
@Transactional
public void updatePersonalInfo(Long accountId, UpdateProfileDto req) {
    Account acc = accRepo.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    if (req.firstName() != null) acc.setFirstName(req.firstName());
    if (req.lastName() != null) acc.setLastName(req.lastName());
    if (req.email() != null) acc.setEmail(req.email());

    accRepo.save(acc);
}


}
