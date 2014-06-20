import java.util.Arrays;

public class Fast {

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

		Point[] srtd = new Point[N];
		double[] slope = new double[N];

		// Repeat for each point in the input
		for (int k = 0; k < N; k++) {
			// Make a copy every time because we need to ensure
			// that we slope sort the array for each point
			for (int j = 0; j < N; j++) {
				srtd[j] = pts[j];
			}
			// Sort the array w.r.t to our current reference point
			Arrays.sort(srtd, srtd[k].SLOPE_ORDER);
			// Find the slope of all other points w.r.t srtd[0]
			for (int j = 1; j < N; j++) {
				slope[j] = srtd[0].slopeTo(srtd[j]);
			}
			// Track points with the same slope w.r.t. srtd[0]
			// Take advantage of the sorting we just did
			for (int j = 1; j < (N - 1); j++) {
				int num = 1;
				while ((j < (N - 1) && (slope[j] == slope[j + 1]))) {
					// We have to test for j first otherwise we may
					// have out of bound array access
					j++; // We advance ahead of the for loop
					num++;
				}
				// If three or more points have the same slope
				// w.r.t the srtd[0] point we have a desired segment
				if (num >= 3) {
					Point[] output = new Point[num + 1];
					int p = 0;
					for (int r = (j - num + 1); r <= j; r++) {
						output[p++] = srtd[r];
					}
					output[p] = srtd[0];
					Arrays.sort(output);
					// To prevent output of permutations we only print a segment
					// only when we are looking at it from its starting point
					if (output[0].compareTo(srtd[0]) == 0) {
						StdOut.print(output[0]);
						for (p = 1; p < (num + 1); p++) {
							StdOut.print(" -> ");
							StdOut.print(output[p]);
						}
						StdOut.println();
						output[0].drawTo(output[num]);
					}

				}
			}

		}
		// display to screen all at once
		StdDraw.show(0);
	}

}
