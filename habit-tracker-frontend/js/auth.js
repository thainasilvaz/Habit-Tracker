const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

console.log(registerBtn);
console.log(loginBtn);

registerBtn.addEventListener('click', () => {
    container.classList.add("active"); //ao clicar no botão a classe active é
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

async function cadastro(){

    const nome = document.getElementById("nomeCadastro").value
    const email = document.getElementById("emailCadastro").value
    const senha = document.getElementById("senhaCadastro").value

    if(!validarEmail(email)){
        mostrarToast("Email inválido!")
        return
    }

    const resposta = await fetch("http://localhost:8080/usuario", {

        method: "POST",

        headers:{
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            nome: nome,
            email: email,
            senha: senha
        })

    })

    if(!resposta.ok){
        const erro = await resposta.text()
        mostrarToast(erro)
        return
    }

    mostrarToast("Usuário criado com sucesso!")

    container.classList.remove("active")

    const dados = await resposta.json()
    console.log(dados)

}

async function login(){

    console.log("login clicado")

    const email = document.getElementById("emailLogin").value
    const senha = document.getElementById("senhaLogin").value

    const resposta = await fetch("http://localhost:8080/auth/login", {

        method:"POST", //o login vai enviar dados para a API

        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify({ //enviando email e senha -> transforma-se objeto javascript em JSON
            email: email,
            senha: senha
        })

    })

    if(resposta.ok){

    const dados = await resposta.json()

    localStorage.setItem("usuarioId", dados.id) //salvando usuário no navegador -> permite que as outras páginas saibam qual usuário está logado

    window.location.href = "dashboard.html" //redirecionando o usuário

    }else{

    alert("Email ou senha inválidos")

    }

}

function validarEmail(email){
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/ //verificando se o email segue o formato básico de email usando regex
    return regex.test(email)
}