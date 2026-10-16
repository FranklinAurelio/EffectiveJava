package com.sistema.db;

import com.sistema.db.domain.DatabaseConfig;
import com.sistema.db.usecase.ExecuteQueryUseCase;
import com.sistema.db.util.SecurityUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting database application");

        DatabaseConfig config = new DatabaseConfig.Builder("127.0.0.1", 3306)
                .user("root_user")
                .timeout(45)
                .useSSL(true)
                .build();

        String passwordEncrypted = SecurityUtils.criptPassword("my_secure_password");
        System.out.println("Encrypted password: " + passwordEncrypted);

        ExecuteQueryUseCase useCase = new ExecuteQueryUseCase();
        useCase.execute(config);

        System.out.println("Database application finished");
    }

}
