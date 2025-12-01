package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "availabilities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Account trainer;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    private String note; // optional
}
