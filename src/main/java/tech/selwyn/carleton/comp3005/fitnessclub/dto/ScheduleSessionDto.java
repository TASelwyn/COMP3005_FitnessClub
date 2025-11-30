package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record ScheduleSessionDto(
        @NotBlank Long trainerId,
        Instant startTime,
        Instant endTime
) {}
