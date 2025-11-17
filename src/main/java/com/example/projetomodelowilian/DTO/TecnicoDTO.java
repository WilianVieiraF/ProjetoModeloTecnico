package com.example.projetomodelowilian.DTO;

import com.example.projetomodelowilian.entity.Tecnico;
import org.hibernate.validator.constraints.br.CPF;

public class TecnicoDTO extends PessoaDTO {

    public TecnicoDTO() { }

    public TecnicoDTO(Long id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha, null, null);
    }

    public TecnicoDTO(Tecnico entity) {
        super(entity);
    }
}


