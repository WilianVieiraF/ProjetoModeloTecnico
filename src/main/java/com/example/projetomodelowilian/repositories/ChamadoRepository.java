package com.example.projetomodelowilian.repositories;

import com.example.projetomodelowilian.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
}
