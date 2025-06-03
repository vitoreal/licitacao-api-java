# 📑 Sistema de Licitações

Aplicação em **Java - Spring Boot** para importação, listagem e marcação de licitações públicas como visualizadas.

---

## ⚙️ Tecnologias

- **Backend:** 
  - Java 17+
  - Spring Boot 3
  - Spring Web, Spring Data JPA
  - Jsoup (web scraping)
  - Maven
- **Banco de Dados:** MySQL/MariaDB
- **API RESTful:** com paginação, filtros e marcação de licitacoes lidas
- **Testes:** Junit
- **Documentação:** Swagger (OpenAPI)


---

## ⚙️ Pré-requisitos

- Docker e Docker Compose instalados.  
  (https://docs.docker.com/get-docker/)


## 🚀 Instalação

### Clonando o Repositório

- Utilize o comando abaixo clonar o projeto:

```bash
git clone https://github.com/vitoreal/licitacao-api-java.git
```

### Configuração de Ambiente

1. Execute os containers usando Docker com o Compose configurado.

### Build e Execução com Docker

- Utilize o comando abaixo para rodar os containers:
    ```bash
    docker-compose up -d
    ```

### Criação do banco

- Script para criar a tabela está dentro da pasta database com o nome licitacaodb.sql


## 📝 Documentação / Swagger

A documentação da API está disponível via Swagger. Após iniciar os containers, acesse:

http://localhost:8080/swagger-ui/index.html

## 🔗 Links dos Ambientes

### Ambiente de DEV
- **Backend:** [http://localhost:8080](http://localhost:8080)