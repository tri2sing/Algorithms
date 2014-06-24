
public class Percolation {

	private int N;
	private final int vTop = 0; // Virtual top cell
	private int vBot; // Virtual bottom cell
	private boolean[] open;
	private boolean[] full;
	private WeightedQuickUnionUF fullQUF;

	private void testRange(int index) {
		if (index < 1 || index > this.N) {
			throw new IndexOutOfBoundsException(index + " value is out of bounds");
		}
	}

	private int mapXY(int x, int y) {
		return ((x - 1) * this.N + y);
	}
	
	private boolean isFullBottomRowCell() {

        int nSqrd = this.N*this.N;
        for (int i = nSqrd; i > nSqrd - this.N; i--) {
        	if (full[i]) return true;
        }

		return false;
	}
	
	private void markFull(int i, int j) {
		int index;
		boolean left = true;
		boolean right = true;
		boolean above = true;
		boolean below = true;

		testRange(i);
		testRange(j);
		
		index = mapXY(i, j);
		full[index] = true;
		//StdOut.printf("Marking (%d, %d),  %d\n", i, j, index);
		if (i == 1) {
			above = false;
		}
		if (i == this.N) {
			below = false;
		}
		if (j == 1) {
			left = false;
		}
		if (j == this.N) {
			right = false;
		}

		// We do not test above because a cell cannot become full
		// unless the cell above it is full.
		
		if (above && open[mapXY(i - 1, j)] && !full[mapXY(i - 1, j)]) {
			markFull(i - 1, j);
		}
		if (below && open[mapXY(i + 1, j)] && !full[mapXY(i + 1, j)]) {
			markFull(i + 1, j);
		}
		if (left && open[mapXY(i, j - 1)] && !full[mapXY(i, j - 1)]) {
			markFull(i, j - 1);
		}
		if (right && open[mapXY(i, j+1)] && !full[mapXY(i, j + 1)]) {
			markFull(i, j + 1);
		}
		
	}

	public Percolation(int N) {

		if (N < 1) {
			throw new IllegalArgumentException("Constructor argument less than zero");
		}
		
		this.N = N;
		this.vBot = N * N + 1;
		open = new boolean[N * N + 2];
		full = new boolean[N * N + 2];
		for (int i = 1; i < vBot; i++) {
			open[i] = false;
			full[i] = false;
		}
		open[vTop] = true;
		open[vBot] = true;
		
		fullQUF = new WeightedQuickUnionUF(N * N + 2);
	}

	public void open(int i, int j) {
		int index;
		boolean left = true;
		boolean right = true;
		boolean above = true;
		boolean below = true;

		testRange(i);
		testRange(j);
		index = mapXY(i, j);
		open[index] = true; // Label the cell as open

		if (i == 1) { // Top Row
			fullQUF.union(vTop, index); // Merge with virtual top cell
			above = false; // Don't want to repeat union
		}
		if (i == this.N) { // Bottom Row
			below = false;
		}
		if (j == 1) {
			left = false;
		}
		if (j == this.N) {
			right = false;
		}

		if (above && open[mapXY(i - 1, j)]) {
			fullQUF.union(index, mapXY(i - 1, j));
		}
		if (below && open[mapXY(i + 1, j)]) {
			fullQUF.union(index, mapXY(i + 1, j));
		}
		if (left && open[mapXY(i, j - 1)]) {
			fullQUF.union(index, mapXY(i, j - 1));
		}
		if (right && open[mapXY(i, j + 1)]) {
			fullQUF.union(index, mapXY(i, j + 1));
		}
		
		
		if (fullQUF.connected(vTop, index)) {
			markFull(i, j);
		}
	}

	/*
	 * Each site is either open or blocked.
	 */
	public boolean isOpen(int i, int j) {
		testRange(i);
		testRange(j);
		return (open[mapXY(i, j)]);
	}

	/*
	 * A full site is an open site that can be connected to an open site in the
	 * top row via a chain of neighboring (left, right, up, down) open sites.
	 */
	public boolean isFull(int i, int j) {
		testRange(i);
		testRange(j);
		return (fullQUF.connected(vTop, mapXY(i, j)));
	}

	/*
	 * We say the system percolates if there is a full site in the bottom row.
	 * In other words, a system percolates if we fill all open sites connected
	 * to the top row and that process fills some open site on the bottom row.
	 */
	public boolean percolates() {
		return isFullBottomRowCell();
	}
}
