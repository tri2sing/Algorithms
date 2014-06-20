import java.util.Iterator;

public class PointSET {

	private SET<Point2D> ptSet;

	public PointSET() {
		ptSet = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return ptSet.isEmpty();
	}

	public int size() {
		return ptSet.size();
	}

	public void insert(Point2D p) {
		ptSet.add(p);
	}

	public boolean contains(Point2D p) {
		return ptSet.contains(p);
	}

	public void draw() {
		Iterator<Point2D> ptItr = ptSet.iterator();
		while (ptItr.hasNext()) {
			ptItr.next().draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> ptQ = new Queue<Point2D>();

		Iterator<Point2D> ptItr = ptSet.iterator();
		while (ptItr.hasNext()) {
			Point2D pt = ptItr.next();
			if (rect.contains(pt))
				ptQ.enqueue(pt);
		}

		return ptQ;
	}

	public Point2D nearest(Point2D p) {
		Point2D retpt = null;
		double least = Double.POSITIVE_INFINITY;

		Iterator<Point2D> ptItr = ptSet.iterator();
		while (ptItr.hasNext()) {
			Point2D cur = ptItr.next();
			double curdist = p.distanceSquaredTo(cur);
			if (curdist < least) {
				least = curdist;
				retpt = cur;
			}
		}
		return retpt;
	}

	public static void main(String[] args) {

		String filename = args[0];
		In in = new In(filename);

		PointSET brute = new PointSET();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			brute.insert(p);
		}

		brute.draw();
	}

}
