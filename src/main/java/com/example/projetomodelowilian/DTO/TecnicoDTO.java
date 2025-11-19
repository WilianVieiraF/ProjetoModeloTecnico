package com.example.projetomodelowilian.DTO;

import com.example.projetomodelowilian.entity.Tecnico;

import java.util.HashSet;

public class TecnicoDTO extends PessoaDTO {

    public TecnicoDTO() { }

    public TecnicoDTO(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, new HashSet<>(), null); // A senha será ignorada pelo construtor de PessoaDTO
    }

    public TecnicoDTO(Tecnico entity) {
        super(entity);
    }
}
