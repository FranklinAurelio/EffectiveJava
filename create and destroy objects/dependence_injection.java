//Uso inapropriado da classe utilitaria estatica
public class SpellChecker {
    private static final Lexicon dictionary = new Lexicon();

    private SpellChecker() {
    } // Impede a instanciação

    public static boolean isValid(String word) {
        return dictionary.contains(word);
    }

    public static List<String> suggestions(String typo) {
        return dictionary.findSimilar(typo);
    }
}

// Uso inapropriado do singleton
public class SpellChecker {
    private static final SpellChecker INSTANCE = new SpellChecker();
    private final Lexicon dictionary = new Lexicon();

    private SpellChecker() {
    } // Impede a instanciação

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        return dictionary.findSimilar(typo);
    }
}

// injecao de dependencia proporciona flexibilidade e testabilidade
public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        return dictionary.findSimilar(typo);
    }
}