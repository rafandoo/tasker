<h1 align="center">Tasker ✅</h1>

## 📝 Descrição do projeto

O **Tasker** é um microsserviço construída durante o workshop de Quarkus na Semana Acadêmica de Ciência da Computação do
**Instituto Federal Catarinense – Campus Rio do Sul**, realizado no dia **22/05/2025**.

Este projeto tem como objetivo demonstrar na prática como criar APIs RESTful utilizando o **framework Quarkus**, abordando conceitos como:

- Injeção de dependências com CDI.
- Mapeamento de entidades com JPA/Hibernate.
- Persistência em banco de dados com Panache.
- Validações com Bean Validation.
- Testes automatizados com `@QuarkusTest`.
- Documentação com OpenAPI/Swagger.
- Segurança com autenticação e autorização basic.

 Mais detalhes sobre o problema proposto podem ser encontrados no arquivo [PROBLEM.md](docs/PROBLEM.md).

## 📚 Sobre o Workshop

Este projeto é parte do conteúdo apresentado no workshop de introdução ao Quarkus, com foco em demonstrar os principais recursos da plataforma
para desenvolvimento moderno de APIs Java.

Durante o workshop, os seguintes tópicos serão abordados:

- Introdução ao Quarkus e sua proposta;
- Criação de um projeto do zero com Quarkus;
- Implementação de uma API RESTful completa;
- Integração com banco de dados e JPA;
- Boas práticas de desenvolvimento;
- Demonstração de testes automatizados.

## 🛠️ Tecnologias utilizadas

<p align="center">
    <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"/>
    <img src="https://img.shields.io/badge/Java-ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
    <img src="https://img.shields.io/badge/Quarkus-4695EB.svg?style=for-the-badge&logo=quarkus&logoColor=white" alt="Quarkus"/>
    <img src="https://img.shields.io/badge/Hibernate-59666C.svg?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate"/>
    <img src="https://img.shields.io/badge/H2-09476B.svg?style=for-the-badge&logo=h2database&logoColor=white" alt="H2"/>
    <img src="https://img.shields.io/badge/Lombok-D9230F.svg?style=for-the-badge" alt="Lombok"/>
    <img src="https://img.shields.io/badge/OpenAPI-6BA539.svg?style=for-the-badge&logo=openapi-initiative&logoColor=white" alt="OpenAPI"/>
    <img src="https://img.shields.io/badge/Swagger-85EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger"/>
    <img src="https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit5"/>
</p>

## 🚀 Como executar o projeto

### Pré-requisitos

- IDE para desenvolvimento Java (recomendado utilizar IntelliJ CE).
- JDK 21+ instalado e JAVA_HOME configurado propriamente.
- Gradle 8.13.
- Mandrel instalado e configurado. *
- Um ambiente de desenvolvimento C funcional. *

> 💡 Os itens marcados com asterisco são necessários apenas para gerar e executar o projeto em modo nativo.

Mais detalhes sobre essas instalações estão disponíveis na [documentação de configuração do ambiente]().

### Executando o projeto em modo de desenvolvimento

Para executar o projeto em modo de desenvolvimento, siga os seguinte passos:

1. Clone o repositório para sua máquina local, por exemplo, utilizando o comando `git clone https://github.com/rafandoo/tasker.git`.
2. Abra o projeto em sua IDE de desenvolvimento Java.
3. Execute o comando `./gradlew quarkusDev` no terminal para iniciar o servidor de desenvolvimento.
4. Abra o navegador e acesse a URL `http://localhost:8080/q/dev-ui/` para visualizar a interface de desenvolvimento do Quarkus.

## 📦 Executável nativo

O executável nativo é, essencialmente, uma aplicação Java compilada para um binário que pode ser executado diretamente pelo sistema operacional, sem a necessidade de uma máquina virtual Java (JVM).

Isso significa que o sistema operacional roda o executável de forma direta, eliminando a dependência da JVM convencional.

O executável nativo gerado pelo nosso aplicativo inclui:

- O código da aplicação
- As bibliotecas necessárias
- As APIs Java utilizadas
- Uma versão enxuta da JVM embutida

Essa abordagem reduz o tempo de inicialização da aplicação e minimiza o uso de espaço em disco.

### Como gerar o executável nativo

Para criar o executável nativo, siga os seguintes passos:

1. Abra o terminal na raiz do projeto.
2. Execute o seguinte comando no terminal:

```bash
./gradlew clean build
```

Após a conclusão do processo, o executável estará disponível na pasta `build/`.

**Atenção (Windows)**:

Em ambientes Windows, use o comando abaixo no lugar de `./gradlew clean build`:

```bash
vcvars64.bat && ./gradlew clean build
```

Isso é necessário devido a um problema específico no processo de empacotamento no Windows, que exige a inicialização do Microsoft Native Tools for Visual Studio antes da geração do executável.

## 🧪 Executando os testes

O projeto contém um conjunto de testes automatizados para garantir a qualidade e o funcionamento correto do
microsserviço. Os testes são implementados utilizando o **JUnit 5** em conjunto com **RestAssured** para a validação dos
endpoints REST.

### Executando os testes

Para executar todos os testes unitários disponíveis, utilize o seguinte comando:

```bash
./gradlew test
```

### Entrando no ambiente de testes

O Quarkus oferece um ambiente de testes integrado, que facilita a execução de testes automatizados em um contexto semelhante ao ambiente de desenvolvimento. Para ativá-lo, utilize o comando abaixo:

```bash
./gradlew quarkusTest
```

## 🔍 Documentação da API

A documentação da API está disponível através do Swagger UI. Para acessar a documentação, siga os seguintes passos:

1. Inicie o servidor de desenvolvimento do Quarkus, caso ainda não esteja em execução.
2. Abra o navegador e acesse a URL `http://localhost:8080/q/swagger-ui/` para visualizar a documentação da API.

## 📚 Documentação Adicional

- [Quarkus](https://quarkus.io/)
- [Contexto e Injeção de Dependência](https://quarkus.io/guides/cdi-reference)
- [Hibernate ORM com Panache](https://quarkus.io/guides/hibernate-orm-panache)
- [Validação de dados com o Hibernate](https://quarkus.io/guides/validation)
- [Segurança com JPA](https://quarkus.io/guides/security-jpa)
- [REST](https://quarkus.io/guides/rest)
- [OpenAPI / Swagger UI](https://quarkus.io/guides/openapi-swaggerui)
- [Lombok](https://projectlombok.org/)
- [JUnit 5 e RestAssured](https://quarkus.io/guides/getting-started-testing) – para testes automatizados

## 🔑 Licença

The [MIT License]() (MIT)

Copyright :copyright: 2025 - Rafael Camargo
