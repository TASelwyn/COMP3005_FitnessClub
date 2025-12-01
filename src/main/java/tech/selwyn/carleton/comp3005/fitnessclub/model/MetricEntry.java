package tech.selwyn.carleton.comp3005.fitnessclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "metric_entries")
public class MetricEntry {
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
