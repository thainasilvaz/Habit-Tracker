package com.thaina.habittracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; //spring data ja cria automaticamente: save(), findAll(), findById(), delete()
import com.thaina.habittracker.model.Habito;

public interface HabitoRepository extends JpaRepository<Habito, Long> {

    List<Habito> findByUsuarioId(Long usuarioId); //SELECT * FROM habito WHERE usuario_id = ? (quem faz isso é o Spring Data JPA)
}
