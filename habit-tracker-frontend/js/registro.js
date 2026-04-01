async function concluirHabito(habitoId){

    const usuarioId = localStorage.getItem("usuarioId") //pega o usuário logado

    const resposta = await fetch("http://localhost:8080/registro/"+habitoId+"/concluir/"+usuarioId,{

        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },

        body: JSON.stringify({
            habito:{ //diz qual hábito foi concluído
                id:habitoId
            },

            usuario:{ //diz qual usuário concluiu o hábito
                id:usuarioId
            },

            data: new Date().toISOString().split("T")[0], //gera a data atual no formato para o backend (objeto de data com o momento atual no padrão internacional ISO e dividido no caractere T) -> ex: 2026-03-20T18:40:21.345Z vira ["2026-03-20","18:40:21.345Z"] e o [0] pega só o primeiro elemento da lista
            concluido:true
        })
    })

    const dados = await resposta.json()
    console.log(dados)

    await carregarConcluidosHoje()
    await listarHabitos()
    await atualizarDashboard()

}

