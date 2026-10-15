package com.sistema.db.domain;

/**
 * Representa as configurações necessárias para acessar um banco de dados.
 *
 * A classe usa o padrão Builder: em vez de passar muitos valores de uma vez
 * para um construtor, configuramos um Builder e, no final, criamos o objeto.
 */
public class DatabaseConfig {
    // "private" protege os dados: outras classes não podem acessá-los diretamente.
    // "final" garante que cada valor seja definido uma vez e não seja trocado
    // depois.
    private final String host;
    private final int port;
    private final String user;
    private final int timeout;
    private final boolean useSSL;

    // O construtor é private para que a configuração só seja criada pelo método
    // build().
    // Ele copia para este objeto os valores reunidos pelo Builder.
    private DatabaseConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.user = builder.user;
        this.timeout = builder.timeout;
        this.useSSL = builder.useSSL;
    }

    // Builder é uma classe auxiliar que permite montar DatabaseConfig passo a
    // passo.
    // "static" permite usar Builder sem criar antes um objeto DatabaseConfig.
    public static class Builder {
        // Host e porta são obrigatórios e são definidos ao criar o Builder.
        private final String host;
        private final int port;

        // Estes campos são opcionais; começam com valores padrão.
        private String user = "admin";
        private int timeout = 30;
        private boolean useSSL = false;

        // Recebe os dois valores obrigatórios para iniciar a montagem.
        public Builder(String host, int port) {
            this.host = host;
            this.port = port;
        }

        // Define o usuário. Retornar "this" permite encadear chamadas,
        // por exemplo: new Builder(...).user(...).timeout(...).
        public Builder user(String val) {
            this.user = val;
            return this;
        }

        // Define por quantos segundos a conexão deve aguardar antes de expirar.
        public Builder timeout(int val) {
            this.timeout = val;
            return this;
        }

        // Define se a conexão deve usar SSL, uma camada de comunicação criptografada.
        public Builder useSSL(boolean val) {
            this.useSSL = val;
            return this;
        }

        // Finaliza a montagem e cria a configuração com os valores escolhidos.
        public DatabaseConfig build() {
            return new DatabaseConfig(this);
        }
    }

    // @Override informa ao Java que estamos fornecendo nossa versão de toString(),
    // método herdado de Object, para exibir os dados de forma legível.
    @Override
    public String toString() {
        return String.format("DatabaseConfig [host=%s, port=%d, user=%s, timeout=%d, useSSL=%b]",
                host, port, user, timeout, useSSL);
    }

}
