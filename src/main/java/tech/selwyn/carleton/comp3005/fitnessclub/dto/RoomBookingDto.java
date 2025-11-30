package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RoomBookingDto(
        @NotNull Long roomId,
        @NotNull Instant startTime,
        @NotNull Instant endTime
) {}
