public class Board {
	private final int N;
	private int i_0, j_0; // track the location of the blank cell
	private final int[][] blocks;

	public Board(int[][] blocks) { // board from an N-by-N array of blocks
		this.N = blocks.length; // As block is 2D square
		this.blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				this.blocks[i][j] = blocks[i][j];
				// Track the blank slot in the board
				if (blocks[i][j] == 0) {
					i_0 = i;
					j_0 = j;
				}
			}
	}

	public int dimension() { // board dimension N
		return N;
	}

	public int hamming() { // number of blocks out of place
		int outOfPlace = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;
				if ((blocks[i][j] - 1) != (i * N + j))
					outOfPlace++;
			}

		return outOfPlace;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int distance = 0;
		int value = -1;
		int goal_i = -1;
		int goal_j = -1;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				value = blocks[i][j];
				if (value == 0)
					continue;
				goal_i = (value - 1) / N; // integer division
				goal_j = (value - 1) % N; // integer remainder
				if (goal_i == i && goal_j == j)
					continue;
				else
					distance += Math.abs(goal_i - i) + Math.abs(goal_j - j);
			}

		return distance;
	}

	public boolean isGoal() {
		return (hamming() == 0);
	}

	public Board twin() {
		int[][] pieces = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				pieces[i][j] = blocks[i][j];

		// Scan the rows to find the first one without zero in it
		for (int i = 0; i < N; i++) {
			if (i != i_0) { // this row does not have the blank
				// swap first two pieces in the row to get twin
				int temp = pieces[i][0];
				pieces[i][0] = pieces[i][1];
				pieces[i][1] = temp;
				break;
			}
		}

		return new Board(pieces);
	}

	public boolean equals(Object y) {

		if (y == null)
			return false;
		if (y == this)
			return true;
		if (y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if (this.N != that.N)
			return false;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] != that.blocks[i][j])
					return false;
			}
		}

		return true;
	}

	public Iterable<Board> neighbors() { // all neighboring boards
		Queue<Board> qb = new Queue<Board>();

		// At the most there are four neighbors (N, S, E, W)
		// that can move into the blank slot [i_0][j_0].

		if ((i_0 - 1) >= 0) { // There is a valid North neighbor
			blocks[i_0][j_0] = blocks[i_0 - 1][j_0]; // move neighbor into blank
			blocks[i_0 - 1][j_0] = 0; // Set N neighbor to empty
			qb.enqueue(new Board(blocks));
			blocks[i_0 - 1][j_0] = blocks[i_0][j_0]; // reset
			blocks[i_0][j_0] = 0;
		}
		if ((i_0 + 1) < N) { // There is a valid South neighbor
			blocks[i_0][j_0] = blocks[i_0 + 1][j_0]; // move neighbor into blank
			blocks[i_0 + 1][j_0] = 0; // Set neighbor to empty
			qb.enqueue(new Board(blocks));
			blocks[i_0 + 1][j_0] = blocks[i_0][j_0]; // reset
			blocks[i_0][j_0] = 0;
		}
		if ((j_0 - 1) >= 0) { // There is a valid East neighbor
			blocks[i_0][j_0] = blocks[i_0][j_0 - 1]; // move neighbor into blank
			blocks[i_0][j_0 - 1] = 0; // Set neighbor to empty
			qb.enqueue(new Board(blocks));
			blocks[i_0][j_0 - 1] = blocks[i_0][j_0]; // reset
			blocks[i_0][j_0] = 0;
		}
		if ((j_0 + 1) < N) { // There is a valid West neighbor
			blocks[i_0][j_0] = blocks[i_0][j_0 + 1]; // move neighbor into blank
			blocks[i_0][j_0 + 1] = 0; // Set neighbor to empty
			qb.enqueue(new Board(blocks));
			blocks[i_0][j_0 + 1] = blocks[i_0][j_0]; // reset
			blocks[i_0][j_0] = 0;
		}

		return qb;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		// create start board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board start = new Board(blocks);
		Board duplicate = new Board(blocks);
		StdOut.println(start.toString());
		StdOut.println("Hamming = " + start.hamming());
		StdOut.println("Manhattan = " + start.manhattan());
		StdOut.println("Is goal = " + start.isGoal());
		Board twin = start.twin();
		StdOut.println("The twin is");
		StdOut.print(twin.toString());
		StdOut.println("Intial == Twin is " + start.equals((Object) twin));
		StdOut.println("Intial == Duplicate is "
				+ start.equals((Object) duplicate));
		for (Board neighbor : start.neighbors()) {
			StdOut.println("The neighbor is");
			StdOut.print(neighbor.toString());
		}
	}
}
