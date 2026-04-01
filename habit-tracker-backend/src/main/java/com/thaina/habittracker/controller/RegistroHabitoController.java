package com.thaina.habittracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thaina.habittracker.model.RegistroHabito;
import com.thaina.habittracker.service.RegistroHabitoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/registro")
public class RegistroHabitoController {
    
    private final RegistroHabitoService registroService;

    public RegistroHabitoController(RegistroHabitoService registroService){
        this.registroService = registroService;
    }

    @PostMapping
    public RegistroHabito criarRegistro(@RequestBody RegistroHabito registro){
        return registroService.criarRegistro(registro);
    }

    @GetMapping
    public List<RegistroHabito> listarRegistros(){
        return registroService.listarRegistros();
    }

    @GetMapping("/habito/{habitoId}")
    public List<RegistroHabito> listarRegistrosPorHabito(@PathVariable Long habitoId){
        return registroService.listarRegistrosPorHabito(habitoId);
    }

    @PostMapping("/{habitoId}/concluir")
    public RegistroHabito concluirHabito(@PathVariable Long habitoId, @PathVariable Long usuarioId){
        return registroService.concluirHabitoHoje(habitoId, usuarioId);
    }

    @GetMapping("/hoje/{usuarioId}")
    public List<RegistroHabito> registrosHoje(@PathVariable Long usuarioId){
        return registroService.buscarRegistrosHoje(usuarioId);
    }

    @PostMapping("/{habitoId}/concluir/{usuarioId}")
    public RegistroHabito ConcluirHabito(@PathVariable Long habitoId, @PathVariable Long usuarioId){
        return registroService.concluirHabitoHoje(habitoId, usuarioId);
    }

    @GetMapping("/streak/{usuarioId}")
    public int getStreak(@PathVariable Long usuarioId){
        return registroService.calcularStreak(usuarioId);
    }

    @GetMapping("/estatisticas/{usuarioId}") //api para o front conseguir pegar os dados e criar as estatísticas
    public List<Object[]> estatisticas(@PathVariable Long usuarioId){
        return registroService.obterEstatisticas(usuarioId);
    }

}
