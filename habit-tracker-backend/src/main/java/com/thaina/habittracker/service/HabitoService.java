package com.thaina.habittracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thaina.habittracker.model.Habito;
import com.thaina.habittracker.repository.HabitoRepository;

@Service
public class HabitoService {

    private final HabitoRepository habitoRepository; //o service precisa do repository para acessar o banco

    public HabitoService(HabitoRepository habitoRepository){ //injeção de dependência por construtor
        //colocando o repository recebido dentro do atributo da classe
        this.habitoRepository = habitoRepository; //this.habitoRepository -> variável da classe; habitoRepository -> parâmetro do construtor
    }

    public Habito criarHabito(Habito habito){ //recebendo o objeto Habito
        return habitoRepository.save(habito); //INSERT INTO habito
    }

    public List<Habito> listarHabitos(){
        return habitoRepository.findAll(); //SELECT * FROM habito
    }

    public void deletarHabito(Long id){
        habitoRepository.deleteById(id);
    }

    public List<Habito> listarHabitosPorUsuario(Long usuarioId){
    return habitoRepository.findByUsuarioId(usuarioId);
    }

    public Habito atualizarHabito(Long id, Habito novoHabito){

    Habito existente = habitoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

    existente.setNome(novoHabito.getNome());
    existente.setDiasSemana(novoHabito.getDiasSemana());

    return habitoRepository.save(existente);
    }
    
}
