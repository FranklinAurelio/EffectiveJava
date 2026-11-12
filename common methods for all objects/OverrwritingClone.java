/**
 * Reúne exemplos didáticos de cópia de objetos usando clone().
 *
 * Os exemplos foram colocados juntos, mas em um projeto Java cada classe
 * pública deve ficar em seu próprio arquivo. Também faltam algumas declarações
 * e imports para que este material compile de forma independente.
 */
public class OverrwritingClone {
    // Exemplo de implementação de clone. O tipo de retorno PhoneNumber e a
    // declaração desta classe precisam ser ajustados para que o exemplo compile.
    // A classe concreta que executa super.clone() deve implementar Cloneable;
    // essa interface sinaliza que a cópia com Object.clone() é permitida.
    // Sem Cloneable, super.clone() lança CloneNotSupportedException.
    @Override
    public PhoneNumber clone() {
        try {
            // Object.clone cria uma cópia superficial: copia os campos,
            // mas não duplica automaticamente os objetos referenciados.
            return (PhoneNumber) super.clone();
        } catch (CloneNotSupportedException e) {
            // Se Cloneable estiver corretamente implementado, esta exceção
            // não deve ocorrer; AssertionError indica uma falha inesperada.
            throw new AssertionError(e);
        }
    }
}

// Exemplo de uma pilha simples que armazena elementos em um array.
// Uma pilha segue a regra LIFO: o último elemento adicionado é o primeiro removido.
// Para que clone() funcione, Stack também precisa implementar Cloneable.
public class Stack {
    // Array que guarda os elementos; pode ter espaços livres no final.
    private Object[] elements;
    // Quantos elementos válidos existem no array.
    private int size = 0;
    // Capacidade inicial usada ao criar uma pilha vazia.
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // Cria uma cópia da pilha. Como o array é mutável, uma cópia superficial
    // faria as duas pilhas compartilharem o mesmo array. Por isso, o array
    // também é copiado.
    @Override
    public Stack clone() {
        try {
            // Copia os campos primitivos e as referências do objeto original.
            Stack result = (Stack) super.clone();
            // Cria um novo array para que alterações estruturais numa pilha
            // não alterem o array da outra.
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    // Cria uma pilha vazia com a capacidade inicial padrão.
    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    // Adiciona um elemento ao topo da pilha.
    public void push(Object e) {
        // Aumenta o array caso não haja espaço livre.
        ensureCapacity();
        // Armazena o elemento na próxima posição livre e depois avança size.
        elements[size++] = e;
    }

    // Remove e devolve o elemento do topo.
    public Object pop() {
        // Não é possível remover um item de uma pilha vazia.
        if (size == 0)
            throw new EmptyStackException();
        // Diminui size e usa a posição que agora é o índice do último elemento.
        Object result = elements[--size];
        // Apaga a referência que já não pertence à pilha. Isso permite que
        // o Garbage Collector libere o objeto, caso não existam outras referências.
        elements[size] = null;
        return result;
    }

    /**
     * Garante espaço para pelo menos mais um elemento.
     * Quando o array está cheio, cria outro com capacidade maior.
     */
    private void ensureCapacity() {
        if (elements.length == size)
            // Arrays.copyOf cria um array novo e copia os elementos existentes.
            // A capacidade cresce aproximadamente para o dobro mais um espaço.
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}

// Exemplo de tabela hash simplificada. Cada posição do array é um "bucket";
// entradas com o mesmo índice ficam ligadas umas às outras.
public class HashTable implements Cloneable {
    // Array que guarda o início das listas de entradas.
    private Entry[] buckets;
    // Quantidade de pares chave-valor armazenados (não usado neste trecho).
    private int size;

    // Cria uma tabela independente, copiando também as listas de entradas.
    @Override
    public HashTable clone() {
        try {
            // Faz uma cópia superficial dos campos.
            HashTable result = (HashTable) super.clone();
            // Cria um novo array para os buckets, sem compartilhar o array original.
            result.buckets = new Entry[buckets.length];
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null)
                    // Copia a lista encadeada daquele bucket para não compartilhar
                    // os objetos Entry entre a tabela original e a cópia.
                    result.buckets[i] = buckets[i].clone();
            }
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    // Uma entrada da tabela: guarda uma chave, um valor e a próxima entrada
    // da lista que resolve colisões de hash.
    private static class Entry implements Cloneable {
        // A chave não pode ser substituída após a criação da entrada.
        final Object key;
        // O valor pode ser atualizado.
        Object value;
        // Referência para a próxima entrada do mesmo bucket; null indica o fim.
        Entry next;

        Entry(Object k, Object v, Entry n) {
            key = k;
            value = v;
            next = n;
        }

        // Cria uma cópia da entrada e de todas as entradas seguintes.
        // As chaves e os valores são copiados como referências: esta é uma
        // cópia profunda da estrutura de Entries, mas não dos próprios objetos
        // usados como chave ou valor.
        @Override
        protected Entry clone() {
            Entry result = new Entry(key, value, next);
            for (Entry p = result; p.next != null; p = p.next)
                p.next = new Entry(p.next.key, p.next.value, p.next.next);
            return result;
        }
    }
}
