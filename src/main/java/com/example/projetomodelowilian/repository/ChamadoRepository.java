package com.example.projetomodelowilian.repository;

import com.example.projetomodelowilian.entity.Chamado;
import com.example.projetomodelowilian.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // Busca uma lista de chamados por uma data de abertura específica
    List<Chamado> findByDataAbertura(LocalDateTime dataAbertura);

    // Busca uma lista de chamados que estão especificamente com o status ABERTO
    @Query("SELECT c FROM Chamado c WHERE c.status = 'ABERTO'")
    List<Chamado> findAllByStatusAberto();

    // Conta o número de chamados associados a um técnico específico pelo seu ID
    long countByTecnicoId(Long tecnicoId);

    // Verifica se existe algum chamado para um técnico que não esteja com o status ENCERRADO
    boolean existsByTecnicoIdAndStatusNot(Long tecnicoId, Status status);

    // Verifica se existe algum chamado para um cliente que não esteja com o status ENCERRADO
    boolean existsByClienteIdAndStatusNot(Long clienteId, Status status);
}
