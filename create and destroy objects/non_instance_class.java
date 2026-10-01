//Classe utilitária nao instanciavel

public class Non_instance_class {
    // Construtor privado previne a instanciação
    private Non_instance_class() {
        throw new AssertionError();
    }
}
