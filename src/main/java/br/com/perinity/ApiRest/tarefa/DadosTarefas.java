package br.com.perinity.ApiRest.tarefa;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosTarefas(

        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotNull LocalDate prazo,
        @NotNull Long duracao,

        @NotNull @Min(1) @Max(3) Long departamento,

        boolean finalizado
) {
}
