# Sistema de Gerenciamento de Biblioteca

Este projeto é um sistema de desktop para gerenciamento de uma biblioteca, desenvolvido em Java com a biblioteca Swing para a interface gráfica e seguindo o padrão de arquitetura MVC (Model-View-Controller). O sistema permite o cadastro de autores, livros e usuários, além do controle de empréstimos. Ele também gera relatórios de empréstimos ativos e vencidos utilizando JasperReports.

## Funcionalidades

- **Gerenciamento de Autores:** Cadastro, edição, exclusão e listagem de autores.
- **Gerenciamento de Livros:** Cadastro, edição, exclusão e listagem de livros.
- **Gerenciamento de Usuários:** Cadastro, edição, exclusão e listagem de usuários.
- **Gerenciamento de Empréstimos:** Registro, devolução e listagem de empréstimos.
- **Relatórios:** Geração de relatórios de empréstimos ativos e vencidos.

## Tecnologias Utilizadas

- **Java 21:** Linguagem de programação principal.
- **Swing:** Biblioteca para a criação da interface gráfica.
- **MySQL:** Banco de dados para o armazenamento das informações.
- **JasperReports:** Biblioteca para a geração de relatórios.
- **Maven:** Ferramenta para o gerenciamento de dependências e build do projeto.

## Pré-requisitos

- Java 21 ou superior.
- MySQL.
- Apache Maven.

## Como Executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```

2. **Crie o banco de dados:**
   - Crie um banco de dados chamado `biblioteca` no seu servidor MySQL.

3. **Configure a conexão com o banco de dados:**
   - Abra o arquivo `src/BibliotecaApp.java` e altere as credenciais de acesso ao banco de dados (usuário e senha) na linha a seguir:
     ```java
     Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin");
     ```

4. **Compile e execute o projeto:**
   - Você pode executar o projeto diretamente da sua IDE ou utilizar o Maven para compilar e empacotar a aplicação:
     ```bash
     mvn clean install
     java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
     ```

## Estrutura do Projeto

O projeto segue a estrutura padrão de um projeto Maven e está organizado da seguinte forma:

- `src/main/java`: Contém o código-fonte da aplicação, dividido em pacotes:
  - `controller`: Controladores que fazem a ponte entre a View e o Model.
  - `model`: Classes de modelo (entidades) e DAO (Data Access Object).
  - `view`: Classes da interface gráfica (telas do sistema).
  - `BibliotecaApp.java`: Classe principal que inicia a aplicação.
- `pom.xml`: Arquivo de configuração do Maven com as dependências do projeto.
- `EmprestimosAtivos.jrxml`, `EmprestimosVencidos.jrxml`: Arquivos de template do JasperReports para os relatórios.
