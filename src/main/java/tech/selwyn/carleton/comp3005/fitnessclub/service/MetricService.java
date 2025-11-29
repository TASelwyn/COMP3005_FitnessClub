package tech.selwyn.carleton.comp3005.fitnessclub.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Metric;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricEntryRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.MetricRepository;

import java.time.Instant;
import java.util.List;

@Service
public class MetricService {
    private final AccountRepository accRepo;
    private final MetricRepository metricRepo;
    private final MetricEntryRepository entryRepo;

    public MetricService(AccountRepository accRepo, MetricRepository metricRepo, MetricEntryRepository entryRepo) {
        this.accRepo = accRepo;
        this.metricRepo = metricRepo;
        this.entryRepo = entryRepo;
    }

    @Transactional
    public void logMetric(Long accountId, Long metricId, Double value) {
        Metric metric = metricRepo.findByMetricId(metricId).orElseThrow(() -> new IllegalArgumentException("Unknown metric"));
        Account acc = accRepo.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Unable to find member"));

        MetricEntry entry = MetricEntry.builder()
                .metric(metric)
                .account(acc)
                .value(value)
                .timestamp(Instant.now())
                .build();

        entryRepo.save(entry);
    }

    @Transactional
    public List<MetricEntry> getMetricHistory(Long accountId) {
        Account acc = accRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find member"));
        return entryRepo.findByAccount_AccountId(acc.getAccountId());
    }

    @Transactional
    public MetricEntry getLatestMetric(Long accountId, Long metricId) {
        return entryRepo.findTopByAccount_AccountIdAndMetric_MetricIdOrderByTimestampDesc(accountId, metricId)
                .orElse(null);
    }

    public List<Metric> getAllMetrics() {
        return metricRepo.findAll();
    }



}
