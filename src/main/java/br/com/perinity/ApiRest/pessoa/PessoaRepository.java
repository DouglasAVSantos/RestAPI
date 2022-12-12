package br.com.perinity.ApiRest.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

    Pessoa findByNome(String nome);

}
