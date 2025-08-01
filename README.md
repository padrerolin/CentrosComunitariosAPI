# CentrosComunitariosAPI
# API RESTful para Centros Comunitários

## Visão Geral

Este projeto é uma API RESTful para gerenciamento de centros comunitários, seus recursos e seus intercambios

---

## Funcionalidades

- **Gerenciamento de Centros Comunitários:**
    - Cadastro, listagem, atualização e exclusão de centros.
    - Controle da ocupação com cálculo automático do percentual e notificação ao atingir capacidade máxima.

- **Recursos dos Centros:**
    - Armazenamento e gerenciamento de recursos pessoais e materiais.
    - Regras para intercâmbio de recursos entre centros, respeitando valores em pontos e exceções para alta ocupação.

- **Intercâmbio de Recursos:**
    - Troca de recursos entre centros garantindo equilíbrio de pontos, com exceções para centros com ocupação > 90%.
    - Registro histórico de negociações para rastreamento.

- **Relatórios:**
    - Centros com alta ocupação (> 90%).
    - Média de recursos por tipo nos centros cadastrados.
    - Histórico de negociações filtrado por centro e período.

---

## Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data MongoDB
- Lombok
- Maven
- Docker (para containerização)
- Postman (testes da API)

---

## Estrutura do Projeto

- **model** — Classes que representam os documentos MongoDB e entidades do domínio.
- **dto** — Objetos para transferência de dados entre camadas e para requests/responses.
- **repository** — Interfaces para acesso aos dados MongoDB.
- **service** — Regras de negócio, validações e manipulação de entidades.
- **controller** — Endpoints REST para interação com o sistema.

---

## Como Rodar

1. Configurar MongoDB local ou remoto.
2. Ajustar `application.properties` com dados de conexão.
3. Rodar via Maven:

   ```bash
   mvn spring-boot:run
   
## ou com Docker:

1. docker build -t centros-comunitarios .
2. docker run -p 8080:8080 centros-comunitarios
