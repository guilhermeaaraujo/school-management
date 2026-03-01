# 📚 School Management API

API REST para gerenciamento escolar desenvolvida em Java com Spring Boot.
Projeto criado para estudo e prática de padrões de arquitetura backend e boas práticas em APIs REST.

## 🧩 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgresSQL
- Flyway Migrations
- H2 Database (para testes)
- Swagger (OpenAPI)
- Maven
- Spring Security + JWT
- JUnit + Mockito para testes unitários.

## 🏛 Arquitetura do Projeto

Organização em camadas com responsabilidades definidas:

- **Controller** → Endpoints da API
- **Service** → Regras de negócio
- **Repository** → Acesso ao banco de dados
- **Entities** → Modelos do domínio
- **DTOs** → Objetos para transferência de dados

## 📊 Database Migrations

Esse projeto utiliza Flyway para versionamento e controle do banco de dados.

## 🔒 Autenticação e Autorização

- Implementada utilizando Spring Security + JWT com controle baseado em roles.
- ROLE_ADMIN: Representa os administradores com todas as permissões de CRUD ao banco de dados.
- ROLE_TEACHER: Representa os professores, tendo certas permissões sobre seus próprios dados e dados de classes.
- ROLE_STUDENT: Representa os professores, tendo certas permissões sobre seus próprios dados.

## 📌 Funcionalidades Principais

Endpoints que permitem:

- CRUD de Alunos
- CRUD de Professores
- CRUD de Turmas
- Associação de alunos a turmas

## 🚀 Executando o Projeto

**Requisitos**:

- Java 17+
- Maven
- Banco PostgreSQL

## 📘 Documentação da API (Swagger)

Documentação interativa disponível em:

http://localhost:8080/swagger-ui/index.html

Através dela você pode testar os endpoints diretamente.

## 📈 Possíveis Evoluções

- Adicionar paginação e filtros
- Criar front-end web ou mobile
- Relatórios de desempenho/resumo escolar

## 👨‍💻 Autor

**Guilherme Araújo**

Projeto desenvolvido com foco em aprendizado e arquitetura backend.

🔗 GitHub: https://github.com/guilhermeaaraujo
