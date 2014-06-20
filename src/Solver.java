public class Solver {

	private boolean solvable;
	private Node solution;
	private MinPQ<Node> origPQ;
	private MinPQ<Node> twinPQ;

	public Solver(Board initial) {
		boolean twinSolvable = false;
		boolean origSolvable = false;
		Node origCurrent = null;
		Node twinCurrent = null;
		
		origPQ = new MinPQ<Node>();
		twinPQ = new MinPQ<Node>();

		origPQ.insert(new Node(initial, 0, null));
		twinPQ.insert(new Node(initial.twin(), 0, null));

		solvable = false;
		solution = null;
		while (!origSolvable && !twinSolvable) {
			origCurrent = origPQ.delMin();
			if (origCurrent.brd.isGoal()) {
				origSolvable = true;
			} else {
				Iterable<Board> neighbors = origCurrent.brd.neighbors();
				for (Board neighbor : neighbors) {
					if (origCurrent.prev == null) {
						origPQ.insert(new Node(neighbor, origCurrent.mvs + 1, origCurrent));
					} else {
						if (!neighbor.equals((Object) origCurrent.prev.brd)) { // Optimization
							origPQ.insert(new Node(neighbor, origCurrent.mvs + 1, origCurrent));
						}

					}

				}
			}
			twinCurrent = twinPQ.delMin();
			if (twinCurrent.brd.isGoal()) {
				twinSolvable = true;
			} else {
				Iterable<Board> neighbors = twinCurrent.brd.neighbors();
				for (Board neighbor : neighbors) {
					if(twinCurrent.prev == null) {
						twinPQ.insert(new Node(neighbor, twinCurrent.mvs+1, twinCurrent));
					} else {
						if (!neighbor.equals((Object) twinCurrent.prev.brd)) {
							twinPQ.insert(new Node(neighbor, twinCurrent.mvs+1, twinCurrent));
						}
					}
				}
			}
		}
		if (origSolvable) {
			solvable = true;
			solution = origCurrent;
		}
	}
	
	public boolean isSolvable() {
		return solvable;
	}
	
	public int moves() {
		if (solvable) return solution.mvs;
		else return -1;
	}
	
	public Iterable<Board> solution() {
		Stack<Board> boards = new Stack<Board>();
		
		if (!solvable) return null;
		else {
			Node current = solution;
			while (current != null) {
				boards.push(current.brd);
				current = current.prev;
			}
			return boards;
		}
		
	}

	private class Node implements Comparable<Node> {

		private Board brd;
		private Node prev;
		private int mvs;

		public Node(Board board, int moves, Node previous) {
			brd = board;
			mvs = moves;
			prev = previous;
		}

		public int compareTo(Node that) {
			int thisPriority = this.brd.manhattan() + this.mvs;
			int thatPriority = that.brd.manhattan() + that.mvs;
			if (thisPriority < thatPriority)
				return -1;
			else if (thisPriority > thatPriority)
				return 1;
			else
				return 0;
		}
	}

	public static void main(String[] args) {
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}

}
