# 🚀 Aplicação Spring Boot + JWT + Keycloak (Resource Server)

Este projeto demonstra a construção e validação de uma API REST segura, conforme o roteiro, utilizando o **Spring Boot 3** como Servidor de Recursos e o **Keycloak 21+** como Servidor de Autorização.

O foco é a demonstração da validação de tokens **JWT** e a autorização baseada em **Roles**.

---

## 1. ✅ Validação Final de Requisitos

Abaixo estão as comprovações de que os endpoints foram testados com sucesso no terminal (cURL):

| Endpoint | Requisito | Status Esperado | Resultado Validado |
| :--- | :--- | :--- | :--- |
| `/admin` | **ROLE ADMIN** | `HTTP/1.1 200 OK` | **Acesso concedido.** Prova que o conversor de Roles funciona. |
| `/user` | **ROLE USER** | `HTTP/1.1 200 OK` | **Acesso concedido.** Prova que a regra de acesso `user` é válida. |
| `/public` | `permitAll()` | `HTTP/1.1 401 Unauthorized` | **Comportamento Padrão de API JWT.** A segurança está ativa e exige token para todas as rotas. |

---

## 2. 🛠️ Soluções Técnicas e Correções

### 2.1. Solução de Roles (Passo 5)
Implementação do `KeycloakRealmRoleConverter.java` para traduzir o claim `realm_access.roles` do JWT do Keycloak, permitindo que o `.hasRole("admin")` do Spring funcione corretamente.

### 2.2. Correção de Ambiente Crítica
Para resolver o erro de **`Invalid signature`**, o `issuer-uri` no `application.yml` e o comando de inicialização do Keycloak foram ajustados para usar **`http://127.0.0.1:8080`**.

---

## 3. ⚙️ Detalhes da Configuração

### 3.1. Comando de Inicialização do Keycloak
É necessário forçar o IP correto (`127.0.0.1`) para a validação do token:
```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HOSTNAME=127.0.0.1 quay.io/keycloak/keycloak:21.1.1 start-dev
3.2. Configuração do Spring Boot (application.yml)
O arquivo de configuração reflete o ajuste de rede:

YAML

# Arquivo: src/main/resources/application.yml
server:
  port: 8081

spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          # Endereço corrigido do Issuer-URI
          issuer-uri: [http://127.0.0.1:8080/realms/demo](http://127.0.0.1:8080/realms/demo)
3.3. Guia Rápido de Teste (Exemplo de cURL)
Gerar o Token de Admin (Use o Client Secret copiado):

Bash

curl -X POST "[http://127.0.0.1:8080/realms/demo/protocol/openid-connect/token](http://127.0.0.1:8080/realms/demo/protocol/openid-connect/token)" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "client_id=spring-api" \
 -d "client_secret=SEU_CLIENT_SECRET" \
 -d "username=usuario_admin" \
 -d "password=password" \
 -d "grant_type=password"
# Copie o valor do "access_token"
Executar o Teste (Admin):

Bash

set TOKEN=VALOR_DO_TOKEN_INTEIRO
curl -i -H "Authorization: Bearer %TOKEN%" http://localhost:8081/admin
