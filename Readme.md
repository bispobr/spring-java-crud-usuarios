# API REST cadastro de usuário

## Descrição

 API REST  desenvolvida para o cadastro de usuários por meio de dois campos: nome e e-mail. Ela oferece suporte às operações básicas de um CRUD, incluindo:
- **Listagem de usuários cadastrados, com possibilidade de busca por ID, e-mail ou listagem geral** 
- **Listagem** Dos usuarios cadastrados podendo ser traves do id, email ou uma simples busca por todos os usuarios cadastrados.
- **Atualização de um usuário existente** 
- **Exclusão de um usuário existente** 

## Tecnologias Utilizadas

- **Java + Spring Boot** – Framework principal para o desenvolvimento da aplicação
- **Lombok (@Slf4j)** – Facilita a geração e o gerenciamento de logs
- **Tratamento de Exceções** - @RestControllerAdvice Centraliza o tratamento de erros da aplicação
- **Swagger** – Documentação interativa da API
- **Spring Boot Actuator** – Monitoramento e verificação da saúde da aplicação
- **H2 database** – Banco de dados relacional em memória
- **Docker** – Criação, empacotamento e execução da aplicação em contêineres.


## Requisitos

- Java 25
- Maven


## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/spring-java-crud-usuarios.git
```

## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health

## Como Rodar em um Container (Opcional)

1. Construa o projeto

```bash
mvn clean package 
```

2. Gere a Imagem Docker, com o Docker  instalado execute:


```bash
docker build -t demo . 
```

3. Execute o Container

```bash
docker run -p 8080:8080 demo
```

## API Endpoints
API contem os seguintes endpoints:

```http request
POST /usuario/Cadastro - Cadastra um novo usuario
Content-Type: application/json

{
  "email": "xxxxxx",
  "nome": 00000
}
```
| Parâmetro | Tipo     | Descrição                           |
|:----------|:---------| :---------------------------------- |
| `email`   | `String` | **Obrigatório**. O email do usuário 
| `nome`    | `String` | **Obrigatório**. O nome do usuário 


```http request
GET /usuario/listar -  Lista todos os Usuários
```

```http request
GET /usuario/listar -  Lista Usuário por id
```


```http request
GET /usuario/ListarPorEmail -  Lista Usuário por id
```



```http request
PUT /usuario/atualizar/ - Atualizar um usuário existente
Content-Type: application/json

{
 "email": "xxxxxx",
  "nome": 00000
}
```
```http request
DELETE /usuario/RemoverPorid - Remover usuário de id especificado.
```

```http request
DELETE /usuario/RemoverPorEmail - Remover usuário do email  especificado.
```

