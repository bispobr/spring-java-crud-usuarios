# API REST de Cadastro de Usuários

API REST desenvolvida com Java e Spring Boot para gerenciamento de usuários, disponibilizando operações de CRUD e consultas por diferentes critérios.

O projeto também demonstra práticas comuns no desenvolvimento de APIs REST, como tratamento global de exceções, documentação com Swagger/OpenAPI, monitoramento com Actuator, logging e execução em Docker.

## Funcionalidades

- Cadastro de usuários
- Listagem de todos os usuários
- Consulta de usuário por ID
- Consulta de usuário por e-mail
- Atualização de usuário
- Exclusão de usuário por ID
- Exclusão de usuário por e-mail
- Validação dos dados de entrada
- Tratamento global de exceções
- Logging
- Documentação da API com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Persistência com H2 Database
- Execução em container Docker

## Tecnologias

- Java 25
- Spring Boot
- Spring Web
- Spring Validation
- Spring Boot Actuator
- Swagger/OpenAPI
- H2 Database
- Lombok
- Maven
- Docker

## Requisitos

- Java 25+
- Maven
- Docker (opcional)

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/spring-java-crud-usuarios.git
cd spring-java-crud-usuarios
```

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, acesse a documentação interativa:

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

Endpoint de saúde da aplicação:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Cadastrar usuário

```http
POST /usuario/Cadastro
Content-Type: application/json
```

Exemplo:

```json
{
  "email": "usuario@example.com",
  "nome": "João da Silva"
}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `email` | `String` | E-mail do usuário. |
| `nome` | `String` | Nome do usuário. |

### Listar usuários

```http
GET /usuario/listar
```

Retorna os usuários cadastrados.

### Buscar usuário por ID

```http
GET /usuario/listar/{id}
```

Retorna o usuário correspondente ao identificador informado.

### Buscar usuário por e-mail

```http
GET /usuario/ListarPorEmail?email={email}
```

Retorna o usuário correspondente ao e-mail informado.

### Atualizar usuário

```http
PUT /usuario/atualizar/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "email": "usuario@example.com",
  "nome": "João da Silva"
}
```

### Remover usuário por ID

```http
DELETE /usuario/RemoverPorid/{id}
```

### Remover usuário por e-mail

```http
DELETE /usuario/RemoverPorEmail?email={email}
```

## Fluxo simplificado

```text
Cliente
   │
   ▼
API REST
   │
   ├── Cadastro
   ├── Consulta
   ├── Atualização
   └── Exclusão
          │
          ▼
     Persistência
          │
          ▼
     H2 Database
```

## Docker

Gere o pacote da aplicação:

```bash
mvn clean package
```

Gere a imagem Docker:

```bash
docker build -t demo .
```

Execute o container:

```bash
docker run -p 8080:8080 demo
```

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Spring Boot, operações CRUD, validação, tratamento de exceções, logging, documentação OpenAPI, monitoramento e execução em containers.
