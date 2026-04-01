//esse arquivo não mexe no html, ele apenas conversa com a API -> centraliza todas as chamadas para a API


const API_URL = "http://localhost:8080" //variável global com o endereço da API

// buscar usuários
async function buscarUsuarios(){

    const resposta = await fetch(`${API_URL}/usuario`) //GET /usuario
    return resposta.json()

}

// criar usuário
async function criarUsuarioAPI(usuario){ //recebe um objeto usuário -> com nome, email e senha

    const resposta = await fetch(`${API_URL}/usuario`, {

        method: "POST",

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify(usuario)

    })

    return resposta.json()

}

