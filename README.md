# 🚀 Gestão Financeira - Desafio NTT Data

![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-brightgreen?logo=spring&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-4.0.0-critical?logo=apache-maven&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?logo=apachekafka&logoColor=white) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white) ![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-orange?logo=swagger&logoColor=white)

Uma plataforma de microserviços robusta para gestão financeira, desenvolvida com **Clean Architecture** e **Domain-Driven Design (DDD)**. O projeto permite o gerenciamento completo de clientes e o processamento assíncrono de transações financeiras, garantindo segurança, escalabilidade e manutenibilidade.

---

## 📚 Índice

- [✨ Funcionalidades Principais](#-funcionalidades-principais)
- [��️ Arquitetura e Tecnologias](#️-arquitetura-e-tecnologias)
- [🗂️ Visão Geral dos Microserviços](#️-visão-geral-dos-microserviços)
- [⚙️ Começando](#️-começando)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação e Execução](#instalação-e-execução)
- [📡 Uso da API e Endpoints](#-uso-da-api-e-endpoints)
- [🧪 Testes](#-testes)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🙏 Agradecimentos](#-agradecimentos)

---

## ✨ Funcionalidades Principais

- 🔐 **Autenticação Segura:** Sistema de login com **Spring Security** e tokens **JWT**.
- 🤖 **Gestão de Clientes:** CRUD completo para clientes, com controle de status (ativo/inativo) e atualização de permissões (`Role`).
- ✉️ **Importação em Lote:** Cadastro massivo de clientes a partir de planilhas Excel (`.xlsx`) utilizando **Apache POI**.
- 💸 **Processamento de Transações:** Criação e cancelamento de transações financeiras.
- 📄 **Geração de Extratos:** Emissão de extratos de transações em formato **PDF** com a biblioteca **OpenPDF**.
- 🚀 **Processamento Assíncrono:** As transações são publicadas em um tópico **Apache Kafka** e processadas de forma assíncrona por um microserviço consumidor, simulando integrações com sistemas externos (via MockAPI).
- 🌐 **Integração Externa:** Consulta de taxas de câmbio em tempo real através da [BrasilAPI](https://brasilapi.com.br/).
- 📖 **Documentação de API:** Geração automática de documentação interativa com **OpenAPI (Swagger UI)**.

---

## 🏛️ Arquitetura e Tecnologias

O projeto segue os princípios da **Clean Architecture** para separar as responsabilidades em camadas bem definidas (`domain`, `application`, `infra`, `presentation`). A comunicação entre os serviços é feita via requisições REST e mensageria.

**Fluxo de Transação:**
`Cliente (APP)` ➔ `service-transaction (API REST)` ➔ `Apache Kafka (Tópico)` ➔ `service-transaction-processor (Consumidor)` ➔ `MockAPI (Simulação)`

### Principais Tecnologias:
*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3
*   **Segurança:** Spring Security, JWT (Auth0 Java JWT)
*   **Acesso a Dados:** Spring Data JPA, Hibernate
*   **Banco de Dados:** PostgreSQL
*   **Migrações:** Flyway
*   **Mensageria:** Apache Kafka
*   **Containerização:** Docker e Docker Compose
*   **Manipulação de Arquivos:** Apache POI (Excel), OpenPDF (PDF)
*   **Documentação:** Springdoc OpenAPI
*   **Build:** Maven

---

## 🗂️ Visão Geral dos Microserviços

<table class="data-table">
  <thead>
    <tr>
      <th scope="col">Microserviço</th>
      <th scope="col">Porta Padrão</th>
      <th scope="col">Responsabilidade Principal</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>service-client</code></td>
      <td><code>8081</code></td>
      <td>Gerencia a autenticação, o cadastro e a consulta de clientes. Responsável pela importação de dados via Excel.</td>
    </tr>
    <tr>
      <td><code>service-transaction</code></td>
      <td><code>8082</code></td>
      <td>Orquestra a criação e o cancelamento de transações, publicando os eventos no Kafka. Gera os extratos em PDF.</td>
    </tr>
    <tr>
      <td><code>service-transaction-processor</code></td>
      <td><code>8083</code></td>
      <td>Serviço de background que consome as mensagens do Kafka, simula o processamento real da transação e atualiza seu status.</td>
    </tr>
  </tbody>
</table>

---

## ⚙️ Começando

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

### Pré-requisitos
- [Java 17+](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started/) e [Docker Compose](https://docs.docker.com/compose/install/)

### Instalação e Execução

**1. Clone o Repositório**
```bash
git clone https://github.com/seu-usuario/desafio-gestao-financeira.git
cd desafio-gestao-financeira
```

**2. Configure as Variáveis de Ambiente**
Para cada microserviço (`service-client`, `service-transaction`), renomeie o arquivo `.env.example` para `.env` e preencha as variáveis necessárias.

Exemplo de `.env` para `service-client`:
```properties
# BANCO DE DADOS
DB_URL=jdbc:postgresql://localhost:5432/client_db
DB_USER=seu_usuario
DB_PASS=sua_senha

# JWT
JWT_SECRET=seu_segredo_super_secreto

# KAFKA
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

**3. Execute com Docker Compose (Recomendado)**
Este comando irá construir as imagens dos microserviços e iniciar todos os contêineres necessários (aplicações, PostgreSQL, Kafka e Zookeeper) de forma automática.

```bash
docker-compose up --build -d
```
> O `-d` (detached mode) executa os contêineres em segundo plano. Para visualizar os logs, use `docker-compose logs -f`.

**4. Verifique se os serviços estão no ar:**
- **service-client:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html){target="_blank"}
- **service-transaction:** [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html){target="_blank"}

---

## 📡 Uso da API e Endpoints

A forma mais fácil de explorar e interagir com a API é através da interface do **Swagger UI**, disponível nos links acima.

Abaixo, alguns exemplos de requisições `cURL` para funcionalidades chave:

**Autenticação (obter token JWT):**
```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{
        "username": "usuario",
        "password": "senha"
      }'
```

**Criar uma nova transação (usando o token obtido):**
```bash
TOKEN="seu_token_jwt_aqui"

curl -X POST http://localhost:8082/transaction \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
        "sourceAccountId": 1,
        "destinationAccountId": 2,
        "value": 150.75,
        "currency": "BRL",
        "description": "Pagamento de serviço",
        "type": "TRANSFER",
        "method": "PIX"
      }'
```

**Importar clientes via Excel:**
```bash
TOKEN="seu_token_jwt_aqui"

curl -X POST http://localhost:8081/clients/import \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@caminho/para/seu/arquivo/clientes.xlsx"
```

---
# 🦇Rodar arquivos .jar local
  Utilize o comando no terminal para executar o build com o Maven, incluindo a execução dos testes, e abrir os arquivos .jar gerados.
```
.\build-run.bat

```
---
## 🧪 Testes

Para garantir a qualidade e a integridade do código, execute os testes unitários de cada módulo.

**Executar todos os testes do projeto:**
```bash
mvn clean test
```

**Executar testes de um módulo específico:**
```bash
# Exemplo para o service-client
mvn -f service-client/pom.xml test
```

---

## 🗂️ Estrutura do Projeto

O projeto é organizado em um modelo multi-módulo Maven, onde cada microserviço é um módulo independente. Internamente, cada serviço segue a estrutura da Clean Architecture.

```
desafiobeca/
├── docker-compose.yml          # Orquestração dos contêineres
├── pom.xml                     # POM pai do Maven
|
├── service-client/             # Módulo do microserviço de clientes
│   ├── src/main/java
│   │   ├── com/nttdata/
│   │   │   ├── application/      # Casos de uso e serviços da aplicação
│   │   │   ├── domain/           # Entidades e lógica de negócio
│   │   │   ├── infra/            # Implementações (JPA, Kafka producers) 
│   │   │   │   └─ presentation/  # Controllers (API REST) e DTO
│   │   │   └── configs/          # Configurações para o spring "@Bean"
│   │   └── resources/            # Arquivos para o FlyWay e .properties
│   ├── pom.xml
|
├── service-transaction/        # Módulo do microserviço de transações
│   └── ... (estrutura similar ao service-client)
|
└── service-transaction-processor/ # Módulo consumidor do Kafka
    └── ...
```

---

## 🙏 Agradecimentos

- Agradeço a todas as bibliotecas e projetos de código aberto que tornaram este desenvolvimento possível, como **Spring**, **Apache Kafka** e **Docker**.
- E obrigado **NTT Data** pela proposição do desafio e pela inspiração que tornou possivel sua elaboração.
