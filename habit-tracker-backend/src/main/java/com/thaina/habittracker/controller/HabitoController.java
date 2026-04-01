package com.thaina.habittracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thaina.habittracker.model.Habito;
import com.thaina.habittracker.service.HabitoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/habito")
public class HabitoController {

    private final HabitoService habitoService; //o controller precisa do service para executar a lógica

    public HabitoController(HabitoService habitoService){
        this.habitoService = habitoService;
    }

    @PostMapping //endpoint post 
    public Habito criarHabito(@RequestBody Habito habito){
        return habitoService.criarHabito(habito);
    }

    @GetMapping
    public List<Habito> listarHabitos(){
        return habitoService.listarHabitos();
    }

    @DeleteMapping("/{id}")
    public void deletarHabito(@PathVariable Long id){
        habitoService.deletarHabito(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Habito> listarHabitosPorUsuario(@PathVariable Long usuarioId){
        return habitoService.listarHabitosPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public Habito atualizarHabito(@PathVariable Long id, @RequestBody Habito habito){
        return habitoService.atualizarHabito(id, habito);
    }
}
