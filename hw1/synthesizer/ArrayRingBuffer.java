// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
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
        // TODO: Create new array with capacity elements.
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
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
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
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
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
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    public Iterator<T> iterator() {
        return new Iter();

    }
    private class Iter implements Iterator<T> {
        int iterIndex;
        public Iter() {
            iterIndex = first;
        }
        public boolean hasNext() {
            return iterIndex == last;
        }
        public T next() {
            T returnItem = rb[iterIndex];
            iterIndex = incrementfirst(iterIndex);
            return returnItem;
        }

    }



    private int incrementlast(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index += 1;
    }
    private int decreaseLast(int index) {
        if (index == 0) {
            return capacity - 1;
        }
        return index - 1;
    }
    private int incrementfirst(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index += 1;
    }
}
