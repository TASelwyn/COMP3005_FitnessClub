package tech.selwyn.carleton.comp3005.fitnessclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "metric_entries")
public class MetricEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "metric_id", nullable = false)
    private Metric metric;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Instant timestamp;

    public Map<String, Object> toSummary() {
        return Map.of(
                "metric", metric.getName(),
                "unit", metric.getUnit(),
                "value", value,
                "timestamp", timestamp
        );
    }
}
