package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotNull;

public record AssignBookingDto(
        @NotNull Long bookingId,
        @NotNull Long sessionId
) {}
