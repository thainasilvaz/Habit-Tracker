package com.thaina.habittracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.thaina.habittracker.model.Habito;
import com.thaina.habittracker.model.RegistroHabito;
import com.thaina.habittracker.model.Usuario;
import com.thaina.habittracker.repository.HabitoRepository;
import com.thaina.habittracker.repository.RegistroHabitoRepository;
import com.thaina.habittracker.repository.UsuarioRepository;


@Service
public class RegistroHabitoService {
    
    private final RegistroHabitoRepository registroRepository;
    private final HabitoRepository habitoRepository;
    private final UsuarioRepository usuarioRepository;

    public RegistroHabitoService(RegistroHabitoRepository registroRepository, HabitoRepository habitoRepository, UsuarioRepository usuarioRepository){
        this.registroRepository = registroRepository;
        this.habitoRepository = habitoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RegistroHabito criarRegistro(RegistroHabito registro){

        Habito habito = habitoRepository.findById(registro.getHabito().getId())
                .orElseThrow(() -> new RuntimeException("Habito não encontrado"));

        registro.setHabito(habito);

        registro.setData(LocalDate.now());

        return registroRepository.save(registro);
    }

    public List<RegistroHabito> listarRegistros(){
        return registroRepository.findAll();
    }

    public List<RegistroHabito> listarRegistrosPorHabito(Long habitoId){
        return registroRepository.findByHabitoId(habitoId);
    }

    public RegistroHabito concluirHabitoHoje(Long habitoId, Long usuarioId){

    Habito habito = habitoRepository.findById(habitoId)
            .orElseThrow();

    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow();
    
    //criando a validação para que um usuário não consiga concluir o mesmo hábito mais de uma vez no dia        
    LocalDate hoje = LocalDate.now();

    boolean jaExiste = registroRepository
            .existsByUsuarioIdAndHabitoIdAndData(usuarioId, habitoId, hoje);

    if(jaExiste){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hábito já marcado como concluído hoje");
    }

    RegistroHabito registro = new RegistroHabito();

    registro.setHabito(habito);
    registro.setUsuario(usuario);
    registro.setData(LocalDate.now());
    registro.setConcluido(true);

    return registroRepository.save(registro);

    }

    public List<RegistroHabito> buscarRegistrosHoje(Long usuarioId){ // SELECT * FROM registro WHERE usuario_id = ? AND data = ?

        LocalDate hoje = LocalDate.now();

        return registroRepository.findByUsuarioIdAndData(usuarioId, hoje);

    }

    public int calcularStreak(Long usuarioId){

    List<RegistroHabito> registros = registroRepository.findByUsuarioIdOrderByDataDesc(usuarioId);

    if(registros.isEmpty()) return 0;

    int streak = 0;

    LocalDate hoje = LocalDate.now();

    for(int i = 0; i < registros.size(); i++){

        RegistroHabito r = registros.get(i);

        if(r.getData().equals(hoje.minusDays(streak))){
            streak++;
        }else{
            break;
        }
    }

    return streak;
}

    public List<Object[]> obterEstatisticas(Long usuarioId){
        return registroRepository.contarPorData(usuarioId);
    }
    
}
