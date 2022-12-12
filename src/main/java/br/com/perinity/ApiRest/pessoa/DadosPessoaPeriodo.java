package br.com.perinity.ApiRest.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosPessoaPeriodo(

        @NotBlank String nome,
        @NotNull LocalDate inicioPeriodo,
        @NotNull LocalDate fimPeriodo
) {
}
