package com.example.projetomodelowilian.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO que estende PessoaDTO e adiciona o campo de senha.
 * Usado especificamente para a criação de novas Pessoas (Clientes ou Técnicos),
 * permitindo que a senha seja recebida e validada sem ser exposta em outros DTOs.
 */
public class PessoaCreateDTO extends PessoaDTO {

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;

    public PessoaCreateDTO() {
        super();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}