package com.example.projetomodelowilian.service;

import com.example.projetomodelowilian.DTO.ChamadoDTO;
import com.example.projetomodelowilian.entity.Chamado;
import com.example.projetomodelowilian.entity.Cliente;
import com.example.projetomodelowilian.entity.Tecnico;
import com.example.projetomodelowilian.enums.Status;
import com.example.projetomodelowilian.repository.ChamadoRepository;
import com.example.projetomodelowilian.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService; 

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! Id: " + id));
    }

    public List<Chamado> listarTodos() {
        return chamadoRepository.findAll();
    }

    public Chamado criar(@Valid ChamadoDTO dto) {
        return chamadoRepository.save(fromDTO(dto));
    }

    public Chamado atualizar(Long id, @Valid ChamadoDTO dto) {
        dto.setId(id);
        Chamado oldChamado = buscarPorId(id);
        oldChamado = fromDTO(dto);
        return chamadoRepository.save(oldChamado);
    }

    public void deletar(Long id) {
        
        buscarPorId(id);
        chamadoRepository.deleteById(id);
    }

    private Chamado fromDTO(ChamadoDTO dto) {
        Chamado chamado = new Chamado();
        if (dto.getId() != null) {
            chamado.setId(dto.getId());
        }

       
        if (dto.getStatus().equals(Status.ENCERRADO)) {
            chamado.setDataFechamento(LocalDateTime.now());
        }

        
        Tecnico tecnico = tecnicoService.buscarPorId(dto.getTecnico().getId());
        Cliente cliente = clienteService.buscarPorId(dto.getCliente().getId());

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());

        
        if (dto.getId() != null) {
            chamado.setDataAbertura(buscarPorId(dto.getId()).getDataAbertura());
        }

        return chamado;
    }
}