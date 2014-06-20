public class Subset {

	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		// StdOut.println(k);
		
		while (!StdIn.isEmpty()) {
            String itemin = StdIn.readString();
            rq.enqueue(itemin);		
        }
		
		for (int i = 0; i < k; i++) {
			String itemout = rq.dequeue();
			StdOut.println(itemout);
		}
	}

}
