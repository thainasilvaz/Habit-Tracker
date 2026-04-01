package com.thaina.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thaina.habittracker.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{ //<entidade, chave primaria>

    Usuario findByEmailAndSenha(String email, String senha);
    
}