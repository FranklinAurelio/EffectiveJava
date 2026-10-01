String s = new String("Hello"); // Instanciação desnecessária de objeto
String s = "Hello"; // Instanciação de objeto desnecessária evitada

// O desempenho pode ser melhorado
static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}
/*
 * Enquanto o Strings.matches é o jeito mais simples de verificar se uma string
 * corresponde a uma expressão regular, ele cria um objeto Pattern e um Matcher
 * a cada invocação.
 * Se o método isRomanNumeral for chamado com frequência, a criação desses
 * objetos pode ser um gargalo de desempenho.
 */

// Reuso do objeto trabalhoso para melhorar o desempenho
public class RomanNumerals {
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }
}

// Absurdamente lento
private static long sum() {
    Long sum = 0L;
    for (long i = 0; i < Long.MAX_VALUE; i++) {
        sum += i;
    }
    return sum;
}

// De preferencia aos tipos primitivos em vez de seus equivalentes wrapper e
// tome cuidado com a autoboxing
