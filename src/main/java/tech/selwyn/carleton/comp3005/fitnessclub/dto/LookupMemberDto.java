package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LookupMemberDto(
        @NotBlank String name,
        @Min(0) int limit,
        @Min(0) int offset
) {}
