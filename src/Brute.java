import java.util.Arrays;

public class Brute {

	public static void main(String[] args) {

		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);

		// read in the input
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] pts = new Point[N];
		for (int k = 0; k < N; k++) {
			int x = in.readInt();
			int y = in.readInt();
			pts[k] = new Point(x, y);
			pts[k].draw();
		}

		// Sort the points by their natural order
		// (increasing y, then increasing x for equal y
		Arrays.sort(pts);

		int num = 0;
		int r = 4;
		for (int i = 0; i <= N - r; i++)
			for (int j = i + 1; j <= N - r + 1; j++)
				for (int k = j + 1; k <= N - r + 2; k++)
					for (int l = k + 1; l <= N - r + 3; l++) {
						num++;
						double ptoq = pts[i].slopeTo(pts[j]);
						double ptor = pts[i].slopeTo(pts[k]);
						double ptos = pts[i].slopeTo(pts[l]);
						if ((ptoq == ptor) && (ptoq == ptos)) {
							// points are collinear
							StdOut.println(pts[i].toString() + " -> "
									+ pts[j].toString() + " -> "
									+ pts[k].toString() + " -> "
									+ pts[l].toString());
							pts[i].drawTo(pts[l]);
						}
					}
		// display to screen all at once
		StdDraw.show(0);
	}

}
