package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;

public record UpdateProfileDto(
        @Size(max = 50) String firstName,
        @Size(max = 50) String lastName,

        // Goal fields
        String goalTitle,
        Double goalTargetValue,
        Instant goalTargetDate,

        // Metric logging fields
        Long metricId,
        Double metricValue
) {}