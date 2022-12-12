package br.com.perinity.ApiRest.pessoa;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DadosPessoa(

        @NotBlank String nome,
        @NotNull @Min(1) @Max(3) Long departamento
) {
}
