package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

public record MemberSummaryDto(
        @NotBlank Long accountId,
        String fullName,
        String email,
        Map<String, Object> currentGoal,
        List<Map<String, Object>> latestMetrics
) {}
