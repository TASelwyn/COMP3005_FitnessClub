package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record UpdateProfileDto(
        @Size(max = 50) String firstName,
        @Size(max = 50) String lastName,
        String email // optional
//        String goalTitle,
//        Double goalTargetValue,
//        Instant goalTargetDate
) {}