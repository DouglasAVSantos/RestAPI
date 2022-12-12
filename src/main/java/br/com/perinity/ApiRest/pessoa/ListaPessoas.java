package br.com.perinity.ApiRest.pessoa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ListaPessoas(String nome, String departamento,@JsonProperty("total_de_horas_gastas_nas_tarefas") Long totalHoras){

    public ListaPessoas(Pessoa p){
    this(p.getNome(),p.getDepartamento().getTitulo(),p.getTotalduracao());
    }

}

