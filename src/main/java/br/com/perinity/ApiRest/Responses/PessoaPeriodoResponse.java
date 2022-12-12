package br.com.perinity.ApiRest.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record PessoaPeriodoResponse(String nome, @JsonProperty("data_de_inicio") LocalDate inicio, @JsonProperty("data_de_fim") LocalDate fim, @JsonProperty("media_de_horas_gastas_por_tarefa") Long media) {

    }

