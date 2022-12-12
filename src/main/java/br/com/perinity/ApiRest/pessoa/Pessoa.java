package br.com.perinity.ApiRest.pessoa;

import br.com.perinity.ApiRest.departamento.Departamento;
import br.com.perinity.ApiRest.tarefa.Tarefas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Table(name ="pessoas")
@Entity(name = "Pessoa")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToOne
    private Departamento departamento;


    @OneToMany
    @JoinColumn(name = "id_pessoa")
    private List<Tarefas> tarefas;


    public Pessoa(DadosPessoa dados,Departamento d) {
        this.nome = dados.nome();
        this.departamento = d;
    }

    public void atualizarPessoa(DadosPessoaAtualizar dados, Departamento d){
        if (dados.nome() != null){
            this.nome = dados.nome();}
        if (d != null){
            this.departamento = d;
        }

    }

    public Long getTotalduracao(){
        Long total = 0L ;
        for (Tarefas id : this.tarefas) {
            if (id.isFinalizado())
                total += id.getDuracao();
        } return total;



    }

    public Long getPeriodo(LocalDate inicioPeriodo, LocalDate fimPeriodo) {
        Long total = 0L;
        int tar = 0;
        for (Tarefas id : this.tarefas) {
            LocalDate date = id.getPrazo();
            if (id.isFinalizado()){
                if(date.isAfter(inicioPeriodo) || date.isBefore(fimPeriodo)){
                    total += id.getDuracao();
                    tar++;
                }
            }}
        tar = (tar == 0) ? 1 : tar;
        return total/tar;
    }

}



