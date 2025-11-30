package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricEntryRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MetricService {
    private final AccountRepository accRepo;
    private final MetricRepository metricRepo;
    private final MetricEntryRepository entryRepo;

    /*
    Health History: Log multiple metric entries; do not overwrite. Must support time-stamped entries.
    */
    public void logMetric(Long accountId, Long metricId, Double value) {
        Metric metric = metricRepo.findById(metricId).orElseThrow(() -> new IllegalArgumentException("Unknown metric"));
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        MetricEntry entry = MetricEntry.builder()
                .metric(metric)
                .account(acc)
                .value(value)
                .timestamp(Instant.now())
                .build();

        entryRepo.save(entry);
    }

    public List<Map<String, Object>> getHealthHistory(Long accountId) {
        Account acc = accRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        return entryRepo.findByAccountIdOrderByTimestampDesc(acc.getId())
                .stream()
                .map(MetricEntry::toSummary)
                .toList();
    }

    @Transactional
    public MetricEntry getLatestMetric(Long accountId, Long metricId) {
        return entryRepo.findTopByAccountIdAndMetricIdOrderByTimestampDesc(accountId, metricId)
                .orElse(null);
    }

    public List<Metric> getAllMetrics() {
        return metricRepo.findAll();
    }

    public Metric getMetric(Long metricId) {
        return metricRepo.findById(metricId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find metric"));
    }
}
