package br.com.perinity.ApiRest.pessoa;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record DadosPessoaAtualizar(

        String nome,
        @Min(1) @Max(3) Long departamento
) {
}
