package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentIssue;

import java.util.Optional;

public interface EquipmentIssueRepository extends JpaRepository<EquipmentIssue, Long> {
    Optional<EquipmentIssue> findByIssueId(Long issueId);
}
