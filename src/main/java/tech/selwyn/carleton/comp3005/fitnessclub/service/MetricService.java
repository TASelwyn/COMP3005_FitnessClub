package tech.selwyn.carleton.comp3005.fitnessclub.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricEntryRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricRepository;

import java.time.Instant;
import java.util.*;

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

    public List<Map<String, Object>> getLatestMetricEntries(Long accountId) {
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));
        List<MetricEntry> entries = entryRepo.findByAccountIdOrderByTimestampDesc(acc.getId());

        // Grab latest (only adds once, abusing fact that entries come as desc so first one is the latest)
        Set<Long> seen = new HashSet<>();
        List<MetricEntry> latestPerMetric = new ArrayList<>();
        for (MetricEntry entry : entries) {
            Long metricId = entry.getMetric().getId();
            if (seen.add(metricId)) {
                latestPerMetric.add(entry);
            }
        }

        return latestPerMetric.stream()
                .map(MetricEntry::toSummary)
                .toList();
    }

    public List<Metric> getAllMetrics() {
        return metricRepo.findAll();
    }

    public Metric getMetric(Long metricId) {
        return metricRepo.findById(metricId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find metric"));
    }
}
