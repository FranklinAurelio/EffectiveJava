package com.sistema.db.util;

/**
 * Reúne operações relacionadas à segurança que podem ser chamadas sem criar
 * um objeto desta classe.
 */
public class SecurityUtils {
    // Construtor privado impede que outras classes criem objetos desta classe.
    // O AssertionError também deixa explícito que essa criação não é permitida.
    // Isso é comum em classes utilitárias que oferecem apenas métodos static.
    private SecurityUtils() {
        throw new AssertionError("Instance of SecurityUtils is not allowed");
    }

    /**
     * Retorna uma representação de texto derivada da senha.
     *
     * Este exemplo usa hashCode(), que não é um método seguro para proteger
     * senhas: ele é fácil de reproduzir e pode gerar colisões. Em um sistema
     * real, use uma função própria para senhas, como Argon2id, bcrypt ou scrypt,
     * com salt. O nome do método não torna este cálculo criptograficamente seguro.
     */
    public static String criptPassword(String password) {
        // hashCode() produz um número baseado no texto; "***" é apenas um sufixo
        // visual e não adiciona segurança criptográfica.
        return password.hashCode() + "***";
    }

}
