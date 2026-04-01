package com.thaina.habittracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "registro_habito")

@Getter
@Setter

public class RegistroHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private boolean concluido;

    @JsonIgnore
    @ManyToOne //varios registros pertencem a um habito
    @JoinColumn(name = "id_habito")
    private Habito habito;

    @ManyToOne
    @JsonIgnore //evitando loop infinito de JSON
    private Usuario usuario;

    @Column(name = "id_habito", insertable = false, updatable = false)
    private Long habitoId;

    
}
