package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record SetAvailabilityDto(
        @NotNull Instant startTime,
        @NotNull Instant endTime
) {}
