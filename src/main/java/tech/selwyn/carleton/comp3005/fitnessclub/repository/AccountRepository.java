package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.ClubAccount;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<ClubAccount, Long> {
    Optional<ClubAccount> findByEmail(String email);
}
