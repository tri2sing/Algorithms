import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
     private Item[] a; // array of items
     private int N; // number of elements on stack

     public RandomizedQueue() {
          a = (Item[]) new Object[2];
          N = 0;
     }

     public boolean isEmpty() {
          return N == 0;
     }

     public int size() {
          return N;
     }

     // resize the underlying array holding the elements
     private void resize(int capacity) {
          assert capacity >= N;
          Item[] temp = (Item[]) new Object[capacity];
          for (int i = 0; i < N; i++) {
               temp[i] = a[i];
          }
          a = temp;
     }

     public void enqueue(Item item) {
          if (item == null)
               throw new NullPointerException();
          if (N == a.length)
               resize(2 * a.length);
          a[N++] = item;
     }

     public Item dequeue() {
          if (N == 0)
               throw new NoSuchElementException();
          int i = StdRandom.uniform(N); // Return a number from 0 to N - 1
          Item result = a[i];
          if (i != N - 1)
               a[i] = a[N - 1]; // If chosen element is not the last one
          a[N - 1] = null;
          N--;
          if (N > 0 && N == a.length / 4)
               resize(a.length / 2);
          return result;
     }

     public Item sample() {
          if (N == 0)
               throw new NoSuchElementException();
          int i = StdRandom.uniform(N);
          return a[i];
     }

     public Iterator<Item> iterator() {
          return new RandomizedQueueIterator();
     }

     private class RandomizedQueueIterator implements Iterator<Item> {
          private int current;
          private Item [] b;

          public RandomizedQueueIterator() {
               current = 0;
               b = (Item[]) new Object[N];
               for (int i = 0; i < N; i++) b[i]= a[i];
               StdRandom.shuffle(b);
          }

          public boolean hasNext() {
               return current < N;
          }

          public void remove() {
               throw new UnsupportedOperationException();
          }

          public Item next() {
               if (!hasNext())
                    throw new NoSuchElementException();
               return b[current++];
          }
     }

     public static void main(String[] args) {
          StdOut.println("Testing RandomizedQueue");

          RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
         
          StdOut.println("Size of queue = " + rq.size());
          StdOut.println("Inserting: ");
          for (int i = 0; i < 10; i++) {
               StdOut.print(String.valueOf(i) + ", ");
               rq.enqueue(Integer.valueOf(i));
          }
          StdOut.println();

          StdOut.println("Size of queue = " + rq.size());
          StdOut.println("Iterating: ");
          for (Integer I : rq) {
               StdOut.print(I + ", ");
          }
          StdOut.println();

          StdOut.println("Size of queue = " + rq.size());
          StdOut.println("Iterating: ");
          for (Integer K : rq) {
               StdOut.print(K + ", ");
          }
          StdOut.println();

          StdOut.println("Size of queue = " + rq.size());
          StdOut.println("Inserting: ");
          for (int i = 20; i < 30; i++) {
               StdOut.print(String.valueOf(i) + ", ");
               rq.enqueue(Integer.valueOf(i));
          }
          StdOut.println();

          StdOut.println("Size of queue = " + rq.size());
          StdOut.println("Extrating: ");
          for (int i = 0; i < 20; i++) {
               StdOut.print(rq.dequeue() + ", ");
          }
          StdOut.println();
          StdOut.println("Size of queue = " + rq.size());
     }
}

