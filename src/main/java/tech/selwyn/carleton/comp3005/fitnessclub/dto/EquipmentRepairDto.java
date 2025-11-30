package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentRepairDto(
        @NotNull Long issueId,
        @NotBlank String notes
) {}
