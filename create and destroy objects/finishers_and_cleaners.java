//Uma classe autocloseable usando um cleaner como rede de segurança

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    // State of the Room
    static class State implements Runnable {
        int numJunkPiles; // Number of junk piles in this room

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    private final State state;

    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() {
        cleanable.clean();
    }
}

public class Adult {
    public static void main(String[] args) {
        try (Room room = new Room(7)) {
            System.out.println("Goodbye");
        }
    }
}

public class BadRoomUser {
    public static void main(String[] args) {
        Room room = new Room(99);
        System.out.println("Goodbye");
    }
}