package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.EquipmentRepair;

import java.util.Optional;

public interface EquipmentRepairRepository extends JpaRepository<EquipmentRepair, Long> {

    Optional<EquipmentRepair> findByIssueId(Long issueId);
}
