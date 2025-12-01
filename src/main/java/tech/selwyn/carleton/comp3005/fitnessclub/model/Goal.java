package tech.selwyn.carleton.comp3005.fitnessclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metric_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Metric metric;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Account account;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double targetValue;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant targetDate;

    public Map<String, Object> toSummary() {
        return Map.of(
                "goalId", id,
                "metricName", metric.getName(),
                "title", title,
                "targetValue", targetValue,
                "startDate", startDate,
                "targetDate", targetDate
        );
    }

}
