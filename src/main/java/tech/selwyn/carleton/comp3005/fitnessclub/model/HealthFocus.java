package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Table(name = "health_focus")
public class HealthFocus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal primaryGoal;

}
