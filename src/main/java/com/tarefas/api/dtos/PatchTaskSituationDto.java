package com.tarefas.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record PatchTaskSituationDto(@NotBlank String situation) {
    
}
