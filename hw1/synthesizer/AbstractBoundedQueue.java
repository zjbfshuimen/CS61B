package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    // the number of the elements in BoundedQueue
    protected int fillCount;
    // the capacity of the BoundedQueue
    protected int capacity;

    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
    @Override
    public boolean isEmpty(){
        return fillCount == 0;
    }

    @Override
    public boolean isFull() {
        return capacity == fillCount;
    }
    // return the front item but not remove it
    public abstract T peek();
    // remove the front item and return that item
    public abstract T dequeue();
    // add item to the last of the BoundedQueue
    public abstract void enqueue(T x);

}
