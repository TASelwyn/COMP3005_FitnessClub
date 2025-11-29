package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LogMetricDto(
        @NotNull Long metricId, //@NotNull bcz NotBlank doesn't work long or double only works for string
        @NotNull @Positive Double value //@NotNull
) {}
