package org.example.provider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

// для заполения БД таблца Клиент
public record ClientDto(
        @NotBlank(message = "имя обязательно")
        String name,
        String address,
        String details,
        @NotNull(message = "Дата регистрации обязательна")
        @PastOrPresent(message = "Дата регистрации не может быть в будущем")
        LocalDate signupDate
) {}
