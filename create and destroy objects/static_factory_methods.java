
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}


/*
Exemplos de métodos de fábrica estáticos (static factory methods) em Java:

from - Um método de conversão de tipo que apresenta um unico parâmetro de entrada e retorna uma instancia do tipo desejado.

of - Um método de agregação que aceita múltiplos parâmetros de entrada e retorna uma instancia do tipo desejado.

valueOf - Uma alternativa verbosa para from e para of.

instance ou getInstance - Retorna uma instancia que é descrita pelos parametros se houver, mas não pode ter os mesmos valores.

create ou newInstance - Analogo ao instance ou getInstance, embora nesse caso o metodo garante que cada chamada retorne uma instancia nova.

getType - Igual ao getInstance, porem é usado se o metodo de fabricação for de uma classe diferente. Type é o tipo de retorno do método de fábrica.

newType - Igual ao newIstance, porém é usado se o método de fabricação for de uma classe diferente. Type é o tipo de retorno do método de fábrica.

type - Uma alternativa para getType e newType.
*/

Date date = Date.from(instant); // from
Set<Rank> rankSet = EnumSet.of(ACE, KING); // of
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE); // valueOf
StackWalker luke = StackWalker.getInstance(options); // getInstance
Object newArray = Array.newInstance(classObject, arrayLength); // newInstance
FileStore fs = Files.getFileStore(path); // getType
BufferedReader br = Files.newBufferedReader(path); // newType
List<Complaint> litany = Collections.list(legacyLitany); // type
