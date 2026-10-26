import java.util.Objects;

/**
 * Reúne exemplos de diferentes estratégias para calcular hashCode().
 *
 * Os métodos estão nomeados hashCode1, hashCode2 etc. para compará-los,
 * então eles não sobrescrevem Object.hashCode(). Para usar uma estratégia,
 * ela deve ser implementada em um método chamado exatamente hashCode().
 * Além disso, field1, field2 e hashCode precisam ser declarados conforme o
 * tipo da classe que usar os exemplos. Este arquivo é material didático que
 * precisa ser adaptado antes de compilar.
 */
public class OverwritingHashCodeMethod {
    // Estratégia válida, mas muito ruim: todos os objetos recebem o mesmo hash.
    // Isso respeita a regra de que objetos iguais têm o mesmo hash, mas faz
    // coleções como HashMap e HashSet perderem eficiência por causa das colisões.
    public int hashCode1() {
        return 42;
    }

    // Forma tradicional de combinar os campos usados na igualdade do objeto.
    // Começa com o hash do primeiro campo e combina os seguintes usando 31.
    // O valor 31 ajuda a distribuir os resultados; ele não garante ausência
    // de colisões. Atenção: Short.hashCode(field1) exige um short primitivo;
    // se field1 for Short e puder ser null, essa chamada pode causar erro antes
    // da verificação de null nas linhas seguintes. Ajuste conforme o tipo real.
    public int hashCode2() {
        int result = Short.hashCode(field1);
        result = 31 * result + (field1 != null ? field1.hashCode() : 0);
        result = 31 * result + (field2 != null ? field2.hashCode() : 0);
        return result;
    }

    // Versão concisa: Objects.hash calcula um hash a partir dos campos recebidos.
    // Pode ser mais lenta que combinar os campos diretamente, pois usa argumentos
    // variáveis (varargs) e pode criar um array temporário.
    public int hashCode3() {
        return Objects.hash(field1, field2);
    }

    // Calcula o hash somente na primeira chamada e guarda o resultado em cache.
    // Pode ser útil quando calcular o hash é caro e o objeto é imutável.
    // O campo hashCode também precisa ser declarado; o valor zero é usado aqui
    // para indicar que o cálculo ainda não foi feito. Se o hash calculado for
    // zero, ele será recalculado nas chamadas seguintes.
    public int hashCode4() {
        // Primeiro verifica se já existe um resultado armazenado.
        int result = hashCode;
        if (result == 0) {
            // Se ainda não foi calculado, combina os campos como no exemplo acima.
            result = Short.hashCode(field1);
            result = 31 * result + (field1 != null ? field1.hashCode() : 0);
            result = 31 * result + (field2 != null ? field2.hashCode() : 0);
            // Guarda o valor para reutilizá-lo nas próximas chamadas.
            hashCode = result;
        }
        return result;
    }

}
