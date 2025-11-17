package com.example.projetomodelowilian.repository;

import com.example.projetomodelowilian.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);

    List<Cliente> findByDataCriacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

}