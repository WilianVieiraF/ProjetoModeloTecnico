package com.example.projetomodelowilian.repository;

import com.example.projetomodelowilian.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Optional<Tecnico> findByCpf(String cpf);
    Optional<Tecnico> findByEmail(String email);
}
