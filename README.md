# Wishlist App — Backend API (Terminal Edition)

API RESTful robusta desenvolvida em **Java 17** e **Spring Boot 3**, seguindo rigorosamente a arquitetura em camadas e o documento de especificação técnica.

## 🚀 Tecnologias Utilizadas

- **Java 17** (Microsoft OpenJDK 17)
- **Spring Boot 3.3.5**
  - **Spring Web**: Controladores RESTful com mapeamento de endpoints e DTOs.
  - **Spring Security 6**: Proteção de rotas com autenticação stateless.
  - **Spring Data JPA**: Repositórios com suporte a `JpaSpecificationExecutor` para filtros dinâmicos e paginação.
  - **Bean Validation**: Validação rigorosa de campos com mensagens descritivas.
- **Autenticação JWT**: `io.jsonwebtoken (jjwt 0.12.6)` com encriptação de senhas via `BCryptPasswordEncoder`.
- **Base de Dados**:
  - **PostgreSQL**: Base de dados relacional primária.
  - **H2 Database**: Perfil de desenvolvimento (`--spring.profiles.active=dev`) em memória para testes imediatos.
- **Documentação de API**: SpringDoc OpenAPI / Swagger UI 3.

---

## 🗄️ Modelação da Base de Dados

### Tabela `users`
- `id` (UUID, Primary Key)
- `name` (VARCHAR)
- `email` (VARCHAR, Unique, Not Null)
- `password_hash` (VARCHAR, Not Null)
- `created_at` (TIMESTAMP)

### Tabela `wish_items`
- `id` (UUID, Primary Key)
- `user_id` (UUID, Foreign Key -> `users.id`)
- `title` (VARCHAR, Not Null)
- `description` (TEXT)
- `url` (VARCHAR 2048)
- `image_url` (VARCHAR 2048)
- `estimated_price` (NUMERIC 10,2)
- `priority` (ENUM: `LOW`, `MEDIUM`, `HIGH`)
- `status` (ENUM: `PENDING`, `PURCHASED`, `RESERVED`)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

---

## 📡 Endpoints da API REST

| Método | Endpoint | Protegido? | Descrição |
|---|---|---|---|
| `POST` | `/api/v1/auth/register` | Não | Registo de novos utilizadores com devolução de token JWT |
| `POST` | `/api/v1/auth/login` | Não | Autenticação com credenciais e emissão do token JWT |
| `GET` | `/api/v1/items` | **Sim (Bearer JWT)** | Listagem paginada e com filtros combinados (`search`, `priority`, `status`) |
| `POST` | `/api/v1/items` | **Sim (Bearer JWT)** | Criação de um novo artigo na lista do utilizador autenticado |
| `GET` | `/api/v1/items/{id}` | **Sim (Bearer JWT)** | Detalhes de um artigo específico |
| `PUT` | `/api/v1/items/{id}` | **Sim (Bearer JWT)** | Atualização de parâmetros ou status do artigo |
| `DELETE`| `/api/v1/items/{id}` | **Sim (Bearer JWT)** | Remoção de um artigo da base de dados |

---

## 📑 Documentação Interativa (Swagger / OpenAPI)

Com a aplicação em execução, aceda a:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## ⚙️ Como Executar

### 1. Perfil de Desenvolvimento Rápido (H2 em memória)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
ou executando o JAR:
```bash
java -jar target/lista-desejos-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### 2. Produção / Conexão Nativa ao PostgreSQL
Por padrão, o Spring Boot conecta-se ao PostgreSQL na porta `5432` da base de dados `wishlist_db`.
Pode configurar via variáveis de ambiente:
```bash
export DB_URL="jdbc:postgresql://localhost:5432/wishlist_db"
export DB_USERNAME="postgres"
export DB_PASSWORD="sua_senha_aqui"

./mvnw spring-boot:run
```
Na primeira execução com PostgreSQL, crie a base de dados:
```sql
CREATE DATABASE wishlist_db;
```
O Hibernate executará o `ddl-auto: update` criando automaticamente as tabelas `users` e `wish_items` com todas as constraints e relacionamentos.
