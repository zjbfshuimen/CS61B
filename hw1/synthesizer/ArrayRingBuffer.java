// package <package name>;
package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;

    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        //       first, last, and fillCount should all be set to 0.
        first = 0;
        last = 0;
        fillCount = 0;
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = incrementlast(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T returnitem = rb[first];
        rb[first] = null;
        first = incrementfirst(first);
        fillCount -= 1;
        return returnitem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    public Iterator<T> iterator() {
        return new ArrayBufferingIterator();

    }
    private class ArrayBufferingIterator implements Iterator<T> {
        int iterIndex;
        int num;
        public ArrayBufferingIterator() {
            iterIndex = first;
            num = 0;
        }
        public boolean hasNext() {
            return num < fillCount;
        }
        public T next() {
            T returnItem = rb[iterIndex];
            iterIndex = incrementfirst(iterIndex);
            num++;
            return returnItem;
        }
    }

    private int incrementlast(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        int returnIndex = index + 1;
        return returnIndex;
    }
    private int decreaseLast(int index) {
        if (index == 0) {
            return capacity - 1;
        }
        int returnIndex = index - 1;
        return returnIndex;
    }
    private int incrementfirst(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        int returnIndex = index + 1;
        return returnIndex;
    }
}
