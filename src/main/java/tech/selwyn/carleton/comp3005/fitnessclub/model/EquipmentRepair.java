package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "equipment_repairs")
public class EquipmentRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repairId;

    @ManyToOne
    @JoinColumn(name = "equipment_issue_id", nullable = false)
    private EquipmentIssue issue;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account repaired_by;

    @Column(nullable = false)
    private Instant repaired_at;

    @Column(nullable = false)
    private String notes;
}
