# DELIVERYFOOD-API

![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-%236DB33F?logo=springboot&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-UI-%2300BC8C?logo=swagger&logoColor=white)

---
## Sumário

- [Descrição](#descrição)
- [Objetivo](#objetivo)
- [Modelo de Domínio (DDD)](#modelo-de-domínio-ddd)
- [Principais Tecnologias e Funcionalidades](#principais-tecnologias-e-funcionalidades)
  - [Autenticação \& Autorização](#autenticação--autorização)
  - [Relatórios](#relatórios)
  - [E-mails](#e-mails)
  - [Cache \& Sessão](#cache--sessão)
  - [Documentação da API](#documentação-da-api)
  - [Banco de Dados](#banco-de-dados)
  - [Versionamento do Banco](#versionamento-do-banco)
  - [Persistência](#persistência)
  - [Logging](#logging)
- [Docker Compose](#docker-compose)
  - [Arquitetura de Containers](#arquitetura-de-containers)
- [Armazenamento de imagem de produtos](#armazenamento-de-imagem-de-produtos)
- [Versão da API](#versão-da-api)
- [Endpoints da API](#endpoints-da-api)
  - [Endpoints Principais](#endpoints-principais)
  - [Autenticação](#autenticação)
  - [Exemplo de Requisição](#exemplo-de-requisição)
- [Swagger UI - Acessando documentação e explorando endpoints](#swagger-ui---acessando-documentação-e-explorando-endpoints)
- [Considerações Finais](#considerações-finais)
---

## Descrição
API RESTful para um sistema de delivery de comida desenvolvida com:
- Java 17
- Spring Boot 3.4.4
- Arquitetura baseada em Domain-Driven Design (DDD)
- HATEOAS para navegação entre recursos via hipermídia

## Objetivo
- Modelar e Desenvolver um Produto Mínimo Viável (MVP) de API RESTful com Spring Boot.

## Modelo de Domínio (DDD)
O diagrama foi feito usando a abordagem *Domain Driven Design (DDD)*.

<div align="center">
    <img src="docs/img/diagrama-de-classes-de-dominio.jpeg" width=800 alt="Descrição">
    <figcaption>Fonte: <a href="https://assets.algaworks.com/portal/content/especialista-spring-rest/images/diagrama-de-classes-de-dominio.jpg" target="_blank">AlgaWorks</a></figcaption>
</div><br/>

## Principais Tecnologias e Funcionalidades

### Autenticação & Autorização
- OAuth2 com JWT (Authorization Code Flow)
- Controle de acesso baseado em roles

### Relatórios
- Geração de PDFs com JasperReports

### E-mails
- Envio de e-mails transacionais com templates Freemarker
- Uso de Domain Events para disparo automático

### Cache & Sessão
- Armazenamento de sessão distribuído com Redis

### Documentação da API
- SpringDoc OpenAPI
- Swagger UI

### Banco de Dados
- MySQL
- Redis

### Versionamento do Banco
- Flyway

### Persistência
- Hibernate (JPA)

### Logging
- SLF4J
- Logback


## Docker Compose

```bash
# Build e execução com 2 instâncias - troque o número 2 para quantidade de instâncias desejadas
docker compose up --build --scale deliveryfood-api=2
```

### Arquitetura de Containers
<div align="center">
  <img src="docs/img/containers-projeto.png" width=800" alt="Descrição">
</div>

#### Componentes
- Nginx - Proxy reverso (roteamento)
- API (1+ instâncias escaláveis)
- MySQL - Banco de dados principal
- Redis - Cache de sessão distribuído

## Armazenamento de imagem de produtos
- O Armazenamento pode ser feito localmente ou na nuvem, usando a Amazon S3.
- Para definir o tipo de armazenamento, basta definir a variável `local` ou `S3` de ambiente no application.properties. Veja:
  - `deliveryfood.storage.tipo=local` ou `deliveryfood.storage.tipo=S3`

## Versão da API
- A API já está estruturada para suportar versionamento, permitindo a introdução de novas funcionalidades sem impactar versões anteriores.

- **Versão atual:** `v1`

**URL Base:** `http://127.0.0.1/v1`

## Endpoints da API

A API conta com mais de 60 endpoints RESTful. Abaixo estão alguns exemplos representativos. A documentação completa pode ser acessada via Swagger UI: `http://127.0.0.1/swagger-ui/index.html`.

### Endpoints Principais

#### 🏪 Restaurantes
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Listar | `/v1/restaurantes` | GET | Lista restaurantes |
| Ativar | `/v1/restaurantes/{restauranteId}/ativo` | PUT | Ativa um restaurante |
| Abrir | `/v1/restaurantes/{restauranteId}/abertura` | PUT | Abre um restaurante |

#### 🍳 Cozinhas
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Listar | `/v1/cozinhas` | GET | Lista cozinhas com paginação |
| Cadastrar | `/v1/cozinhas` | POST | Cadastra uma nova cozinha |
| Buscar | `/v1/cozinhas/{cozinhaId}` | GET | Busca cozinha por ID |

#### 🛒 Pedidos
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Registrar | `/v1/pedidos` | POST | Cria um novo pedido |
| Buscar | `/v1/pedidos/{codigoPedido}` | GET | Busca pedido por código |
| Confirmar | `/v1/pedidos/{codigoPedido}/confirmacao` | PUT | Confirma um pedido |

#### 💳 Formas de Pagamento
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Listar | `/v1/formas-pagamento` | GET | Lista formas de pagamento |
| Associar | `/v1/restaurantes/{restauranteId}/formas-pagamento/{formaPagamentoId}` | PUT | Associa forma de pagamento a restaurante |

#### 👥 Usuários
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Cadastrar | `/v1/usuarios` | POST | Cria um novo usuário |
| Alterar Senha | `/v1/usuarios/{usuarioId}/senha` | PUT | Atualiza senha do usuário |
| Associar Grupo | `/v1/usuarios/{usuarioId}/grupos/{grupoId}` | PUT | Associa usuário a grupo |

#### 🏙️ Cidades
| Operação | Endpoint | Método | Descrição |
|----------|----------|--------|-----------|
| Listar | `/v1/cidades` | GET | Lista todas as cidades |
| Cadastrar | `/v1/cidades` | POST | Cadastra uma nova cidade |
| Buscar | `/v1/cidades/{cidadeId}` | GET | Busca cidade por ID |
| Atualizar | `/v1/cidades/{cidadeId}` | PUT | Atualiza uma cidade |

### Autenticação
A API utiliza OAuth2 (Authorization Code Flow). Para autenticar:

1. Obtenha token em `/oauth2/token`
2. Inclua no header: `Authorization: Bearer {token}`

### Exemplo de Requisição
```bash
curl -X GET "http://127.0.0.1/v1/restaurantes" \
  -H "Authorization: Bearer {token}"
```

## Swagger UI - Acessando documentação e explorando endpoints

1. Acesse a UI do Swagger em:  
    - `127.0.0.1/swagger-ui/index.html`
2. Autentique-se com OAuth2
<div align="center">
  <img src="docs/gifs/login.gif" alt="Demonstração Completa">
  <br/>
  <sup>Autenticação</sup>
</div>
3. Explore os endpoints disponíveis
<div align="center">
  <img src="docs/gifs/explorando-endpoint.gif" alt="Demonstração Completa">
  <br/>
  <sup>Autenticação</sup>
</div>

## Considerações Finais
Este projeto foi desenvolvido como parte do curso **Especialista Spring REST** da [AlgaWorks](https://www.algaworks.com/).