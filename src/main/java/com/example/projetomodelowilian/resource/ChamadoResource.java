package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.DTO.ChamadoDTO;
import com.example.projetomodelowilian.entity.Chamado;
import com.example.projetomodelowilian.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamados")
public class ChamadoResource {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> listarTodos() {
        List<Chamado> chamados = chamadoService.listarTodos();
        List<ChamadoDTO> chamadosDTO = chamados.stream().map(ChamadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(chamadosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO> buscarPorId(@PathVariable Long id) {
        Chamado chamado = chamadoService.buscarPorId(id); // Deixa a exceção ser lançada se não encontrar
        return ResponseEntity.ok(new ChamadoDTO(chamado));
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> criar(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        Chamado novoChamado = chamadoService.create(chamadoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoChamado.getId()).toUri();
        return ResponseEntity.created(uri).body(new ChamadoDTO(novoChamado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ChamadoDTO chamadoDTO) {
        Chamado chamadoAtualizado = chamadoService.atualizar(id, chamadoDTO);
        return ResponseEntity.ok(new ChamadoDTO(chamadoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        chamadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}