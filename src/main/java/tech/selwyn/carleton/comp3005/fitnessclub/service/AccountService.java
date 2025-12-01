package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.dto.MemberSummaryDto;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoleType;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accRepo;
    private final PasswordEncoder encoder;
    private final GoalService goalService;
    private final MetricService metricService;

    /*
    User Registration: Create a new member with unique email and basic profile info.
    */
    public Account register(String firstName, String lastName, String email, String password) {
        if (accRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Account newAccount = new Account();
        newAccount.setEmail(email);
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setPasswordHash(encoder.encode(password));
        newAccount.setRole(RoleType.ROLE_MEMBER);

        return accRepo.save(newAccount);
    }

    /*
    Member Lookup: Search by name (case-insensitive) and view current goal and last metric. No editing rights.
    */
    public List<MemberSummaryDto> lookupMember(String name) {
        return accRepo.searchMembersByName(name).stream()
                .map(acc -> new MemberSummaryDto(
                        acc.getId(),
                        acc.getFullName(),
                        acc.getEmail(),
                        goalService.getPrimaryGoal(acc.getId()).toSummary(),
                        metricService.getLatestMetricEntries(acc.getId())))
                .toList();
    }

    /*
    Profile Management: Update personal details
    */
    public void updatePersonalInfo(Long accountId, String firstName, String lastName) {
        Account acc = accRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (firstName != null) acc.setFirstName(firstName);
        if (lastName != null) acc.setLastName(lastName);

        accRepo.save(acc);
    }


}
