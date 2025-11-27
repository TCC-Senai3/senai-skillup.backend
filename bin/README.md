# BACKEND Senai Skill UP

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-blue.svg)

API RESTful para a plataforma de gamificação de aprendizado Senai Skill UP, permitindo o gerenciamento de usuários, salas, quizes e respostas.

---

## 📋 Índice

* [Sobre](#-sobre)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Pré-requisitos](#-pré-requisitos)
* [Como Instalar e Rodar o Projeto](#-como-instalar-e-rodar-o-projeto)
* [Endpoints da API](#-endpoints-da-api)
* [Como Contribuir](#-como-contribuir)
* [Licença](#-licença)

---

## 📖 Sobre

Backend criado para conclusao do curso de Desenvolvimento de sistemas (senai)

---

## 🚀 Tecnologias Utilizadas

* **Back-end:** [Java](https://www.java.com/) com [Spring Framework](https://spring.io/)
* **Banco de Dados:** [MySQL](https://www.mysql.com/)
* **Autenticação e Segurança:** [JWT (JSON Web Token)](https://jwt.io/)

---

## ✔️ Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
* [Git](https://git-scm.com)
* [Java JDK](https://www.oracle.com/java/technologies/downloads/) (versão 17 ou superior)
* [Maven](https://maven.apache.org/)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)

---

## ⚙️ Como Instalar e Rodar o Projeto


```bash
# 1. Clone este repositório
$ git clone [https://github.com/](https://github.com/)[seu-usuario]/[seu-repo].git

# 2. Acesse a pasta do projeto no seu terminal
$ cd [seu-repo]

# 3. Configure o banco de dados
#    - Crie um banco de dados no seu MySQL.
#    - Configure o arquivo 'application.properties' (em src/main/resources) com a URL do banco, usuário e senha.

# 4. Execute a aplicação
$ ./mvnw spring-boot:run
# ou, se tiver o Maven instalado globalmente:
$ mvn spring-boot:run

# O servidor será iniciado na porta: 8080
# A URL base da API é http://localhost:8080
```

---

## 📡 Endpoints da API

A URL base para todos os endpoints é `http://localhost:8080`.

**Observação:** Rotas marcadas com "Sim" na coluna "Requer Autenticação?" precisam do Token JWT no cabeçalho da requisição (`Authorization: Bearer <seu_token>`).

### Autenticação e Usuários

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Cadastro** | `POST` | `/usuarios/cadastro` | Não | `{"nome": "Nome", "email": "email@gmail.com", "senha": "senha"}` |
| **Login** | `POST` | `/usuarios/login` | Não | `{"email": "email@gmail.com", "senha": "senha"}` |
| **Adicionar Biografia** | `POST` | `/usuarios/{id}/biografia` | Sim | `{"biografia": "Biografia de teste"}` |

### Temas

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Listar Temas** | `GET` | `/temas` | Não | *(vazio)* |
| **Criar Tema** | `POST` | `/temas` | Sim | `{"nomeTema": "Geral"}` |

### Formulários

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Listar Formulários** | `GET` | `/formularios` | Não | *(vazio)* |
| **Criar Formulário** | `POST` | `/formularios` | Sim | `{"titulo": "Formulário de teste"}` |

### Perguntas

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Listar Perguntas** | `GET` | `/perguntas` | Não | *(vazio)* |
| **Buscar Pergunta por ID** | `GET` | `/perguntas/{id}` | Não | *(vazio)* |
| **Criar Pergunta** | `POST` | `/perguntas` | Sim | `{"textoPergunta": "O que é Java?", "tema": {"idTema": 1}, "idFormulario": 1}` |

### Alternativas

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Listar Alternativas** | `GET` | `/alternativas` | Não | *(vazio)* |
| **Buscar Alternativa por ID** | `GET` | `/alternativas/{id}` | Não | *(vazio)* |
| **Criar Alternativa** | `POST` | `/alternativas` | Sim | `{"idPergunta": 1, "textoAlternativa": "É uma linguagem de programação", "correta": true}` |

### Salas e Jogo

| Funcionalidade | Método | Rota | Requer Autenticação? | Corpo da Requisição (Body) |
| :--- | :--- | :--- | :--- | :--- |
| **Listar Salas** | `GET` | `/salas` | Não | *(vazio)* |
| **Buscar Sala por ID** | `GET` | `/salas/{id}` | Não | *(vazio)* |
| **Criar Sala** | `POST` | `/salas` | Sim | `{"nomeSala": "Sala teste", "idUsuario": 1, "idTema": 1, "statusSala": "DISPONIVEL", "idFormulario": 1}` |
| **Entrar em uma Sala** | `POST` | `/salas/{idSala}/entrar/{idUsuario}` | Sim | *(vazio)* |
| **Enviar Resposta** | `POST` | `/respostas` | Sim | `{"idUsuario": 1, "idPergunta": 1, "idAlternativaSelecionada": 1, "tempoGasto": 15, "idSala": 1}` |

---

## 🤔 Como Contribuir

Se quiser contribuir para o projeto, siga estes passos:

1.  Faça um **Fork** do projeto.
2.  Crie uma nova branch com as suas alterações: `git checkout -b my-feature`
3.  Salve as alterações e crie uma mensagem de commit: `git commit -m "feat: My new feature"`
4.  Envie para a sua branch: `git push origin my-feature`
5.  Abra um **Pull Request**.

---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.

---

Feito por isaacdev07 Entre em contato!

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/[seu-linkedin]/)
[![github](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/isaacdev07)
