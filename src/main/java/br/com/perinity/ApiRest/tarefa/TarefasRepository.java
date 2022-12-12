package br.com.perinity.ApiRest.tarefa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    List<Tarefas> findByFinalizadoOrderByPrazoAsc(boolean finalizado);

}
