package com.sistema.db.domain;

/**
 * Guarda o texto que representa o resultado de uma consulta.
 * A classe é imutável: depois de criado, o resultado não pode ser alterado.
 */
public class QueryResult {
    // "private" impede acesso direto por outras classes.
    // "final" faz com que a referência seja definida somente no construtor.
    // String também é imutável, então seu conteúdo não muda depois de atribuído.
    private final String data;

    // Construtor privado: quem está fora desta classe não cria objetos diretamente.
    // Assim, a criação é controlada pelos métodos estáticos abaixo.
    private QueryResult(String data) {
        this.data = data;
    }

    // Método de criação estático (também chamado de fábrica).
    // Ele recebe o texto do resultado e devolve um novo QueryResult.
    public static QueryResult successfullyCreated(String data) {
        // Exibe uma mensagem no console, útil para acompanhar esta criação.
        System.out.println("QueryResult successfully created with data: " + data);
        return new QueryResult(data);
    }

    // Cria uma única instância reutilizável que representa a ausência de dados.
    // Como QueryResult é imutável, compartilhar esta instância é seguro.
    private static final QueryResult EMPTY_CACHE = new QueryResult("No data available");

    // Devolve a instância vazia compartilhada, em vez de criar outra a cada
    // chamada.
    public static QueryResult emptyCacheCreated() {
        // Exibe uma mensagem no console ao devolver a instância.
        System.out.println("Returning empty cache QueryResult");
        return EMPTY_CACHE;
    }

    // Getter: permite consultar o texto sem permitir que ele seja alterado.
    public String getData() {
        return data;
    }
}
