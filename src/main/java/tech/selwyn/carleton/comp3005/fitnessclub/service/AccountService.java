package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoleType;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;

import java.util.List;
import java.util.Map;

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
}
