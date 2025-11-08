package com.example.projetomodelowilian.repository;

import com.example.projetomodelowilian.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    
    public long contarChamadosPorTecnico(Long idDoTecnico) {
        return chamadoRepository.countByTecnicoId(idDoTecnico);
    }

}

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
}
