package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;

import java.util.List;
import java.util.Optional;

public interface HealthFocusRepository extends JpaRepository<MetricEntry, Long> {
    Optional<MetricEntry> findByEntryId(Long entryId);

    List<MetricEntry> findByAccountAccountIdOrderByTimestampDesc(Long accountId);

    Optional<MetricEntry> findTopByAccount_AccountIdAndMetric_MetricIdOrderByTimestampDesc(Long accountId, Long metricId);

}
