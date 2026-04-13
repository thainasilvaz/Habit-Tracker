# 🧠 Habit Tracker

Sistema web para gerenciamento de hábitos diários, permitindo ao usuário criar, acompanhar e visualizar seu progresso ao longo do tempo.

---

## 📌 Funcionalidades

* ✅ Cadastro e login de usuários
* ✅ Criação de hábitos com dias da semana
* ✅ Marcar hábitos como concluídos
* ✅ Edição e exclusão de hábitos
* ✅ Cálculo de streak (sequência de dias)
* ✅ Barra de progresso diária
* ✅ Gráfico de hábitos concluídos ao longo do tempo
* ✅ Interface interativa com modais e animações

---

## 🛠️ Tecnologias utilizadas

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate

### Frontend

* HTML
* CSS
* JavaScript

### Banco de dados

* PostgreSQL

### Bibliotecas

* Chart.js

---

## 📂 Estrutura do projeto

```
habit-tracker/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── repository/
│   └── application.properties
│
├── frontend/
│   ├── login.html
│   ├── dashboard.html
│   ├── css/
│   └── js/
│
└── README.md
```

---

## ⚙️ Como rodar o projeto

### 🔹 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/habit-tracker.git
cd habit-tracker
```

---

### 🔹 2. Configurar o banco de dados (PostgreSQL)

Crie um banco no PostgreSQL:

```sql
CREATE DATABASE habit_tracker;
```

---

### 🔹 3. Configurar o backend

No arquivo:

```
src/main/resources/application.properties
```

Configure:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/habit_tracker
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

### 🔹 4. Rodar o backend

No terminal:

```bash
mvn spring-boot:run
```

Ou execute a aplicação pela sua IDE.

O backend ficará disponível em:

```
http://localhost:8080
```

---

### 🔹 5. Rodar o frontend

Abra o arquivo:

```
frontend/login.html
```

Ou utilize uma extensão como **Live Server** no VS Code.

---

## 🔗 Integração Frontend ↔ Backend

O frontend consome a API através de requisições `fetch`, por exemplo:

```javascript
fetch("http://localhost:8080/habito")
```

---

## 📊 Funcionalidades em destaque

### 📈 Gráfico de hábitos

Mostra a quantidade de hábitos concluídos por dia.

### 🔥 Streak

Calcula quantos dias consecutivos o usuário completou hábitos.

### 📅 Filtro por dia da semana

Cada hábito só aparece nos dias selecionados.

---

## 💡 Melhorias futuras

* Login com redes sociais
* Deploy em nuvem
* IA para sugestão de hábitos

---

## 👩‍💻 Autora

Projeto desenvolvido por **Thainá Ferreira da Silva** como prática de desenvolvimento full stack.

---

## 📄 Licença

Este projeto é apenas para fins educacionais.
