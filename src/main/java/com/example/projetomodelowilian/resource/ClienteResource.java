package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.DTO.ClienteCreateDTO;
import com.example.projetomodelowilian.DTO.ClienteDTO;
import com.example.projetomodelowilian.entity.Cliente;
import com.example.projetomodelowilian.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        List<ClienteDTO> clientesDTO = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(new ClienteDTO(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteCreateDTO clienteDTO) {
        Cliente novoCliente = clienteService.create(clienteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoCliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDTO(novoCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteCreateDTO clienteDTO) {
        Cliente clienteAtualizado = clienteService.update(id, clienteDTO);
        if (clienteAtualizado != null) {
            return ResponseEntity.ok(new ClienteDTO(clienteAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
