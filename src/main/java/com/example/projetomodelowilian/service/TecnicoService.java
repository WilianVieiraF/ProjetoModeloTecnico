package com.example.projetomodelowilian.service;

import com.example.projetomodelowilian.DTO.TecnicoDTO;
import com.example.projetomodelowilian.DTO.PessoaCreateDTO;
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

    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! Id: " + id));
    }

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    public Tecnico criar(PessoaCreateDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        tecnicoDTO.setSenha(passwordEncoder.encode(tecnicoDTO.getSenha()));
        validaPorCpfEEmail(tecnicoDTO);
        Tecnico newTecnico = fromDTOCreate(tecnicoDTO);
        return tecnicoRepository.save(newTecnico);
    }

    public Tecnico atualizar(Long id, PessoaCreateDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico oldTecnico = buscarPorId(id);
        // A senha só deve ser atualizada se for fornecida. Lógica adicional pode ser necessária.
        validaPorCpfEEmail(tecnicoDTO);
        oldTecnico = fromDTO(tecnicoDTO);
        return tecnicoRepository.save(oldTecnico);
    }

    public void deletar(Long id) {
        Tecnico tecnico = buscarPorId(id); // Reutiliza o buscarPorId para verificar se o técnico existe

        
        if (chamadoRepository.existsByTecnicoIdAndStatusNot(id, Status.ENCERRADO)) {
            throw new DataIntegrityViolationException("Técnico possui chamados em aberto e não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(PessoaCreateDTO tecnicoDTO) {
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

    private Tecnico fromDTO(PessoaCreateDTO dto) {
        Tecnico tecnico = new Tecnico();
        tecnico.setId(dto.getId());
        tecnico.setNome(dto.getNome());
        tecnico.setCpf(dto.getCpf());
        tecnico.setEmail(dto.getEmail());
        // Não atualiza a senha aqui para não sobrescrever com null
        tecnico.setPerfis(dto.getPerfis());
        return tecnico;
    }

    private Tecnico fromDTOCreate(PessoaCreateDTO dto) {
        Tecnico tecnico = new Tecnico();
        tecnico.setId(dto.getId());
        tecnico.setNome(dto.getNome());
        tecnico.setCpf(dto.getCpf());
        tecnico.setEmail(dto.getEmail());
        tecnico.setSenha(dto.getSenha()); // Define a senha criptografada
        tecnico.setPerfis(dto.getPerfis());
        return tecnico;
    }
}