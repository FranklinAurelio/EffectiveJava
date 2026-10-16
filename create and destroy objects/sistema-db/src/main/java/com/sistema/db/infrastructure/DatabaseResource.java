package com.sistema.db.infrastructure;

import java.lang.ref.Cleaner;

/**
 * Exemplo de um recurso que pode ser fechado explicitamente e também possui
 * uma ação de segurança executada pelo Cleaner caso seja esquecido.
 *
 * Na prática, recursos como conexões e arquivos devem ser fechados com
 * try-with-resources; não se deve depender do Cleaner para fechá-los.
 */
public class DatabaseResource implements AutoCloseable {
    // Cleaner é um serviço do Java que pode executar uma ação quando um objeto
    // deixa de ser alcançável. Uma única instância é compartilhada pela classe.
    private static final Cleaner CLEANER = Cleaner.create();

    // Estado separado do DatabaseResource para que a ação do Cleaner não precise
    // guardar uma referência ao próprio objeto que está sendo limpo.
    private static class State implements Runnable {
        // Indica se este exemplo considera que o recurso ainda está aberto.
        private boolean opened;

        State(boolean opened) {
            this.opened = opened;
        }

        // run é chamado quando o Cleaner executa esta ação.
        // Aqui apenas imprimimos uma mensagem; um recurso real precisaria ser
        // liberado nesta ação, sem depender do objeto DatabaseResource.
        @Override
        public void run() {
            if (opened) {
                System.out.println("Cleaner: Closing database resource");
            }
        }
    }

    // Guarda o estado usado pela ação de limpeza.
    private final State state;
    // Representa o registro deste objeto no Cleaner e permite solicitar a limpeza.
    private final Cleaner.Cleanable cleanable;
    // Buffer de exemplo para representar dados mantidos pelo recurso.
    private byte[] dataBuffer;

    public DatabaseResource() {
        // Começa marcando o recurso como aberto.
        this.state = new State(true);
        // Registra uma ação de limpeza associada a este objeto.
        this.cleanable = CLEANER.register(this, state);
        // Reserva um buffer de 1 MB para simular memória usada pelo recurso.
        this.dataBuffer = new byte[1024 * 1024];
        System.out.println("Database resource created" + dataBuffer.length + " bytes allocated");
    }

    // Simula uma operação que utiliza o recurso.
    public void runningOperation() {
        System.out.println("Running operation on database resource");
    }

    // AutoCloseable permite fechar o objeto explicitamente e usá-lo
    // com try-with-resources, que chama close() ao terminar o bloco.
    @Override
    public void close() {
        // Marca como fechado para que a ação do Cleaner não trate como aberto.
        state.opened = false;
        // Executa a limpeza registrada agora; não é necessário esperar o Cleaner.
        cleanable.clean();
        // Remove a referência ao buffer para que ele possa ser coletado pelo GC.
        // O próprio GC decide quando recuperar essa memória.
        dataBuffer = null;
        System.out.println("Database resource closed");
    }

}
