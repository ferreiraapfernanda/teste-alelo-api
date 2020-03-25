# Teste API - Alelo

Fernanda Aparecida Ferreira

Teste desenvolvido para a vaga de Desenvolvedor Back-end na Alelo. 

Projeto de uma API de tarefas.

- [H2 Console](http://localhost:8080/h2-console/login.do?jsessionid=874a1dda362c8a336a03bb35a16ab438)
- [Swagger](http://localhost:8080/swagger-ui.html#/tarefa-controller/)

## Funcionalidades

- Listar tarefas
- Obter tarefa
- Criar tarefa
- Apagar tarefa
- Atualizar tarefa

## Tecnologias Utilizadas

- Java 8
- Spring Boot
- Banco de Dados H2
- Lombok
- JUnit
- Swagger
- Gson

## Rodando a aplicação

### 1. Dependências

É necessário possuir as seguintes dependências:

- Java 8
- Maven

### 2. Configuração base H2

É preciso alterar a URL de configuração do banco de dados H2.

No arquivo **application.properties**, altere a seguinte linha, para o caminho da pasta **dbh2** na máquina local:

```properties
spring.datasource.url = jdbc:h2:file:/home/fernanda/Desenvolvimento/teste-alelo-api/dbh2
```

### 3. Rodado a aplicação

Em um terminal, execute o comando

```console
mvn spring-boot:run
```

A aplicação estará disponível na porta 8080.

## Testes

Foram desenvolvidas 2 classes de testes:

- A classe TarefaTest possui testes sobre a camada service da aplicação.
- A classe APIExternaTest possui testes de consumo de uma API externa mockada;


-----

2020-03-25