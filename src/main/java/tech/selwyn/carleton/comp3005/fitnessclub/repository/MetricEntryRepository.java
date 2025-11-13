package tech.selwyn.carleton.comp3005.fitnessclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.model.MetricEntry;

import java.util.Optional;

public interface MetricEntryRepository extends JpaRepository<MetricEntry, Long> {
    Optional<MetricEntry> findByEntryId(Long entryId);
}
