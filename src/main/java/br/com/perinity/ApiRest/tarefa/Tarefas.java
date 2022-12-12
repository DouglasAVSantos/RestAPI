package br.com.perinity.ApiRest.tarefa;

import br.com.perinity.ApiRest.departamento.Departamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name ="tarefas")
@Entity(name = "Tarefas")
@Getter
@EqualsAndHashCode(of ="id")
@NoArgsConstructor
@AllArgsConstructor
public class Tarefas {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

    private LocalDate prazo;
    @OneToOne
    private Departamento departamento;

    private Long duracao;
    private boolean finalizado = false;

    public Tarefas(DadosTarefas d, Departamento dep){
        this.titulo = d.titulo();
        this.descricao =d.descricao();
        this.duracao =d.duracao();
        this.prazo = d.prazo();
        this.departamento = dep;
    }



    public void finalizar() {
        this.finalizado = true;
    }


}

