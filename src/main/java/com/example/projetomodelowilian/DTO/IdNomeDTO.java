package com.example.projetomodelowilian.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class IdNomeDTO {

    @NotNull(message = "Id é obrigatório")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String nome;

    public IdNomeDTO() { }

    public IdNomeDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}


