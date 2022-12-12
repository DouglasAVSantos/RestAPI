package br.com.perinity.ApiRest.Responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosTarefasResponse(

        @NotBlank String titulo,
        @NotBlank String descricao,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate prazo,
        @NotNull Long duracao,
        @NotBlank String departamento,
        @NotNull boolean finalizado,
        String mensagem
) {
}
