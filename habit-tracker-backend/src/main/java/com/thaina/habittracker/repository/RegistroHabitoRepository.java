package com.thaina.habittracker.repository;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thaina.habittracker.model.RegistroHabito;

public interface RegistroHabitoRepository extends JpaRepository<RegistroHabito, Long> {

    List<RegistroHabito> findByHabitoId(Long habitoId);

    List<RegistroHabito> findByUsuarioIdAndData(Long usuarioId, LocalDate data);

    boolean existsByUsuarioIdAndHabitoIdAndData(Long usuarioId, Long habitoId, LocalDate data); //o spring criará SELECT COUNT(*) FRON registro_habito WHERE usuario_id = ? AND habito_id = ? AND data = ?
    //ou seja, acessa a tabela registro_habito e filtra os registros onde: usuario_id, habito_id e data são os valores específicos do parametro e retorna quantos registros existem que atendem a esses critérios

    List<RegistroHabito> findByUsuarioIdOrderByDataDesc(Long usuarioId);

    //buscando no banco a data e a quantidade de habitos concluídos no dia (obs: o GROUP BY divide os dados em subconjuntos baseados em valores iguais; order by orderna o conjunto de resultados de uma consulta com base em uma ou mais colunas)
    //o grafico de estatísticas precisa saber quantos hábitos o usuário concluiu em cada dia
    @Query("""
    SELECT r.data, COUNT(r)
    FROM RegistroHabito r
    WHERE r.usuario.id = :usuarioId
    GROUP BY r.data
    ORDER BY r.data
    """)
    List<Object[]> contarPorData(Long usuarioId);  // [data, quantidade] -> ex: ["2026-03-20", 5]
}