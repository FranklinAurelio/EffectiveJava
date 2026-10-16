package com.sistema.db.di;

import com.sistema.db.domain.DatabaseConfig;
import com.sistema.db.infrastructure.DatabaseConnectionPool;
import com.sistema.db.usecase.ExecuteQueryUseCase;

/**
 * Centraliza a criação e a ligação dos objetos usados pela aplicação.
 * Uma classe com esse papel é chamada de container de injeção de dependências.
 */
public class AppContainer {

    // Cria e devolve um caso de uso já preparado com os objetos de que precisa.
    // Centralizar essa montagem evita que ExecuteQueryUseCase precise criar
    // suas próprias dependências.
    public ExecuteQueryUseCase createExecuteQueryUseCase() {
        // Monta a configuração com o Builder.
        // Host e porta são obrigatórios; os outros valores podem ser ajustados
        // antes de build() criar o objeto DatabaseConfig.
        DatabaseConfig config = new DatabaseConfig.Builder("127.0.0.1", 3306)
                .user("root_user")
                .timeout(45)
                .useSSL(true)
                .build();

        // Obtém a única instância do enum DatabaseConnectionPool.
        // Este exemplo guarda estados em memória e não abre uma conexão JDBC.
        DatabaseConnectionPool connectionPool = DatabaseConnectionPool.INSTANCE;

        // Injeta as dependências no construtor do caso de uso e devolve o objeto
        // pronto para ser utilizado por outra parte da aplicação.
        return new ExecuteQueryUseCase(connectionPool, config);
    }
}
