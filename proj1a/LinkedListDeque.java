public class LinkedListDeque<Item> {
    public class Node{
        Item item;
        Node next;
        Node prev;
        public Node(Item x) {
           item = x;
           prev = null;
           next = null;
        }
        public Node(Item x, Node p, Node r) {
            item = x;
            prev = p;
            next = r;
        }
        public Item getRecursiveHelper(int i){
            if(i == 0) {
                return this.item;
            }
            return this.next.getRecursiveHelper(i - 1);
        }
    }
    private Node front;
    private int size;


    /**
     * default constructor
     */
    public LinkedListDeque(){
        front = new Node(null);
        front.next = front;
        front.prev = front;
        size = 0;
    }

    public LinkedListDeque(Item x) {
        Node N = new Node(x);
        front.next = N;
        front.prev = front.next;
        N.prev = front;
        N.next = front;
        size = 1;
    }
    public void addFirst(Item x) {
        Node nexttoF =front.next;
        Node N = new Node(x, front, front.next);
        front.next = N;
        nexttoF.prev = N;
        size += 1;
    }

    public void addLast(Item x) {
        Node prevF = front.prev;
        Node N = new Node(x, front.prev , front);
        front.prev = N;
        prevF.next = N;
        size += 1;
    }
    /** Return true if the List is empty*/
    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = front.next;
        while (p != front ) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public Item removeFirst() {
        if(size == 0) {
            return null;
        }
        Node toRemove = front.next;
        front.next = toRemove.next;
        toRemove.next.prev = front;
        size -= 1;
        return toRemove.item;
    }

    public Item removeLast() {
        if(size == 0){
            return null;
        }
        Node toRemove = front.prev;
        front.prev = toRemove.prev;
        toRemove.prev.next = front;
        size -= 1;
        return toRemove.item;
    }

    public Item get(int i) {
        if(i >= size) {
            return null;
        }
        Node p = front.next;
        for(int k = 0; k < i; k++) {
            p = p.next;
        }
        return p.item;
    }
    public Item getRecursive(int index) {
        if(index >= size) {
            return null;
        }
        return front.next.getRecursiveHelper(index);
    }




}
