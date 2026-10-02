public class Stack {
    private Object[] elements;
    private int size = 0;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            Object[] oldElements = elements;
            elements = new Object[2 * size + 1];
            System.arraycopy(oldElements, 0, elements, 0, size);
        }
    }
}

/*
 * Correcao do vazeamento de memoria: A linha "elements[size] = null;" foi
 * adicionada no metodo pop() para eliminar a referencia obsoleta ao objeto que
 * foi removido da pilha.
 * Isso permite que o coletor de lixo libere a memoria ocupada por esse objeto,
 * evitando vazamentos de memoria.
 */

public class Stack {
    private Object[] elements;
    private int size = 0;
    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            Object[] oldElements = elements;
            elements = new Object[2 * size + 1];
            System.arraycopy(oldElements, 0, elements, 0, size);
        }
    }
}