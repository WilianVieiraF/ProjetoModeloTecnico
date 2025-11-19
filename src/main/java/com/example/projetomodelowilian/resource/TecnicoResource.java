package com.example.projetomodelowilian.resource;

import com.example.projetomodelowilian.DTO.TecnicoCreateDTO;
import com.example.projetomodelowilian.DTO.TecnicoDTO;
import com.example.projetomodelowilian.entity.Tecnico;
import com.example.projetomodelowilian.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> listarTodos() {
        List<Tecnico> tecnicos = tecnicoService.listarTodos();
        List<TecnicoDTO> tecnicosDTO = tecnicos.stream().map(TecnicoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(tecnicosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoDTO> buscarPorId(@PathVariable Long id) {
        Tecnico tecnico = tecnicoService.buscarPorId(id);
        if (tecnico != null) {
            return ResponseEntity.ok(new TecnicoDTO(tecnico));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> criar(@Valid @RequestBody TecnicoCreateDTO tecnicoDTO) {
        Tecnico novoTecnico = tecnicoService.create(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoTecnico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TecnicoDTO(novoTecnico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TecnicoCreateDTO tecnicoDTO) {
        Tecnico tecnicoAtualizado = tecnicoService.update(id, tecnicoDTO);
        if (tecnicoAtualizado != null) {
            return ResponseEntity.ok(new TecnicoDTO(tecnicoAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
