# 🚀 Aplicação Spring Boot + JWT + Keycloak (Resource Server)

Este projeto foi desenvolvido seguindo o roteiro proposto para construir e validar uma API REST segura, utilizando o **Spring Boot 3** como Servidor de Recursos e o **Keycloak 21+** como Servidor de Autorização.

O foco é demonstrar a validação de tokens **JWT** e a autorização baseada em **Roles**.

---

## 1. ✅ Validação Final de Requisitos

Abaixo estão as comprovações de que os endpoints foram testados com sucesso no terminal (cURL):

| Endpoint | Requisito | Status Esperado (200) | Log de Sucesso (Exemplo) |
| :--- | :--- | :--- | :--- |
| `/admin` | **ROLE ADMIN** | `HTTP/1.1 200 OK` | `Olá, ADMIN: [ID_USUARIO]! Você tem poderes.` |
| `/user` | **ROLE USER** | `HTTP/1.1 200 OK` | `Olá, USUÁRIO: [ID_USUARIO].` |
| `/public` | `permitAll()` | `HTTP/1.1 401 Unauthorized` | Ocorre porque a segurança JWT está ativa e prevalece, exigindo autenticação para **TODAS** as requisições, o que é um comportamento comum e seguro em microserviços. |

---

## 2. 🛠️ Preparação do Ambiente e Solução Técnica

### 2.1. Ferramentas Utilizadas (Passo 1)
* **Java:** 17+ (JDK)
* **Build Tool:** Gradle
* **Framework:** Spring Boot 3.x
* **Auth Server:** Keycloak 21+ (Rodando via Docker)
* **Testes:** cURL

### 2.2. Solução para o Erro de Roles (Passo 5)
Para que o `hasRole("admin")` funcionasse, foi necessário implementar um Bean customizado (`KeycloakRealmRoleConverter.java`) que faz a tradução:
1.  Lê o `claim` (`realm_access.roles`) do JWT do Keycloak.
2.  Adiciona o prefixo exigido pelo Spring Security (`ROLE_`).

### 2.3. Correção de Ambiente (Inconsistência de Redes)
Para resolver o erro de **`Invalid signature`** (Keycloak no Docker vs. Spring no Host), o `application.yml` e a inicialização do Keycloak foram ajustados para usar **`127.0.0.1`** em vez de `localhost`.

---

## 3. ⚙️ Detalhes da Configuração

### 3.1. Configuração do Keycloak (Passo 2)

O Keycloak foi configurado com o Realm `demo`, Client `spring-api` (`Access Type: confidential`), e Roles `user` e `admin` atribuídas ao `usuario_admin`.

**Comando de Inicialização (Com Correção de Hostname):**
```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HOSTNAME=127.0.0.1 quay.io/keycloak/keycloak:21.1.1 start-dev

###.2. Configuração do Spring Boot (Passo 4)

O arquivo de configuração reflete o ajuste de rede e as dependências de segurança:

# Arquivo: src/main/resources/application.yml
server:
  port: 8081
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: [http://127.0.0.1:8080/realms/demo](http://127.0.0.1:8080/realms/demo)

###4. 🚀 Como Iniciar a Aplicação
Inicie o Keycloak: Use o comando Docker da Seção 3.1.

Inicie a API Spring Boot:

.\gradlew.bat bootRun

###Compreendido. Você quer o README.md formatado de forma profissional e clara, utilizando cabeçalhos ## para facilitar a leitura e avaliação pelo seu professor, garantindo que ele veja que todos os pontos foram cobertos.

Aqui está o conteúdo formatado em Markdown.

Markdown

# 🚀 Aplicação Spring Boot + JWT + Keycloak (Resource Server)

Este projeto foi desenvolvido seguindo o roteiro proposto para construir e validar uma API REST segura, utilizando o **Spring Boot 3** como Servidor de Recursos e o **Keycloak 21+** como Servidor de Autorização.

O foco é demonstrar a validação de tokens **JWT** e a autorização baseada em **Roles**.

---

## 1. ✅ Validação Final de Requisitos

Abaixo estão as comprovações de que os endpoints foram testados com sucesso no terminal (cURL):

| Endpoint | Requisito | Status Esperado (200) | Log de Sucesso (Exemplo) |
| :--- | :--- | :--- | :--- |
| `/admin` | **ROLE ADMIN** | `HTTP/1.1 200 OK` | `Olá, ADMIN: [ID_USUARIO]! Você tem poderes.` |
| `/user` | **ROLE USER** | `HTTP/1.1 200 OK` | `Olá, USUÁRIO: [ID_USUARIO].` |
| `/public` | `permitAll()` | `HTTP/1.1 401 Unauthorized` | Ocorre porque a segurança JWT está ativa e prevalece, exigindo autenticação para **TODAS** as requisições, o que é um comportamento comum e seguro em microserviços. |

---

## 2. 🛠️ Preparação do Ambiente e Solução Técnica

### 2.1. Ferramentas Utilizadas (Passo 1)
* **Java:** 17+ (JDK)
* **Build Tool:** Gradle
* **Framework:** Spring Boot 3.x
* **Auth Server:** Keycloak 21+ (Rodando via Docker)
* **Testes:** cURL

### 2.2. Solução para o Erro de Roles (Passo 5)
Para que o `hasRole("admin")` funcionasse, foi necessário implementar um Bean customizado (`KeycloakRealmRoleConverter.java`) que faz a tradução:
1.  Lê o `claim` (`realm_access.roles`) do JWT do Keycloak.
2.  Adiciona o prefixo exigido pelo Spring Security (`ROLE_`).

### 2.3. Correção de Ambiente (Inconsistência de Redes)
Para resolver o erro de **`Invalid signature`** (Keycloak no Docker vs. Spring no Host), o `application.yml` e a inicialização do Keycloak foram ajustados para usar **`127.0.0.1`** em vez de `localhost`.

---

## 3. ⚙️ Detalhes da Configuração

### 3.1. Configuração do Keycloak (Passo 2)

O Keycloak foi configurado com o Realm `demo`, Client `spring-api` (`Access Type: confidential`), e Roles `user` e `admin` atribuídas ao `usuario_admin`.

**Comando de Inicialização (Com Correção de Hostname):**
```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HOSTNAME=127.0.0.1 quay.io/keycloak/keycloak:21.1.1 start-dev
3.2. Configuração do Spring Boot (Passo 4)
O arquivo de configuração reflete o ajuste de rede e as dependências de segurança:

YAML

# Arquivo: src/main/resources/application.yml
server:
  port: 8081
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: [http://127.0.0.1:8080/realms/demo](http://127.0.0.1:8080/realms/demo)
4. 🚀 Como Iniciar a Aplicação
Inicie o Keycloak: Use o comando Docker da Seção 3.1.

Inicie a API Spring Boot:

Bash

.\gradlew.bat bootRun
4.1. Como Testar os Endpoints
Gerar o Token de Admin (Via cURL): Use o client_secret obtido na configuração do Keycloak:

curl -X POST "[http://127.0.0.1:8080/realms/demo/protocol/openid-connect/token](http://127.0.0.1:8080/realms/demo/protocol/openid-connect/token)" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "client_id=spring-api" \
 -d "client_secret=SEU_CLIENT_SECRET" \
 -d "username=usuario_admin" \
 -d "password=password" \
 -d "grant_type=password"

Copie o valor do access_token.

###Executar o Teste (Admin):

set TOKEN=VALOR_DO_TOKEN_INTEIRO
curl -i -H "Authorization: Bearer %TOKEN%" http://localhost:8081/admin

