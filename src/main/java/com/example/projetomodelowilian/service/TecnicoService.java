package com.example.projetomodelowilian.service;

import com.example.projetomodelowilian.DTO.TecnicoDTO;
import com.example.projetomodelowilian.entity.Cliente;
import com.example.projetomodelowilian.entity.Tecnico;
import com.example.projetomodelowilian.enums.Status;
import com.example.projetomodelowilian.repository.ClienteRepository;
import com.example.projetomodelowilian.repository.ChamadoRepository;
import com.example.projetomodelowilian.repository.TecnicoRepository;
import com.example.projetomodelowilian.service.exceptions.DataIntegrityViolationException;
import com.example.projetomodelowilian.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tecnico findById(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        tecnicoDTO.setSenha(passwordEncoder.encode(tecnicoDTO.getSenha()));
        validaPorCpfEEmail(tecnicoDTO);
        Tecnico newTecnico = fromDTO(tecnicoDTO);
        return tecnicoRepository.save(newTecnico);
    }

    public Tecnico update(Long id, TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico oldTecnico = findById(id);
        tecnicoDTO.setSenha(passwordEncoder.encode(tecnicoDTO.getSenha()));
        validaPorCpfEEmail(tecnicoDTO);
        oldTecnico = fromDTO(tecnicoDTO);
        return tecnicoRepository.save(oldTecnico);
    }

    public void delete(Long id) {
        Tecnico tecnico = findById(id); // Reutiliza o findById para verificar se o técnico existe

        
        if (chamadoRepository.existsByTecnicoIdAndStatusNot(id, Status.ENCERRADO)) {
            throw new DataIntegrityViolationException("Técnico possui chamados em aberto e não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(TecnicoDTO tecnicoDTO) {
        // Validação de CPF
        Optional<Tecnico> tecnicoExistente = tecnicoRepository.findByCpf(tecnicoDTO.getCpf());
        if (tecnicoExistente.isPresent() && !tecnicoExistente.get().getId().equals(tecnicoDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema para um Técnico!");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(tecnicoDTO.getCpf());
        if (clienteExistente.isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema para um Cliente!");
        }

        // Validação de E-mail
        Optional<Tecnico> tecnicoPorEmail = tecnicoRepository.findByEmail(tecnicoDTO.getEmail());
        if (tecnicoPorEmail.isPresent() && !tecnicoPorEmail.get().getId().equals(tecnicoDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema para um Técnico!");
        }

        Optional<Cliente> clientePorEmail = clienteRepository.findByEmail(tecnicoDTO.getEmail());
        if (clientePorEmail.isPresent()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema para um Cliente!");
        }
    }

    private Tecnico fromDTO(TecnicoDTO dto) {
        Tecnico tecnico = new Tecnico();
        tecnico.setId(dto.getId());
        tecnico.setNome(dto.getNome());
        tecnico.setCpf(dto.getCpf());
        tecnico.setEmail(dto.getEmail());
        tecnico.setSenha(dto.getSenha());
        tecnico.setPerfis(dto.getPerfis());
        return tecnico;
    }
}