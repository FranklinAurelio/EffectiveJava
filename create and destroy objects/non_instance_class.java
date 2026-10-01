//Classe utilitária nao instanciavel

public class non_instance_class {
    // Construtor privado previne a instanciação
    private non_instance_class() {
        throw new AssertionError();
    }
}
