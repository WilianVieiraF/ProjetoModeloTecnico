package com.example.projetomodelowilian.DTO;

import com.example.projetomodelowilian.entity.Cliente;

import java.util.HashSet;

public class ClienteDTO extends PessoaDTO {

    public ClienteDTO() { }

    public ClienteDTO(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, new HashSet<>(), null); // A senha será ignorada pelo construtor de PessoaDTO
    }

    public ClienteDTO(Cliente entity) {
        super(entity);
    }
}
