package tech.selwyn.carleton.comp3005.fitnessclub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 100) String password
) {}
