public class ArrayDeque<T> {
    private int size;
    private int begin, end;
    private T[] items;
    private double radio;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        begin = 0;
        end = 0;
    }

    public void addFirst(T x) {
        size++;
        if (begin == end) {
            items[begin] = x;
        }
        if (((begin - 1 + items.length) % items.length) == end) {
            recap();
        }
        begin = (begin - 1 + items.length) % items.length;
        items[begin] = x;
    }
    public void addLast(T x) {
        size++;
        if (begin == end) {
            items[end] = x;
        }
        if ((end + 1) % items.length == begin) {
            recap();
        }
        end = (end + 1) % items.length;
        items[end] = x;

    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size != 0) {
            int i;
            for (i = begin; i != end; i = (i + 1) % items.length) {
                System.out.print(items[i] + " ");
            }
            System.out.print(items[i] + " ");
        }


    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        T x = items[begin];
        items[begin] = null;
        begin = (begin + 1) % items.length;

        radio = (double) size / items.length;
        if (radio <= 0.25) {
            shrink();
        }

        return x;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        T x = items[end];
        items[end] = null;
        end = (end - 1 + items.length) % items.length;

        radio = (double) size / items.length;
        if (radio <= 0.25) {
            shrink();
        }
        return x;
    }
    public T get(int index) {
        int i = (begin + index) % items.length;
        return items[i];
    }

    private void recap() {
        T[] newItems = (T[]) new Object[items.length * 2];
        if (begin <= end) {
            System.arraycopy(items, 0, newItems, 0, items.length);
            items = newItems;
            return;
        }
        System.arraycopy(items, 0, newItems, 0, end + 1);
        int rightLength = items.length - begin;
        System.arraycopy(items, begin, newItems, newItems.length - rightLength - 1, rightLength);
    }

    private void shrink() {
        T[] newItems = (T[]) new Object[items.length / 2];
        if (begin <= end) {
            System.arraycopy(items, begin, newItems, 0, size);
            items = newItems;
            return;
        }
        System.arraycopy(items, begin, newItems, 0, items.length - begin);
        System.arraycopy(items, end, newItems, items.length - begin, end + 1);
        items = newItems;
    }
}