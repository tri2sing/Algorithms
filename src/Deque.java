import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   
    private int N; // number of items in Deque
    private Node<Item> first; // start of the Deque
    private Node<Item> last; // end of the Deque
   
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;  // next node in the list
        private Node<Item> prev;  // previous node in the list
       
        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }
   
    public Deque() {
        N = 0;
        first = null;
        last = null;
    }
   
    public boolean isEmpty() {
        return N == 0;
    }
   
    public int size() {
        return N;
    }
   
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
       
        Node<Item> newNode = new Node<Item>(item);
        if (N == 0) {
            first = newNode;
            last = newNode;
        }
        else {
            Node<Item> oldfirst = first;
            first = newNode;
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        N++;
    }
   
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
       
        Node<Item> newNode = new Node<Item>(item);
        if (N == 0) {
           first = newNode;
            last = newNode;
        }
        else {
            Node<Item> oldlast = last;
            last = newNode;
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
    }
   
    public Item removeFirst() {
        if (N == 0) throw new NoSuchElementException();
        Item result = first.item;
        if (N == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        N--;
        return result;
    }
   
    public Item removeLast() {
        if (N == 0) throw new NoSuchElementException();
        Item result = last.item;
        if (N == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        N--;
        return result;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }
   
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;
       
        public DequeIterator(Node<Item> first) {
            current = first;
        }
       
        public boolean hasNext() {
            return current != null; 
        }
       
        public void remove() {
            throw new UnsupportedOperationException();
        }
       
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }       
    }

    public static void main(String[] atgs) {
    }
       
}

