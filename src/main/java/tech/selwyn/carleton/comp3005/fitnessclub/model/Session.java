package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Account trainer;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Account member;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private RoomBooking booking;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @Column(nullable = false)
    private String status; // SCHEDULED_ON_TIME OR COMPLETED OR CANCELLED
}

