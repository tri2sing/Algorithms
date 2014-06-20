import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	
	public final Comparator<Point> SLOPE_ORDER = new PointComparator(); 

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		/* YOUR CODE HERE */
		if (this.y == that.y && this.x == that.x) {  // Degenerate line segment
			return Double.NEGATIVE_INFINITY;
		}
		else if(this.y == that.y && this.x != that.x) {  //Horizontal line, return positive 0
			double a = 1.0;
			double x = (a - a) / a;
			return x;
		}
		else if(this.x == that.x && this.y != that.y) {  //vertical line
			return Double.POSITIVE_INFINITY;
		}
		else {
			// As the x and y coordinates are integers, we have to ensure that this case
			// returns an accurate value.
			return (double) (that.y - this.y) / (double) (that.x - this.x);
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		/* YOUR CODE HERE */
		if(this.y < that.y) return -1;
		else if (this.y > that.y) return 1;
		else if (this.y == that.y && this.x < that.x) return -1;
		else if (this.y == that.y && this.x > that.x) return 1;
		else return 0;
		}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	private class PointComparator implements Comparator<Point> {
		public int compare (Point q, Point r) {
			double slopeq, sloper;
			slopeq = slopeTo(q);
			sloper = slopeTo(r);
			// If slopes are equal we need to impose
			// lexical order to further break the ties
			if(slopeq == sloper) return q.compareTo (r);
			else if (slopeq < sloper) return -1;
			else return 1;
		}
	}
	
	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
