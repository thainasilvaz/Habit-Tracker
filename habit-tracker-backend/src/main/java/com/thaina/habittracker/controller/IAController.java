package com.thaina.habittracker.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
public class IAController {

@GetMapping("/sugestao")
public String sugestao(){

    String[] sugestoes = {
        "Beber 2 litros de água",
        "Caminhar por 20 minutos",
        "Estudar programação por 30 minutos",
        "Ler 10 páginas de um livro",
        "Dormir antes das 23h",
        "Meditar por 5 minutos",
        "Evitar redes sociais por 1 hora",
        "Organizar seu dia pela manhã"
    };

    int random = new Random().nextInt(sugestoes.length);

    return sugestoes[random];
}
}