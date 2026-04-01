package com.thaina.habittracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
//esses imports permitem usar as anotações @Entity e @Table nas classes definir como elas se relacionam com o banco de dados
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter; 

@Entity //diz ao JPA que essa classe representa uma tabela do banco
@Table(name = "usuario")

//o lombok gera os getters e setters automaticamente durante a compilação
@Getter
@Setter

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //estratégia: autoincrement
    private Long id;

    @Column(nullable = false)
    private String nome;

    @NotBlank //vem da dependência spring-boot-starter-validation -> impede que o campo seja vazio, só espaços ou null
    @Email(message = "Email inválido")  //vem da dependência spring-boot-starter-validation -> valida se o campo segue o formato padrão de email (com mensagem personalizada quando falhar)
    @Column(unique = true)
    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //a senha não será retornada pela API
    @Column(nullable = false)
    private String senha;

    //fazendo o relacionamento entre Usuario e Habitos
    @OneToMany(mappedBy = "usuario") //um usuário pode ter vários hábitos
    @JsonIgnore //quando ele retornar Usuario ele não inclui habitos automaticamente (evita loops)
    private List<Habito> habitos;
}
