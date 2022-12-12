package br.com.perinity.ApiRest.tarefa;

import jakarta.validation.constraints.NotNull;

public record TarefaAlocar(@NotNull Long idTarefa) {
}
