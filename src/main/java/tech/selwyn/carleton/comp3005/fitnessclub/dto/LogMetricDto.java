package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;

public record LogMetricDto(
        @NotBlank Long metricId,
        @NotBlank Double value
) {}
