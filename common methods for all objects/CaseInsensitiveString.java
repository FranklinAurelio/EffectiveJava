import java.util.Objects;

/**
 * Representa um texto cuja comparação com outro CaseInsensitiveString
 * ignora diferenças entre letras maiúsculas e minúsculas.
 *
 * Este arquivo também reúne exemplos didáticos de Point, ColorPoint e
 * CounterPoint. Em um projeto Java, cada classe pública deve ficar em seu
 * próprio arquivo, e as instruções de exemplo devem ficar dentro de um método.
 */
public final class CaseInsensitiveString {
    // Guarda o texto recebido. "private" impede acesso direto de outras classes
    // e "final" impede trocar a referência depois que o objeto é criado.
    // String também é imutável: seu conteúdo não pode ser alterado.
    private final String s;

    // Constrói o objeto e verifica que o texto não seja null.
    // Um valor null não representa uma String válida e causaria erros ao comparar.
    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    // Define quando este objeto deve ser considerado igual a outro.
    // O parâmetro é Object porque esta é a assinatura exigida pelo método equals.
    @Override
    public boolean equals(Object o) {
        // Compara o conteúdo sem diferenciar letras maiúsculas de minúsculas.
        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);

        // Este caso permite comparar diretamente com String, mas quebra a
        // simetria do contrato de equals: a comparação pode ser verdadeira
        // daqui para String, mas falsa quando String.equals recebe este objeto.
        if (o instanceof String) // interoperability (breaking symmetry)
            return s.equalsIgnoreCase((String) o);

        // Objetos de outros tipos não são considerados iguais.
        return false;
    }

    // Objetos iguais segundo equals precisam produzir o mesmo hashCode.
    // Este exemplo transforma o texto em minúsculas antes de calcular o hash,
    // mas toLowerCase() e equalsIgnoreCase() não têm exatamente as mesmas
    // regras para todos os caracteres; por isso, esta implementação pode
    // quebrar esse contrato em alguns casos. Uma implementação real deve usar
    // uma normalização que corresponda exatamente à regra de igualdade escolhida.
    @Override
    public int hashCode() {
        return s.toLowerCase().hashCode();
    }
}

// Exemplo de uma classe que representa um ponto em um plano (coordenadas x e
// y).
// Este exemplo deve ficar em Point.java, separado das outras classes públicas.
public class Point {
    // Coordenadas imutáveis: são definidas no construtor e não mudam depois.
    private final int x;
    private final int y;

    // O construtor recebe e guarda as coordenadas do ponto.
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Compara pontos pelas coordenadas, e não pela identidade dos objetos.
    @Override
    public boolean equals(Object o) {
        // Só objetos do tipo Point (ou de uma subclasse) podem ser iguais.
        if (!(o instanceof Point))
            return false;

        // Converte o Object para Point para acessar x e y.
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }

    // Gera um número baseado nas coordenadas.
    // O uso de 31 combina os valores e ajuda a distribuir os pontos em coleções
    // baseadas em hash, como HashSet e HashMap.
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}

// Exemplo de um ponto que também possui uma cor.
// "Color" não está declarado neste arquivo; o exemplo pressupõe um tipo Color,
// por exemplo java.awt.Color. Esta classe usa composição: guarda um Point,
// em vez de herdar de Point.
public class ColorPoint {
    // A cor e o ponto que descrevem este objeto.
    private final Color color;
    private final Point point;

    // Cria o ponto interno e rejeita uma cor null.
    public ColorPoint(int x, int y, Color color) {
        this.point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    // Devolve o ponto associado. Point é imutável neste exemplo,
    // então devolver a referência não permite alterar suas coordenadas.
    public Point asPoint() {
        return point;
    }

    // Exemplo de comparação entre ColorPoint.
    // Atenção: como ColorPoint não herda de Point, super.equals(o) chama
    // Object.equals e compara identidade; assim, objetos distintos não serão
    // iguais mesmo que tenham as mesmas coordenadas e cor. Além disso, ao
    // sobrescrever equals, a classe também deveria sobrescrever hashCode().
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        // "==" compara se color aponta para a mesma instância, não necessariamente
        // se duas cores têm o mesmo valor. Em geral, use color.equals(...).
        return super.equals(o) && ((ColorPoint) o).color == color;
    }
}

// Exemplos de criação e comparação; instruções assim devem ficar dentro
// de um método, como main, e não diretamente no corpo do arquivo Java.
Point p = new Point(1, 2);
ColorPoint cp = new ColorPoint(1, 2, Color.RED);

