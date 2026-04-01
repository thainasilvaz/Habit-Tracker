package com.thaina.habittracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "habito")

@Getter
@Setter

public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String diasSemana;

    @ManyToOne //varios habitos pertencem a um usuario
    @JoinColumn(name = "id_usuario") //chave estrangeira
    private Usuario usuario; //o JPA entende que "usuario" é um objeto

    //o cascade faz com que todos os registros sejam deletados junto com o hábito
    @OneToMany(mappedBy = "habito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RegistroHabito> registros;

    
}
