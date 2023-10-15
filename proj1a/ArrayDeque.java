/**
 *
 * array based double end queue
 * @param <T>
 */
public class ArrayDeque<T> {
    /** the size of the queue*/
    private int size;
    /** the size of the array*/
    private int capacity;
    private int begin, end;
    private T[] items;
    private double radio;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        capacity = 8;
        size = 0;
        begin = 3;
        end = 3;
    }

    /**
     * The two function is used to calculate the front and the last
     * in case they are at boundaries
     */
    private int minusOne(int i) {
        if (i == 0) {
            return capacity - 1;
        }
        return i - 1;
    }
    private int plusOne(int i) {
        if (i == capacity - 1) {
            return 0;
        }
        return i + 1;
    }
    public void addFirst(T x) {
        if (size == capacity) {
            grow();
        }
        begin = minusOne(begin);
        items[begin] = x;
        size++;
    }
    public void addLast(T x) {
        if (size == capacity) {
            grow();
        }
        end = plusOne(end);
        items[end] = x;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toreturn = items[begin];

        items[begin] = null;
        begin = plusOne(begin);
        size--;

        radio = (double) size / capacity;
        if (capacity >= 8 && radio <= 0.25) {
            shrink();
        }
        return toreturn;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toreturn = items[end];

        items[end] = null;
        end = minusOne(end);
        size--;

        radio = (double) size / capacity;
        if (capacity >= 8 && radio <= 0.25) {
            shrink();
        }
        return toreturn;
    }

    /**
     *
     */
    public T get(int i) {
        if (i >= size) {
            return null;
        }
        int p = begin;
        for (int k = 0; k < i; k++) {
            p = plusOne(p);
        }
        return items[p];
    }

    /**
     * Print the deque from begin to end
     */
    public void printDeque() {
        int p = begin;
        while (p != end && items[p] != null) {
            System.out.print(items[p] + " ");
            p = plusOne(p);
        }
        if (items[p] != null) {
            System.out.print(items[p] + " ");
        }
    }

    /**
     * This method change the capacity of array container to double size
     * and copy the items from begin to end to 0 to size - 1
     */
    private void grow() {
        T[] newItems = (T[]) new Object[capacity * 2];
        int p = begin;
        int i;
        for (i = 0; i <= size; i++) {
            newItems[i] = items[p];
            p = plusOne(p);
        }

        begin = 0;
        end = size - 1;
        items = newItems;
        capacity *= 2;
    }

    /**
     * if the usage percent of array is less than 0.25 and the array is larger than 16
     * This method change the capacity of the array to its half
     */
    private void shrink() {
        T[] newItems = (T[]) new Object[capacity / 2];
        int p = begin;
        int i;
        for (i = 0; i <= size; i++) {
            newItems[i] = items[p];
            p = plusOne(p);
        }
        begin = 0;
        end = size - 1;
        items = newItems;
        capacity /= 2;
    }
}