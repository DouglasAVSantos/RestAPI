package br.com.perinity.ApiRest.controller;


import br.com.perinity.ApiRest.Responses.DadosTarefasResponse;
import br.com.perinity.ApiRest.Responses.PessoaTarefaOkResponse;
import br.com.perinity.ApiRest.Responses.PessoaTarefaResponse;
import br.com.perinity.ApiRest.Responses.Response;
import br.com.perinity.ApiRest.departamento.Departamento;
import br.com.perinity.ApiRest.departamento.DepartamentoRepository;
import br.com.perinity.ApiRest.pessoa.Pessoa;
import br.com.perinity.ApiRest.pessoa.PessoaRepository;
import br.com.perinity.ApiRest.tarefa.DadosTarefas;
import br.com.perinity.ApiRest.tarefa.TarefaAlocar;
import br.com.perinity.ApiRest.tarefa.Tarefas;
import br.com.perinity.ApiRest.tarefa.TarefasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/tarefas")
@RestController
public class TarefaController {
    @Autowired
    TarefasRepository tarefasRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosTarefasResponse> adicionar(@RequestBody @Valid DadosTarefas dados){
        Departamento dep = departamentoRepository.getReferenceById(dados.departamento());
        tarefasRepository.save(new Tarefas(dados, dep));
        return ResponseEntity.ok(new DadosTarefasResponse(dados.titulo(), dados.descricao(), dados.prazo(), dados.duracao(), dep.getTitulo(), dados.finalizado(), "Created"));
    }

    @PutMapping(value = "/alocar/{id}",produces = {"application/json"})
    @Transactional

    public ResponseEntity<?> alocarPessoa(@RequestBody @Valid TarefaAlocar dados ,@PathVariable @NotNull Long id) {
        Optional<Pessoa> p = pessoaRepository.findById(id);
        Optional<Tarefas> t = tarefasRepository.findById(dados.idTarefa());

        if (p.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response("Não foi possivel localizar o id da pessoa : " + id));
        }
        if (t.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response("Não foi possivel localizar o id da tarefa : " + dados.idTarefa()));
        }
        Pessoa pessoa = p.get();
        Tarefas tarefa = t.get();

        if (pessoa.getDepartamento().getId() == tarefa.getDepartamento().getId()){
            pessoa.getTarefas().add(t.get());
            pessoaRepository.save(pessoa);
            return ResponseEntity.ok(new PessoaTarefaOkResponse(pessoa.getNome(),tarefa.getTitulo()));
        }
        return ResponseEntity.badRequest().body(new PessoaTarefaResponse("Departamentos incompativeis",pessoa.getDepartamento().getId(),tarefa.getDepartamento().getId()));


    }


    @PutMapping("/finalizar/{id}")
    @Transactional
    public ResponseEntity<?>finalizarTarefa(@PathVariable @NotNull Long id){
        try{
            Tarefas tarefa = tarefasRepository.getReferenceById(id);
            if (tarefa.isFinalizado()){
                return ResponseEntity.badRequest().body(new Response("A tarefa ja foi finalizada"));
            }
            tarefa.finalizar();
            return ResponseEntity.ok(new Response("Tarefa: " + id+" Finalizada."));

        }catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body(new Response("Não foi possivel localizar a tarefa de id: " + id));
        }
    }

    @GetMapping("/pendentes")
    @Transactional
    public List<Tarefas> listarTarefas(){
        return tarefasRepository.findByFinalizadoOrderByPrazoAsc(false).stream().limit(3).toList();
    }
}
