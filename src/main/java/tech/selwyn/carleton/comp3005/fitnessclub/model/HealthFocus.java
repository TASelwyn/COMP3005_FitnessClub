package tech.selwyn.carleton.comp3005.fitnessclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @Column(name = "account_id")
    private Long id;

    @OneToOne
    @MapsId // maps PK
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal primaryGoal;
}
