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

    // Recebe a configuração que descreve como a aplicação deve acessar o banco.
    // O método ainda não usa os campos de config para abrir uma conexão real;
    // neste exemplo, ele apenas os exibe no console.
    public void execute(DatabaseConfig config) {
        System.out.println("initializing UseCase with config: " + config);

        // Acessa a instância única do enum e registra "AppDB" como conectada.
        // Esse pool de exemplo armazena somente um status; não abre conexão JDBC.
        DatabaseConnectionPool.INSTANCE.connect("AppDB");

        // try-with-resources fecha automaticamente o recurso ao sair do bloco,
        // inclusive se ocorrer uma exceção. DatabaseResource implementa
        // AutoCloseable para que isso funcione.
        try (DatabaseResource resource = new DatabaseResource()) {
            // Executa a operação demonstrativa no recurso.
            resource.runningOperation();

            // Cria um objeto que representa o resultado da consulta.
            // Neste exemplo, a consulta é apenas um texto; ela não é enviada ao banco.
            QueryResult result = QueryResult.successfullyCreated("SELECT * FROM tb_usuarios");

            // Exibe a referência do resultado. Como QueryResult não define
            // toString(), esta impressão usa a representação padrão de Object.
            System.out.println("Query executed successfully: " + result);
        } catch (Exception e) {
            // Captura exceções ocorridas ao usar ou fechar o recurso e envia
            // a mensagem de erro para a saída de erro do console.
            System.err.println("Error during database operation: " + e.getMessage());
        }

    }

}
