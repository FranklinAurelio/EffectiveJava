package com.sistema.db;

import com.sistema.db.usecase.ExecuteQueryUseCase;
import com.sistema.db.util.SecurityUtils;
import com.sistema.db.di.AppContainer;

/**
 * Ponto de entrada da aplicação: a execução começa no método main.
 * Esta classe coordena a inicialização e chama os componentes necessários.
 */
public class Main {
    // A JVM procura este método para iniciar o programa.
    // "public" permite que a JVM o acesse; "static" permite chamá-lo sem criar
    // um objeto Main; "void" indica que ele não devolve um valor.
    // args pode receber argumentos informados ao iniciar o programa.
    public static void main(String[] args) {
        // Exibe uma mensagem para indicar que o programa começou.
        System.out.println("Starting database application");

        // Chama um método estático utilitário sem criar um objeto SecurityUtils.
        // Apesar do nome da variável e da mensagem, o método atual usa hashCode(),
        // que não é uma forma segura de criptografar ou armazenar senhas reais.
        String passwordEncrypted = SecurityUtils.criptPassword("my_secure_password");
        // Concatena o texto com o valor calculado para exibi-los juntos.
        System.out.println("Encrypted password: " + passwordEncrypted);

        // O container centraliza a criação e a ligação dos objetos da aplicação.
        AppContainer container = new AppContainer();

        // Pede ao container um caso de uso já montado com suas dependências.
        ExecuteQueryUseCase useCase = container.createExecuteQueryUseCase();
        // Executa o fluxo demonstrativo do caso de uso.
        useCase.execute();

        // Informa que o fluxo principal chegou ao fim.
        System.out.println("Database application finished");
    }

}
