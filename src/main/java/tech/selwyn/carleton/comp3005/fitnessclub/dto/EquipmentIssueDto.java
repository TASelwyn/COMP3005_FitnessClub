package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentIssueDto(
        @NotNull Long equipmentId,
        @NotBlank String issue
) {}
