package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    int capacity(); // return the size of the buffer

    int fillCount(); // return the number of items currently in the buffer

    void enqueue(T x); //  add item x to the end

    T dequeue(); //delete and return item from the front

    T peek(); // return (but do not delete) item from the front
    // is the buffer empty(fillCount equals to 0)

    Iterator<T> iterator();
    default boolean isEmpty() {
        return fillCount() == 0;
    }
    // is the buffer full(fillCount equals to capacity)
    default boolean isFull() {
        return capacity() == fillCount();
    }

}
