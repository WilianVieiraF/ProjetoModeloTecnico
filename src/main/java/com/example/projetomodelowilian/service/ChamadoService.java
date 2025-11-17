package com.example.projetomodelowilian.service;

import com.example.projetomodelowilian.entity.Chamado;
import com.example.projetomodelowilian.repository.ChamadoRepository;
import com.example.projetomodelowilian.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    public Chamado findById(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    // Outros métodos como create(ChamadoDTO dto) e update(Long id, ChamadoDTO dto)
    // seriam implementados aqui.
    // Ex:
    // public Chamado create(ChamadoDTO dto) { ... }
    // public Chamado update(Long id, ChamadoDTO dto) { ... }

}