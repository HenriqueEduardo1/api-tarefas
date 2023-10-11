package com.tarefas.api.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRecordDto(@NotBlank String title, @NotBlank String description, @NotBlank String responsible, @NotBlank String priority, @NotNull LocalDate deadline) {
    
}
