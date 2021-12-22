package tdd.collection.lifo;

import java.util.LinkedList;
import java.util.List;

public class Stack implements SumpStack {

    private Entry<?>[] sump;

    private Class<?> classType;

    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    private static final double DEFAULT_STRETCH_RATIO = 0.7;

    private int offset;

    public Stack(Class<?> clazz, int capacity) {
        this(capacity);
        this.classType = clazz;
    }

    public Stack(Class<?> clazz) {
        this(clazz, DEFAULT_INITIAL_CAPACITY);
    }

    public Stack() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public Stack(int capacity) {
        this.sump = new Entry<?>[capacity];
    }

    public static class Entry<V> {

        private V value;

        private final int index;

        public Entry(V value, int index) {
            this.value = value;
            this.index = index;
        }

        public void nullify() {
            this.value = null;
        }

        public V value() {
            return this.value;
        }

        public int index() {
            return this.index;
        }

        public Entry<V> get() {
            return new Entry<>(value, index);
        }
    }

    @Override
    public void push(Object entry) {
        if (isTyped() && !entry.getClass().equals(classType)) {
            throw new RuntimeException("Unable to push type " + entry.getClass().getSimpleName() + " to stack typed by " + classType.getSimpleName());
        }

        if (size() == capacity()) {
            int stretchOn = (int) (capacity() * DEFAULT_STRETCH_RATIO);
            Entry<?>[] newArray = new Entry<?>[capacity() + stretchOn];

            System.arraycopy(sump, 0, newArray, 0, capacity());

            sump = newArray;
        }
        sump[offset] = new Entry<>(entry, offset++);
    }

    @Override
    public Object pull() {
       if (offset > 0) {
           Entry<?> original = sump[--offset];
           Entry<?> pulled = original.get();
           original.nullify();

           return pulled.value();
       }

       return null;
    }

    @Override
    public int size() {
        return offset;
    }

    public int capacity() {
        return this.sump.length;
    }

    @Override
    public void clear() {
        if (!sumpNullOrEmpty()) {
            this.sump = new Entry<?>[0];
        }
    }

    @Override
    public void pushBatch(List<Object> batch) {

    }

    @Override
    public List<Object> pullBatch() {
        List<Object> batch = new LinkedList<>();
        return batch;
    }

    public boolean isTyped() {
        return this.classType != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private boolean sumpNullOrEmpty() {
        return size() != 0;
    }

    public void trim() {
        if (capacity() > size()) {
            int trimTo = size();
            Entry<?>[] newArray = new Entry<?>[trimTo];

            System.arraycopy(sump, 0, newArray, 0, trimTo);
            sump = newArray;
        }
    }
}
