package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Account trainer;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;
}
