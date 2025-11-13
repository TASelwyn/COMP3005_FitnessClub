package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;

public record LookupMemberDto(
        @NotBlank String name,
        @NotBlank int limit,
        @NotBlank int offset
) {}
