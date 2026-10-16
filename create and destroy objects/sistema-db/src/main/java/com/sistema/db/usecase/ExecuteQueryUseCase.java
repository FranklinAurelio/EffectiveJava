package com.sistema.db.usecase;

import com.sistema.db.infrastructure.DatabaseConnectionPool;
import com.sistema.db.infrastructure.DatabaseResource;
import com.sistema.db.domain.QueryResult;
import com.sistema.db.domain.DatabaseConfig;

/**
 * Coordena os passos necessários para demonstrar a execução de uma consulta:
 * registra uma conexão, usa um recurso e cria um resultado.
 */
public class ExecuteQueryUseCase {

    // Dependência usada para registrar o estado da conexão.
    // "private" mantém o campo acessível somente nesta classe e "final"
    // impede que ele passe a apontar para outro pool depois da construção.
    private final DatabaseConnectionPool connectionPool;

    // Configuração recebida e guardada para ser usada durante o caso de uso.
    // DatabaseConfig é imutável, então pode ser compartilhada com segurança.
    private final DatabaseConfig config;

    // O construtor recebe as dependências de que esta classe precisa.
    // Esse padrão é chamado de injeção de dependências: em vez de criar o pool
    // e a configuração aqui, a classe recebe esses objetos de quem a utiliza.
    public ExecuteQueryUseCase(DatabaseConnectionPool connectionPool, DatabaseConfig config) {
        this.connectionPool = connectionPool;
        this.config = config;
    }

    // Executa os passos demonstrativos usando as dependências guardadas no objeto.
    // "void" indica que o método não devolve um resultado ao chamador.
    public void execute() {
        // Exibe a configuração. Isso usa o toString() de DatabaseConfig.
        // Nesta demonstração, os dados não são usados para abrir uma conexão real.
        System.out.println("initializing UseCase with config: " + config);

        // Registra "AppDB" como conectada no pool recebido pelo construtor.
        // O pool deste exemplo guarda somente um status; não abre conexão JDBC.
        connectionPool.connect("AppDB");

        // try-with-resources fecha automaticamente o recurso ao sair do bloco,
        // inclusive se ocorrer uma exceção. DatabaseResource implementa
        // AutoCloseable para que isso funcione.
        try (DatabaseResource resource = new DatabaseResource()) {
            // Executa a operação demonstrativa no recurso.
            resource.runningOperation();

            // Cria um objeto que representa o resultado da consulta.
            // Neste exemplo, a consulta é apenas um texto; ela não é enviada ao banco.
            QueryResult result = QueryResult.successfullyCreated("SELECT * FROM tb_usuarios");

            // Exibe o objeto. Como QueryResult não define toString(), Java usa
            // a implementação herdada de Object, que não mostra o texto de data.
            System.out.println("Query executed successfully: " + result);
        } catch (Exception e) {
            // Captura exceções ocorridas ao usar ou fechar o recurso e envia
            // a mensagem de erro para a saída de erro do console.
            System.err.println("Error during database operation: " + e.getMessage());
        }

    }

}
