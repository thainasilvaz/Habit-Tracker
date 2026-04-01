//impedindo usuário acessar o dashboard sem login

const usuarioId = localStorage.getItem("usuarioId")

if(!usuarioId){
    window.location.href = "login.html"
}

//destruindo o grafico anterior -> variavel dentro da funcao carregarGrafico()
let grafico = null


window.onload = async function(){ //carregando funções automaticamente ao abrir a página -> sem precisar clicar em um botão, por exemplo
    await carregarUsuario() //exibindo o nome do usuário conectado
    await atualizarDashboard()
    await listarHabitos() //listando hábitos automaticamente
    await carregarStreak()
}

async function carregarUsuario(){ //identificar qual usuário está logado -> nome no canto superior do dashboard

    const usuarioId = localStorage.getItem("usuarioId")
    const resposta = await fetch("http://localhost:8080/usuario/" + usuarioId)
    const usuario = await resposta.json()
    document.getElementById("nomeUsuario").innerText = usuario.nome

}

function logout(){

    localStorage.removeItem("usuarioId")
    window.location.href = "login.html"

}

function abrirModal(){

    const modal = document.getElementById("modalHabito").style.display = "block"

}

function fecharModal(){

    const modal = document.getElementById("modalHabito")

    modal.style.display = "none"

    //limpando campos ao fechar o modal
    document.getElementById("nomeHabitoModal").value = ""

    const checkboxes = document.querySelectorAll(".diasSemana input") //pegando todos os checkboxes
    checkboxes.forEach(cb => cb.checked = false) //desmarcando os checkboxes

}

async function carregarConcluidosHoje(){ //card de concluídos hoje

    const usuarioId = localStorage.getItem("usuarioId")
    const resposta = await fetch("http://localhost:8080/registro/hoje/"+usuarioId) //chama o endpoint onde todos os registros do dia sao buscados
    const registros = await resposta.json()
    document.getElementById("concluidosHoje").innerText = registros.length //atualiza o card com a quantidade de registros do dia

}

async function carregarTotalHabitos(){ //card total hábitos

    const usuarioId = localStorage.getItem("usuarioId")
    const resposta = await fetch("http://localhost:8080/habito/usuario/"+usuarioId)
    const habitos = await resposta.json()
    document.getElementById("totalHabitos").innerText = habitos.length

}

async function atualizarProgresso(){ //barra de progresso

    const concluidos = parseInt(document.getElementById("concluidosHoje").innerText) //pega o conteúdo do card concluidosHoje

    const totalHoje = await carregarTotalHabitosHoje()

    let porcentagem = 0

    if(totalHoje > 0){ //evita divisão por 0 
        porcentagem = (concluidos / totalHoje) * 100 //quantos % do total foram concluídos?
    }

    document.getElementById("barraProgresso").style.width = porcentagem+"%" //se porcentagem = 40 -> width: 40% -> a barra de progresso aumenta
    document.getElementById("textoProgresso").innerText = porcentagem.toFixed(0)+"% concluído"

}

async function carregarStreak(){

    const usuarioId = localStorage.getItem("usuarioId")

    const resposta = await fetch("http://localhost:8080/registro/streak/" + usuarioId)

    const streak = await resposta.json()

    document.getElementById("streak").innerText = streak + " dias"
}

async function carregarGrafico(){ //grafico de estatísticas do usuário

    const usuarioId = localStorage.getItem("usuarioId")

    const resposta = await fetch("http://localhost:8080/registro/estatisticas/" + usuarioId)
    const dados = await resposta.json()


    // pegando a data no fuso local (Brasil)
    const hojeObj = new Date()
    const hoje = hojeObj.getFullYear() + "-" +
        String(hojeObj.getMonth() + 1).padStart(2, "0") + "-" +
        String(hojeObj.getDate()).padStart(2, "0")

    const dadosNormalizados = dados.map(d => [d[0].split("T")[0], d[1]])

    const existeHoje = dadosNormalizados.some(d => d[0] === hoje) //a função some verifica se pelo menos um elemento de um array satisfaz uma condição

    if(!existeHoje){
        dadosNormalizados.push([hoje, 0])
    }

    //ordenando datas
    dadosNormalizados.sort((a,b) => new Date(a[0]) - new Date(b[0]))

    //separando os dados em eixo x e y -> cada d é ["2026-03-20", 5]
    const labels = dadosNormalizados.map(d => { //data
    const data = new Date(d[0] + "T00:00:00")
    return data.toLocaleDateString("pt-BR") //colocando no formato br
    })

    const valores = dadosNormalizados.map(d => d[1]) //quantidade de habitos

    const ctx = document.getElementById("graficoHabitos").getContext("2d")

    if(grafico){
        grafico.destroy()
    }

    grafico = new Chart(ctx, {
        type: "line", //cria um grafico de linha
        data: {
            labels: labels, //eixo horizontal
            datasets: [{
                label: "Hábitos concluídos",
                data: valores, //eixo vertical
                borderWidth: 3,
                tension: 0.4,
                fill: true
            }]
        },
        options: { //tirando os números quebrados 
            scales: {
                y: {
                    beginAtZero:true,
                    ticks: {
                        stepSize: 1, //pula de 1 em 1
                        callback: function(value) {
                            if (Number.isInteger(value)) {
                                return value;
                            }
                        }
                    }       
                }
            }
        }
    })
}

async function carregarTotalHabitosHoje(){

    const usuarioId = localStorage.getItem("usuarioId")

    const resposta = await fetch("http://localhost:8080/habito/usuario/" + usuarioId)
    const habitos = await resposta.json()

    const hoje = diaAtual()

    const habitosHoje = habitos.filter(h => {
        if(!h.diasSemana) return false

        return h.diasSemana
            .split(",")
            .map(d => d.trim())
            .includes(hoje)
    })

    return habitosHoje.length
}


async function atualizarDashboard(){

    await carregarTotalHabitos()
    await carregarConcluidosHoje()
    await atualizarProgresso()
    await carregarGrafico()
    await listarHabitos()
    await carregarStreak()

}
