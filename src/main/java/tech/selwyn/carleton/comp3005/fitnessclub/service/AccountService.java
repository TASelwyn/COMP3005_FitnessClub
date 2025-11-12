package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.ClubAccount;
import tech.selwyn.carleton.comp3005.fitnessclub.model.RoleType;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accRepo;
    private final PasswordEncoder encoder;

    public AccountService(AccountRepository accRepo, PasswordEncoder encoder) {
        this.accRepo = accRepo;
        this.encoder = encoder;
    }

    @Transactional
    public ClubAccount register(String firstName, String lastName, String email, String password) {
        if (accRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        ClubAccount newClubAccount = new ClubAccount();
        newClubAccount.setEmail(email);

        newClubAccount.setFirstName(firstName);
        newClubAccount.setLastName(lastName);
        newClubAccount.setPasswordHash(encoder.encode(password));
        newClubAccount.setRole(RoleType.ROLE_MEMBER.toString());

        return accRepo.save(newClubAccount);
    }
}
