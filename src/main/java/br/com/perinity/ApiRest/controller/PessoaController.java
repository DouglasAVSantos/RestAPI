package br.com.perinity.ApiRest.controller;


import br.com.perinity.ApiRest.Responses.PessoaPeriodoResponse;
import br.com.perinity.ApiRest.Responses.PessoasResponse;
import br.com.perinity.ApiRest.Responses.Response;
import br.com.perinity.ApiRest.departamento.Departamento;
import br.com.perinity.ApiRest.departamento.DepartamentoRepository;
import br.com.perinity.ApiRest.pessoa.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    PessoaRepository rep;
    @Autowired
    DepartamentoRepository departamentoRepository;

    @PostMapping
    @Transactional
    //Metodo que cadastra uma pessoa
    public ResponseEntity<PessoasResponse> cadastrar(@RequestBody @Valid DadosPessoa dados, UriComponentsBuilder builder) {
        Optional<Departamento> departamentoExiste = departamentoRepository.findById(dados.departamento()); //faz a busca no db pelo id informado no corpo da requisição e atribui seu valor a variavel
        if (departamentoExiste.isPresent()) { //verifica se foi encontrado o departamento no db
            Pessoa pessoa = rep.save(new Pessoa(dados, departamentoExiste.get())); // armazenando a resposta do metodo save em um objeto do tipo Pessoa, instancia um objeto do tipo pessoa e salva as informações da requisição
            URI uri = builder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri(); // gera a URI com o caminho em que foi criado o objeto
            return ResponseEntity.created(uri).body(new PessoasResponse(pessoa.getNome(), pessoa.getDepartamento().getTitulo(),"Created")); // muda a resposta http para 201 CREATED com a URI no header, e entregando os dados no BODY da DTO informada
        }
        return ResponseEntity.badRequest().body(new PessoasResponse(null,null,"Id do departamento inexistente")); // gera um erro ao cadastrar com BODY de mensagem

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PessoasResponse> alterar(@RequestBody @Valid DadosPessoaAtualizar dados, @PathVariable Long id){
        try {
            Departamento departamento = null;
            Pessoa p = rep.getReferenceById(id);
            if (dados.departamento() != null){
                departamento = departamentoRepository.getReferenceById(dados.departamento());}
            p.atualizarPessoa(dados, departamento);
            return ResponseEntity.ok(new PessoasResponse(p.getNome(),p.getDepartamento().getTitulo(),"Altered"));
        } catch (EntityNotFoundException ex){
            return ResponseEntity.badRequest().body(new PessoasResponse(null,null,"Pessoa não encontrada"));
        }



    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try{
            Pessoa pessoa = rep.getReferenceById(id);
            rep.delete(pessoa);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Transactional
    public Page<ListaPessoas> listaPessoas(@PageableDefault(sort ={"nome"})Pageable paginacao){
        return rep.findAll(paginacao).map(ListaPessoas::new);
    }

    @GetMapping("/gastos")
    @Transactional
    public ResponseEntity<?> pessoasPorPeriodo(@RequestBody @Valid DadosPessoaPeriodo dados){
        try{
            Long media = rep.findByNome(dados.nome()).getPeriodo(dados.inicioPeriodo(),dados.fimPeriodo());
            return ResponseEntity.ok(new PessoaPeriodoResponse(dados.nome(),dados.inicioPeriodo(),dados.fimPeriodo(),media));
        }catch (NullPointerException ex){
            return ResponseEntity.badRequest().body(new Response("pessoa não encontrada"));
        }
    }


}
