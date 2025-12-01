package tech.selwyn.carleton.comp3005.fitnessclub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Account;
import tech.selwyn.carleton.comp3005.fitnessclub.model.Availability;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AccountRepository;
import tech.selwyn.carleton.comp3005.fitnessclub.repository.AvailabilityRepository;

import java.time.Instant;
import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepo;
    private final AccountRepository accountRepo;

    public AvailabilityService(AvailabilityRepository availabilityRepo, AccountRepository accountRepo) {
        this.availabilityRepo = availabilityRepo;
        this.accountRepo = accountRepo;
    }

    /**
     * Adds a new availability slot for a trainer.
     */
    @Transactional
    public Availability setAvailability(Long trainerId, Instant start, Instant end, String note) {

        // Validate times or else it'll throw error
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // Checking for overlapping availability for the same trainer if there is any conflict
        List<Availability> overlaps = availabilityRepo
                .findByTrainer_AccountIdAndEndTimeAfterAndStartTimeBefore(trainerId, start, end);
        if (!overlaps.isEmpty()) {
            throw new IllegalArgumentException("Overlapping availability exists");
        }

        // load the trainer account
        Account trainer = accountRepo.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // Build and save new availability
        Availability availability = Availability.builder()
                .trainer(trainer)
                .startTime(start)
                .endTime(end)
                .note(note)
                .build();

        return availabilityRepo.save(availability);
    }
}
