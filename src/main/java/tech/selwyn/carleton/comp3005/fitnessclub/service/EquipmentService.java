package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.*;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.*;

import java.time.Instant;

@Service
@AllArgsConstructor
public class EquipmentService {
    private final AccountRepository accRepo;
    private final EquipmentRepository equipmentRepo;
    private final EquipmentIssueRepository equipmentIssueRepo;
    private final EquipmentRepairRepository equipmentRepairRepo;

    /*
    Equipment Maintenance: Log issues, track repair status, associate with room/equipment
    */
    @Transactional
    public EquipmentIssue logEquipmentIssue(Long accountId, Long equipmentId, String issue) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        Equipment equipment = equipmentRepo.findByEquipmentId(equipmentId).orElseThrow(() -> new IllegalArgumentException("Unable to find equipment"));

        EquipmentIssue equipmentIssue = EquipmentIssue.builder()
                .equipment(equipment)
                .description(issue)
                .reported_by(acc)
                .reported_at(Instant.now())
                .build();

        return equipmentIssueRepo.save(equipmentIssue);
    }

    @Transactional
    public EquipmentRepair logEquipmentRepair(Long accountId, Long issueId, String notes) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        EquipmentIssue equipmentIssue = equipmentIssueRepo.findByIssueId(issueId).orElseThrow(() -> new IllegalArgumentException("Unable to find equipment issue"));

        if (equipmentRepairRepo.findByIssueIssueId(issueId).isPresent()) {
            throw new IllegalStateException("A repair was already logged for this issue.");
        }

        EquipmentRepair equipmentRepair = EquipmentRepair.builder()
                .issue(equipmentIssue)
                .notes(notes)
                .repaired_by(acc)
                .repaired_at(Instant.now())
                .build();

        return equipmentRepairRepo.save(equipmentRepair);
    }

    @Transactional
    public boolean hasIssueBeenRepaired(Long issueId) {
        return equipmentRepairRepo.findByIssueIssueId(issueId).isPresent();
    }
}
