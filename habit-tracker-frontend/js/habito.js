let habitosConcluidosHoje = [] //variável global utilizada na função carregarConcluidosHoje
let habitoEditando = null
let habitoParaDeletar = null

function diaAtual(){

const dias = ["DOM","SEG","TER","QUA","QUI","SEX","SAB"]

const hoje = new Date().getDay() 

return dias[hoje] //se hoje = terça retorna = TER

}

async function listarHabitos(){

    await carregarIdsConcluidosHoje()

    const resposta = await fetch("http://localhost:8080/habito/usuario/" + usuarioId) //GET /habito
    const habitos = await resposta.json()
    const lista = document.getElementById("listaHabitos")

    lista.innerHTML = ""

    const hoje = diaAtual()

    habitos.forEach(habito => { //listando todos os hábitos

        if(!habito.diasSemana){ //evitando erro com o includes
            return
        }

        //mostrar apenas hábitos do dia
        if(!habito.diasSemana.includes(hoje)){
            return
        }

        const item = document.createElement("li")
        const botao = document.createElement("button")

        const jaConcluido = habitosConcluidosHoje.includes(habito.id)

        if(jaConcluido){
            botao.innerText = "✔ Concluído"
            botao.classList.add("botao-concluido")
            botao.disabled = true

            botao.animate([
                {transform: "scale(1)"},
                {transform: "scale(1.2)"},
                {transform: "scale(1)"}
            ],{
                duration:300
            })
        }
        else{
            botao.innerText = "Concluir"
            botao.onclick = () => concluirHabito(habito.id) //no registro.js
        }

        item.innerText = habito.nome + " "

        item.appendChild(botao)

        lista.appendChild(item)

    })
}


async function criarHabito(){

    const nomeHabito = document.getElementById("nomeHabitoModal").value
    
    const usuarioId = localStorage.getItem("usuarioId")

    const checkboxes = document.querySelectorAll(".diasSemana input:checked") //pegando os dias selecionados -> inputs marcados dentro da div diasSemana

    let dias = []

    checkboxes.forEach(c => { //percorre todos os checkboxes selecionados e adiciona no array dias
        dias.push(c.value)
    })

    const diasString = dias.join(",") //transformando o array em string 

    if(!nomeHabito){
        mostrarToast("Digite um nome para o hábito")
        return
    }

    const resposta = await fetch("http://localhost:8080/habito", { //requisição HTTP

        method: "POST",
        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify({
            nome: nomeHabito,
            diasSemana: diasString,
            usuario:{
                id: usuarioId
            }
        })
    })

    const dados = await resposta.json()

    console.log(dados)

    // limpar campos
    document.getElementById("nomeHabitoModal").value = ""

    //desmarcar checkboxes
    checkboxes.forEach(c => c.checked = false)

    // atualizar lista
    await listarHabitos()
    await atualizarDashboard()
    
    //fechar modal de criação de novo hábito
    fecharModal()

}

async function carregarIdsConcluidosHoje(){
    const usuarioId = localStorage.getItem("usuarioId")
    const resposta = await fetch("http://localhost:8080/registro/hoje/"+usuarioId)
    const registros = await resposta.json()

    //PARA DEBUGAR
    console.log(registros)

    habitosConcluidosHoje = registros.map(r => Number(r.habitoId))//pegando só o id dos hábitos (obs: a função map percorre uma lista e cria uma nova lista transformada -> para cada item r, pega r.habito.id)

    console.log("Concluídos hoje:", habitosConcluidosHoje) //debug
}

function abrirModalHabitos(){ //visualizar todos os hábitos do usuário
    const modal = document.getElementById("modalListaHabitos")
    modal.style.display = "block"

    document.body.style.overflow = "hidden" //trava scroll lateral

    listarTodosHabitos() //garante que os dados carregados estarão atualizados
}

function fecharModalHabitos(){
    const modal = document.getElementById("modalListaHabitos")
    modal.style.display = "none"
    document.body.style.overflow = "auto"
}

async function listarTodosHabitos(){

    const usuarioId = localStorage.getItem("usuarioId")
    const resposta = await fetch("http://localhost:8080/habito/usuario/" + usuarioId)
    const habitos = await resposta.json()
    const lista = document.getElementById("listaTodosHabitos")

    lista.innerHTML = "" //limpando para evitar duplicação

    habitos.forEach(h => { //percorrendo todos os hábitos do usuário

        const item = document.createElement("li") //cria cada item da lista

        //nome do hábito
        const texto = document.createElement("span")
        texto.innerText = h.nome + " (" + h.diasSemana + ")"

        //botão editar
        const btnEditar = document.createElement("button")
        btnEditar.innerHTML = "✏️"
        btnEditar.onclick = () => editarHabito(h)

        //botão deletar
        const btnDeletar = document.createElement("button")
        btnDeletar.innerHTML = "🗑️"
        btnDeletar.onclick = () => deletarHabito(h.id)

        item.appendChild(texto)
        item.appendChild(btnEditar)
        item.appendChild(btnDeletar)

        lista.appendChild(item)
    })
}

function deletarHabito(id){
    habitoParaDeletar = id
    document.getElementById("modalDelete").style.display = "block"
}

function editarHabito(habito){

    habitoEditando = habito

    document.getElementById("modalEditarHabito").style.display = "block"

    document.getElementById("editarNomeHabito").value = habito.nome

    const dias = habito.diasSemana.split(",")

    const checkboxes = document.querySelectorAll("#modalEditarHabito input[type=checkbox]")

    checkboxes.forEach(cb => {
        cb.checked = dias.includes(cb.value)
    })

}

function fecharModalEditar(){
    document.getElementById("modalEditarHabito").style.display = "none"
}

async function salvarEdicao(){

    const nome = document.getElementById("editarNomeHabito").value

    const checkboxes = document.querySelectorAll("#modalEditarHabito input:checked")

    let dias = []

    checkboxes.forEach(c => dias.push(c.value))

    const diasString = dias.join(",")

    await fetch("http://localhost:8080/habito/" + habitoEditando.id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: nome,
            diasSemana: diasString,
            usuario: {
                id: localStorage.getItem("usuarioId")
            }
        })
    })

    fecharModalEditar()

    listarTodosHabitos()
    listarHabitos()
    atualizarDashboard()

    mostrarToast("Hábito atualizado com sucesso!")
}

function fecharModalDelete(){
    document.getElementById("modalDelete").style.display = "none"
}

async function confirmarDelete(){

    const resposta = await fetch("http://localhost:8080/habito/" + habitoParaDeletar, {
        method: "DELETE"
    })

    if(!resposta.ok){
        mostrarToast("Erro ao deletar hábito!")
        return
    }

    fecharModalDelete()

    listarTodosHabitos()
    listarHabitos()
    atualizarDashboard()

    mostrarToast("Hábito deletado com sucesso!")
}

function mostrarToast(mensagem){

    const toast = document.getElementById("toast")

    if(!toast){
        console.error("Elemento #toast não encontrado")
        return
    }

    toast.innerText = mensagem
    toast.classList.add("show")

    setTimeout(() => {
        toast.classList.remove("show")
    }, 3000)
}

async function gerarSugestaoIA(){ //não é a implementação real, as sugestões estão fixas

    const input = document.getElementById("nomeHabitoModal")
    input.value = "Gerando sugestão..."
    const resposta = await fetch("http://localhost:8080/ia/sugestao")
    const texto = await resposta.text()
    input.value = texto
}