package com.example.projetomodelowilian.service;

import com.example.projetomodelowilian.DTO.ClienteDTO;
import com.example.projetomodelowilian.DTO.PessoaCreateDTO;
import com.example.projetomodelowilian.entity.Cliente;
import com.example.projetomodelowilian.entity.Tecnico;
import com.example.projetomodelowilian.enums.Status;
import com.example.projetomodelowilian.repository.ChamadoRepository;
import com.example.projetomodelowilian.repository.ClienteRepository;
import com.example.projetomodelowilian.repository.TecnicoRepository;
import com.example.projetomodelowilian.service.exceptions.DataIntegrityViolationException;
import com.example.projetomodelowilian.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente criar(PessoaCreateDTO clienteDTO) {
        clienteDTO.setId(null);
        clienteDTO.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        validaPorCpfEEmail(clienteDTO);
        Cliente newCliente = fromDTOCreate(clienteDTO);
        return clienteRepository.save(newCliente);
    }

    public Cliente atualizar(Long id, PessoaCreateDTO clienteDTO) {
        clienteDTO.setId(id);
        Cliente oldCliente = buscarPorId(id);
        // A senha só deve ser atualizada se for fornecida. Lógica adicional pode ser necessária.
        validaPorCpfEEmail(clienteDTO);
        oldCliente = fromDTO(clienteDTO);
        return clienteRepository.save(oldCliente);
    }

    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);

        // Regra: Não permitir exclusão de cliente com chamados em aberto
        if (chamadoRepository.existsByClienteIdAndStatusNot(id, Status.ENCERRADO)) {
            throw new DataIntegrityViolationException("Cliente possui chamados em aberto e não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(PessoaCreateDTO clienteDTO) {
        // Validação de CPF
        Optional<Cliente> clientePorCpf = clienteRepository.findByCpf(clienteDTO.getCpf());
        if (clientePorCpf.isPresent() && !clientePorCpf.get().getId().equals(clienteDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema para um Cliente!");
        }

        Optional<Tecnico> tecnicoPorCpf = tecnicoRepository.findByCpf(clienteDTO.getCpf());
        if (tecnicoPorCpf.isPresent()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema para um Técnico!");
        }

        // Validação de E-mail
        Optional<Cliente> clientePorEmail = clienteRepository.findByEmail(clienteDTO.getEmail());
        if (clientePorEmail.isPresent() && !clientePorEmail.get().getId().equals(clienteDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema para um Cliente!");
        }

        Optional<Tecnico> tecnicoPorEmail = tecnicoRepository.findByEmail(clienteDTO.getEmail());
        if (tecnicoPorEmail.isPresent()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema para um Técnico!");
        }
    }

    private Cliente fromDTO(PessoaCreateDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        // Não atualiza a senha aqui para não sobrescrever com null
        cliente.setPerfis(dto.getPerfis());
        return cliente;
    }

    private Cliente fromDTOCreate(PessoaCreateDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha()); // Define a senha criptografada
        cliente.setPerfis(dto.getPerfis());
        return cliente;
    }
}