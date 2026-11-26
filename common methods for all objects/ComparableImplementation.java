import java.util.Collections;
import java.util.Comparator;

/**
 * Reúne exemplos de como ordenar objetos em Java.
 *
 * A interface Comparable permite que um objeto defina sua ordem natural,
 * ou seja, como ele deve ser comparado com objetos do mesmo tipo.
 * Este arquivo possui exemplos didáticos em várias classes e precisa ajustar
 * imports, nomes de tipos e visibilidade antes de ser compilado como um todo.
 * Também, a comparação no Comparable exige coerência com equals: objetos que
 * compareTo considera iguais devem normalmente ser iguais segundo equals.
 */
public class ComparableImplementation {

    // Exemplo de comparação feita campo a campo.
    // areaCode, prefix e lineNum são campos que não são declarados neste trecho,
    // mas seriam atributos da classe que implementa este exemplo.
    // Short.compare retorna negativo, zero ou positivo conforme o primeiro
    // valor seja menor, igual ou maior que o segundo.
    // p representa o objeto que será comparado com o objeto atual.
    public int compareTo(PhoneNumber p) {
        int result = Short.compare(areacode, p.areacode);
        if (result == 0) {
            result = Short.compare(prefix, p.prefix);
            if (result == 0) {
                result = Short.compare(lineNum, p.lineNum);
            }
        }
        return result;
    }

    // Alternativa usando Comparator.
    // COMPARATOR define uma sequência de regras: compara a área, depois o
    // prefixo e, por último, o número da linha somente quando os anteriores forem iguais.
    // "private" impede acesso direto a esta configuração e "final" impede trocar
    // o objeto Comparator guardado neste campo.
    private final Comparator<PhoneNumber> COMPARATOR = Comparator.comparingInt((PhoneNumber pn) -> pn.areacode)
            .thenComparingInt(pn -> pn.prefix)
            .thenComparingInt(pn -> pn.lineNum);

    // compareTo delega a comparação ao COMPARATOR.
    // this representa o objeto atual; pn representa o outro objeto comparado.
    public int compareTo2(PhoneNumber pn) {
        return COMPARATOR.compare(this, pn);
    }

}

// Exemplo de um programa que recebe palavras e mostra quais são únicas.
// Como esta é uma classe pública, ela deveria ficar em WordList.java.
public class WordList {
    public static void main(String[] args) {
        // TreeSet mantém os elementos em uma ordem definida.
        // As strings são ordenadas pelo comparador natural: a ordem alfabética.
        java.util.Set<String> s = new java.util.TreeSet<String>();
        // addAll recebe todos os elementos de args e os adiciona ao conjunto.
        // Collections é usado aqui para oferecer uma forma prática de inserir
        // vários argumentos de uma vez.
        Collections.addAll(s, args);
        System.out.println(s);
    }
}

// Exemplo simplificado da interface Comparable.
// O tipo genérico T indica com qual tipo de objeto o comparador pode trabalhar.
public interface Comparable<T> {
    int compareTo(T o);
}

// Representa um texto cuja comparação ignora maiúsculas e minúsculas.
// "final" impede que esta classe seja herdada e "private final" torna o texto
// imutável depois que o construtor o recebe.
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
    private final String s;

    // Construtor que rejeita o valor null.
    // A String armazenada não pode ser substituída depois que o objeto é criado.
    public CaseInsensitiveString(String s) {
        if (s == null)
            throw new NullPointerException();
        this.s = s;
    }

    // Implementa a ordem natural da classe.
    // String.CASE_INSENSITIVE_ORDER fornece um comparador que ignora diferenças
    // entre maiúsculas e minúsculas ao comparar os dois textos.
    @Override
    public int compareTo(CaseInsensitiveString cis) {
        return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
    }
}