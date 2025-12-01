package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LogMetricDto(
        @NotNull Long metricId,
        @NotNull @Positive Double value
) {}
