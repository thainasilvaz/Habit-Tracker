package com.thaina.habittracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thaina.habittracker.model.Usuario;
import com.thaina.habittracker.repository.UsuarioRepository;

@Service //service: contém a lógica de negócio
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; //a classe UsuarioService controla como o usuarioRepository é usado + final: significa que a referência não pode ser alterada depois de inicializada

    public UsuarioService(UsuarioRepository usuarioRepository){ //construtor que faz a injeção de dependência -> o spring boot cria o objeto UsuarioService -> encontra UsuarioRepository e injeta automaticamente
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario){ //método para salvar usuário no banco
        return usuarioRepository.save(usuario); //o método save() é fornecido pelo String Data JPA -> INSERT INTO usuario caso o id não exista e UPDATE usuario caso o id exista
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll(); //SELECT * FROM usuario
    }

    public Usuario buscarUsuarioPorId(Long id){
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado){

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }


}