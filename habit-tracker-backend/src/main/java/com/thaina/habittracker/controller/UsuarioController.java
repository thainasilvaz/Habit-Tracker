package com.thaina.habittracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thaina.habittracker.model.Usuario;
import com.thaina.habittracker.service.UsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*") //permite requisições de qualquer origem -> evita CORS (Cross-Origin Resource Sharing) -> política de segurança do navegador
@RestController
@RequestMapping("/usuario") //rota base do controller
public class UsuarioController { //controla todas as requisições relacionadas a usuários

    private final UsuarioService usuarioService; //declarando dependência

    public UsuarioController(UsuarioService usuarioService){ //construtor usado para injeção de dependência -> cria UsuarioService e injeta no controller
        this.usuarioService = usuarioService;
    }

    @PostMapping //endpoint HTTP POST
    //obs: ResponseEntity<?> é um objeto que representa a resposta HTTP completa -> controla status (200, 400..), body (dados) e headers
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario){ //@RequestBody indica que o corpo da requisição (json) será convertido para um objeto java, @Valid valida o objeto Usuario antes de usar
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario); //controller relega a lógica para o service. o .ok é um atalho do spring e significa HTTP 200 OK + body
    }

    //interceptando erro de validação de email
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body("Email inválido!");
    }

    @GetMapping //endpoint GET
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios(); //service chama repository.findAll()
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id){ //@PathVariable serve para extrair valores diretamente da URL da requisição
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
    }
}


