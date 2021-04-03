<h2>Bookstore API Manager</h2>

O objetivo do projeto Bookstore API Manager é disponibilizar uma API para cadastro dos livros de uma livraria através de uma API REST.

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/books
```

Para abrir a documentação (disponibilizada através do Swagger 2) de todas as operações implementadas com o padrão arquitetural REST, acesse o seguinte link abaixo:

```
http://localhost:8080/swagger-ui.html
```

As seguintes ferramentas abaixo foram utilizadas como parte do desenvolvimento do projeto:

* Java 11
* Maven 3.6.3
* Banco de dados H2 como SGBD (em ambos ambientes, Dev e Prod)
* Swagger 2 para a documentação de todos os endpoints desenvolvidos dentro do projeto

Abaixo, segue o link do projeto implantado no Heroku:

```
https://bookstoremanager-app.herokuapp.com/api/v1/books
```

O link da documentação no Heroku, implementada também através do Swagger, está no link abaixo:

```
https://bookstoremanager-app.herokuapp.com/swagger-ui.html
```
