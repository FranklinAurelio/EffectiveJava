/*
 * Um singleton é uma classe que permite apenas uma instância de si mesma ser
 * criada e dá acesso a essa instância para qualquer outro código.
 * O padrão Singleton é útil quando você precisa de exatamente um objeto para
 * coordenar ações em todo o sistema.
 */

//Singleton como um campo final público estático
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public void leaveTheBuilding() {
        System.out.println("Elvis has left the building");
    }
}

// Singleton como um static factory
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        System.out.println("Elvis has left the building");
    }
}

// Método readResolve para manter a propriedade de singleton durante a
// desserialização
public class Elvis implements Serializable {
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        System.out.println("Elvis has left the building");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

// Enum singleton - a abordagem mais simples e robusta
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Elvis has left the building");
    }
}