/* 
Padrão telescoping constructor (telescoping constructor pattern) - Um padrão de projeto que consiste em criar múltiplos construtores
com diferentes números de parâmetros, cada um chamando o próximo construtor com mais parâmetros, até chegar ao construtor mais completo.
Esse padrão pode levar a código difícil de ler e manter, especialmente quando há muitos parâmetros opcionais.

Não é escalavel!
*/ 

public class NutritionFacts {
    private final int servingSize; // (mL) required
    private final int servings;    // (per container) required
    private final int calories;     // optional
    private final int fat;          // (g) optional
    private final int sodium;       // (mg) optional
    private final int carbohydrate; // (g) optional

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories,
                          int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories,
                          int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}

/*
Padrão JavaBeans (JavaBeans pattern) - Um padrão de projeto que consiste em criar uma classe com um construtor sem argumentos e métodos setter para cada campo.
Esse padrão é mais legível e flexível do que o padrão telescoping constructor, mas não é imutável e pode levar a objetos em estados inconsistentes.

Permite a inconsistência de estado, autoriza a mutabilidade.
*/

public class NutritionFacts {
    //Parametros iniciados com valores padrão (se houver)
    private int servingSize = -1; // (mL) required sem valor padrão
    private int servings = -1;    // (per container) required sem valor padrão
    private int calories = 0;     // optional
    private int fat = 0;          // (g) optional
    private int sodium = 0;       // (mg) optional
    private int carbohydrate = 0; // (g) optional

    public NutritionFacts() {
        // Construtor sem argumentos
    }

    //setters para cada campo, permitindo a configuração dos valores após a criação do objeto

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}

//Criação de instsancia usando o padrão JavaBeans

NutritionFacts cocaCola = new NutritionFacts();
cocaCola.setServingSize(240);
cocaCola.setServings(8);
cocaCola.setCalories(100);
cocaCola.setSodium(35);
cocaCola.setCarbohydrate(27);

/*
Um JavaBean pode apresentar um estado parcialmente inconsistente durante sua construção, pois os campos podem ser configurados em qualquer ordem e
não há garantia de que todos os campos obrigatórios foram definidos antes do uso do objeto. Isso pode levar a erros em tempo de execução se o objeto
for usado antes de estar completamente configurado.
*/

/*
Padrão Builder (Builder pattern) - O padrão builder simula os parâmetros opcionais nomeados.
O Builder pattern é mais legível e flexível do que os padrões telescoping constructor e JavaBeans, e permite a criação de objetos imutáveis com um estado consistente.
Permite a consistência de estado, autoriza a imutabilidade.
*/

public class NutritionFacts {
    private final int servingSize; // (mL) required
    private final int servings;    // (per container) required
    private final int calories;     // optional
    private final int fat;          // (g) optional
    private final int sodium;       // (mg) optional
    private final int carbohydrate; // (g) optional

    public static class Builder {
        //Parametros obrigatórios
        private final int servingSize;
        private final int servings;

        //Parametros opcionais - inicializados com valores padrão
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}

//Criação de instância usando o padrão Builder
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
        .calories(100)
        .sodium(35)
        .carbohydrate(27)
        .build();

//Padrão Builder para hierarquias de classes 

publica abstract class Pizza {
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // Subclasses devem sobrescrever este método para retornar "this"
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // Defensivamente copia
    }
}