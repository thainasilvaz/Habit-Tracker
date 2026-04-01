/*
Funções assíncronas (async function): usadas quando o código precisa esperar algo externo acontecer
Exemplos:
chamada de API, leitura de arquivo, acesso a banco de dados, requisição de rede...

se uma função que chama uma API não for assíncrona o navegador trava esperando a resposta

as funções assíncronas permitem usar "await", que significa "espere a resposta chegar"
*/

async function listarUsuarios(){

    const resposta = await fetch("http://localhost:8080/usuario") //frontend chama uma API
    const usuarios = await resposta.json() //convertendo json para objeto js
    const lista = document.getElementById("listaUsuarios")
    lista.innerHTML = "" //remove tudo que está dentro da lista (sem isso cada vez que clicasse no botão itens duplicados seriam adicionados)

    usuarios.forEach(usuario => { //percorrendo todos os elementos da lista
        const item = document.createElement("li")
        item.innerText = usuario.nome + " - " + usuario.email
        lista.appendChild(item) //adiciona <li> dentro de <ul>, ou seja, adiciona os itens na lista e os deixa visíveis

    })

}

async function criarUsuario(){ //função assíncrona para esperar a chamada da API

    const nome = document.getElementById("nome").value //document representa toda a página HTML e .value pega o texto digitado no campo
    const email = document.getElementById("email").value 
    const senha = document.getElementById("senha").value

    const resposta = await fetch("http://localhost:8080/usuario", { //fetch é uma função do javascript usada para fazer requisições HTTP -> permite que o frontend chame uma API
        method: "POST",
        headers: { //informações extras da requisição
            "Content-Type": "application/json" //dizendo para o backend: estou enviando dados em formato JSON
        },
        body: JSON.stringify({ //body: corpo da requisição (onde os dados são enviados) -> o stringify converte objeto javascript para JSON
            nome: nome,
            email: email,
            senha: senha
        })
    })

    const dados = await resposta.json()  //pegando a resposta do backend e convertendo o JSON para um objeto Javascript

    console.log(dados)

}