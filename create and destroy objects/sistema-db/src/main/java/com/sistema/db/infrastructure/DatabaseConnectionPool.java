package com.sistema.db.infrastructure;

// HashMap é uma implementação de mapa que associa uma chave a um valor.
import java.util.HashMap;
// Map é a interface que descreve operações comuns de mapas, como put e get.
import java.util.Map;

/**
 * Demonstra um objeto único compartilhado para registrar estados de conexão.
 *
 * Apesar do nome, este exemplo não abre conexões reais nem mantém um pool
 * de conexões JDBC: apenas guarda textos com o estado de cada connectionString.
 */
public enum DatabaseConnectionPool {
    // Um enum com uma única constante funciona como Singleton:
    // o programa tem uma única instância acessível por
    // DatabaseConnectionPool.INSTANCE.
    INSTANCE;

    // Guarda cada string de conexão como chave e seu estado como valor.
    // "private" limita o acesso a este mapa à própria classe.
    // "final" impede que o campo passe a apontar para outro mapa;
    // o conteúdo do mapa ainda pode ser alterado.
    private final Map<String, String> connectionPool = new HashMap<>();

    // Registra a string de conexão com o estado "Connected".
    // put adiciona a chave se ela ainda não existir ou substitui seu valor.
    public void connect(String connectionString) {
        connectionPool.put(connectionString, "Connected");
        // Imprime uma mensagem para informar essa ação no console.
        System.out.println("Connected to database: " + connectionString);
    }

    // Retorna o estado registrado para a string informada.
    // Se ela não estiver no mapa, getOrDefault devolve "Not connected".
    public String getConnectionStatus(String connectionString) {
        return connectionPool.getOrDefault(connectionString, "Not connected");
    }
}