// Outros objetos usados para demonstrar comparações entre cores.
ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
Point p2 = new Point(1, 2);
ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);

// Exemplo alternativo de equals para Point: comparar a classe exata evita
// considerar um Point e uma subclasse como iguais. Porém, essa abordagem pode
// dificultar a substituição de subclasses por Point (princípio de Liskov).
// Este método deve substituir o equals anterior dentro da classe Point;
// os dois métodos com a mesma assinatura não podem coexistir.
@Override
public boolean equals(Object o) {
    if (o == null || o.getClass() != getClass())
        return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
}

// Conjunto imutável de pontos que representam os quatro pontos cardeais
// sobre o círculo de raio 1. Set.of cria o conjunto com os valores fornecidos.
// Este campo deve ficar dentro de uma classe, não no nível superior do arquivo.
private static final Set<Point> unitCircle = Set.of(new Point(1, 0), new Point(0, 1), new Point(-1, 0),
        new Point(0, -1));

// Verifica se o ponto recebido está no conjunto de pontos do círculo unitário.
// contains usa equals e hashCode de Point para fazer a busca.
public static boolean isOnUnitCircle(Point p) {
    return unitCircle.contains(p);
}

// Subclasse de Point que conta quantas instâncias de CounterPoint foram
// criadas.
// Como estende Point, ela herda as coordenadas e a implementação de hashCode.
public class CounterPoint extends Point {
    // Contador compartilhado por todas as instâncias.
    // AtomicInteger permite incrementar e ler o valor de forma segura
    // quando há acesso simultâneo por várias threads.
    private static final AtomicInteger counter = new AtomicInteger();

    public CounterPoint(int x, int y) {
        // Inicializa as coordenadas da classe pai antes de contar a instância.
        super(x, y);
        counter.incrementAndGet();
    }

    // Devolve o total de instâncias criadas até agora.
    public static int numberOfInstances() {
        return counter.get();
    }

    // Trata a comparação com Point e CounterPoint para preservar a simetria
    // do equals neste exemplo: se o outro objeto não for CounterPoint,
    // delega a comparação para o equals do outro objeto.
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        // Se o outro objeto for um Point, mas não um CounterPoint,
        // então a comparação é feita chamando o equals do outro objeto.
        if (!(o instanceof CounterPoint))
            return o.equals(this);
        return super.equals(o);
    }
}

// Exemplo de uma classe que representa um número de telefone dividido em
// código de área, prefixo e número da linha.
// Em um projeto Java, esta classe pública deve ficar em PhoneNumber.java.
public final class PhoneNumber {
    // Guarda as três partes do telefone.
    // "private" protege os campos contra alterações externas e "final" faz
    // com que sejam definidos somente durante a construção do objeto.
    // short é um tipo inteiro menor que int; os limites abaixo cabem nele.
    private final short areaCode, prefix, lineNum;

    // Recebe as partes como int e valida cada uma antes de guardá-la.
    // Usar int como parâmetro é conveniente para quem chama o construtor;
    // depois da validação, os valores são armazenados como short.
    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "line num");
    }

    // Método auxiliar que verifica se um número está entre zero e o limite
    // permitido. "static" permite chamá-lo sem uma instância de PhoneNumber,
    // pois a validação não depende dos dados de um objeto já criado.
    private static short rangeCheck(int val, int max, String arg) {
        // Se o valor estiver fora do intervalo, a construção não pode continuar.
        // IllegalArgumentException informa que quem chamou forneceu um valor inválido.
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);

        // O cast converte int para short. É seguro aqui porque o valor já foi
        // validado e os limites usados pelo construtor cabem no tipo short.
        return (short) val;
    }

    // Define igualdade pelo conteúdo das três partes do telefone,
    // em vez de verificar se as referências apontam para o mesmo objeto.
    @Override
    public boolean equals(Object o) {
        // Um objeto é sempre igual a si mesmo; esta verificação também evita
        // as comparações seguintes quando as referências são idênticas.
        if (o == this)
            return true;

        // Telefones de outro tipo não podem ser iguais a este.
        if (!(o instanceof PhoneNumber))
            return false;

        // Depois de confirmar o tipo, converte Object para PhoneNumber para
        // poder acessar os campos do outro telefone.
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    // Ao sobrescrever equals, também é necessário sobrescrever hashCode:
    // objetos iguais segundo equals precisam ter o mesmo código hash.
}