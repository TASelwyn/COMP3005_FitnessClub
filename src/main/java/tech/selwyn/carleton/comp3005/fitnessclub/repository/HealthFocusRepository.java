package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.HealthFocus;

import java.util.Optional;

public interface HealthFocusRepository extends JpaRepository<HealthFocus, Long> {
    Optional<HealthFocus> findByAccountAccountId(Long accountId);
}
