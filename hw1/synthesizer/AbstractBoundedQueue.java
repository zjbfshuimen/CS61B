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


}
