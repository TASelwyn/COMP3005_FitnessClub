package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;

import java.util.List;
import java.util.Optional;

public interface MetricEntryRepository extends JpaRepository<MetricEntry, Long> {
    Optional<MetricEntry> findByEntryId(Long entryId);

    // Fetch all entries for a specific account
    List<MetricEntry> findByAccount_AccountId(Long accountId);

    // Fetch the latest entry for a specific account and metric
    Optional<MetricEntry> findTopByAccount_AccountIdAndMetric_MetricIdOrderByTimestampDesc(Long accountId, Long metricId);

}
