


public class Percolation {
   
    private static final int BLOCKED = 0;
    private static final int OPEN = 1;
    private static final int VALID = -9;   
    private static final int INVALID = -1;

    private int N;
    private int[][] grid;
    private WeightedQuickUnionUF fullQUF;
   
    private void testRange(int index) {
        if(index < 1 || index > this.N)
            throw new IndexOutOfBoundsException(index + "value is out of bounds");
    }
   
    private int xyTo1D(int x, int y) {
        return((x-1)*this.N + y);
    }
   
    public Percolation(int N) {
        int i, j;
       
        this.N = N;
        grid = new int [N+1][N+1];
        for(i = 1; i < N; i++){
            for(j = 1; j < N-1; j++){
                grid[i][j] = BLOCKED;
            }
        }
        fullQUF = new WeightedQuickUnionUF(N*N + 1); 
    }
   
    public void open(int i, int j) {
        int oneD;
        int left = VALID;
        int right = VALID;
        int above = VALID;
        int below = VALID;
       
        testRange(i);
        testRange(j);
        grid[i][j] = OPEN;  // Label the cell as open
        oneD = xyTo1D(i, j);
       
        if(i == 1) {  // Top Row
            fullQUF.union(0, oneD);  //merge given cell with virtual top cell
            above = INVALID;
        }
        if(i == this.N) { // Bottom Row
            below = INVALID;
        }
        if(j == 1) {
            left = INVALID;
        }
        if(j == this.N) {
            right = INVALID;
        }
      
        if(above == VALID && grid[i-1][j] == OPEN) {
            fullQUF.union(oneD, xyTo1D(i-1, j));
        }
        if(below == VALID && grid[i+1][j] == OPEN) {
            fullQUF.union(oneD, xyTo1D(i+1, j));
        }
        if(left == VALID && grid[i][j-1] == OPEN) {
            fullQUF.union(oneD, xyTo1D(i, j-1));
        }
        if(right == VALID && grid[i][j+1] == OPEN) {
            fullQUF.union(oneD, xyTo1D(i, j+1));
        }
    }

    public boolean isOpen(int i, int j) {
        testRange(i);
        testRange(j);
        return(grid[i][j] == OPEN);
    }
   
    public boolean isFull(int i, int j) {
        testRange(i);
        testRange(j);
        return(fullQUF.connected(0, xyTo1D(i, j)));
    }
   
    public boolean percolates() {
        int nSqrd;
        nSqrd = this.N*this.N;
        for (int i = nSqrd; i > nSqrd - this.N; i--) {
            if (fullQUF.connected(0, i)) return true;
        }
        return false;
    }
}

