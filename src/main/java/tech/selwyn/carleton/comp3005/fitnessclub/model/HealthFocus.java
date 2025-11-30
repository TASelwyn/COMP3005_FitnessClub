package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Table(name = "health_focus")
public class HealthFocus {
    @Id
    private Long id;

    @OneToOne
    @MapsId // maps PK
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @Null
    private Goal primaryGoal;

}
