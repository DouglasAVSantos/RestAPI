package br.com.perinity.ApiRest.controller;

import br.com.perinity.ApiRest.departamento.DadosDepartamento;
import br.com.perinity.ApiRest.departamento.Departamento;
import br.com.perinity.ApiRest.departamento.DepartamentoRepository;
import br.com.perinity.ApiRest.pessoa.Pessoa;
import br.com.perinity.ApiRest.pessoa.PessoaRepository;
import br.com.perinity.ApiRest.tarefa.Tarefas;
import br.com.perinity.ApiRest.tarefa.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/departamento")
@RestController
public class DepartamentoController {
    @Autowired
    DepartamentoRepository departamentoRepository;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    TarefasRepository tarefasRepository;

    @GetMapping
    public List<DadosDepartamento> departamento() {
        List<Pessoa> pessoa = pessoaRepository.findAll();
        List<Tarefas> tarefa = tarefasRepository.findAll();
        List<Departamento> departamento = departamentoRepository.findAll();
        List<DadosDepartamento> lista = new ArrayList<>();
        for (Departamento valor: departamento){
            DadosDepartamento dados = new DadosDepartamento();
            dados.setDepartamento(valor);
            int p1 = pessoa.stream().filter(p ->p.getDepartamento().getId().equals( valor.getId())).toList().size();
            int t1 = tarefa.stream().filter(p ->p.getDepartamento().getId().equals( valor.getId())).toList().size();
            dados.setPessoa(p1);
            dados.setTarefas(t1);
            lista.add(dados);
        }
        return lista;
    }
}


