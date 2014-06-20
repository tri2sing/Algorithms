public class KdTree {
	private Node root; // The root of the tree

	private static class Node {
		private Point2D p; // the point at this node.
		private RectHV r; // axis-aligned rectangle for this node.
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
		private int N; // number of nodes in the subtree
		private int level; // depth from root

		public Node(Point2D p, int level) {
			this.p = p;
			r = null;
			lb = null;
			rt = null;
			N = 1;
			this.level = 0;
		}
	}

	public KdTree() { // Construct an empty tree of points
		root = null;
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	public int size() {
		return size(root);
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	// Add the point p to the tree (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null)
			return;
		root = put(root, p, 0);
	}

	private Node put(Node n, Point2D p, int level) {
		if (n == null)
			return new Node(p, level);

		double px = p.x();
		double nx = n.p.x();
		double py = p.y();
		double ny = n.p.y();

		// If the point to insert is already
		// in the set don't do anything
		if ((px == nx) && (py == ny)) {
			//StdOut.println("Point already exists " + p.toString());
			return n;
		}

		level = n.level + 1;
		if (n.level % 2 == 0) { // Use x coordinate
			if (px < nx)
				n.lb = put(n.lb, p, level);
			else
				n.rt = put(n.rt, p, level);
		} else { // Use y coordinate
			if (py < ny)
				n.lb = put(n.lb, p, level);
			else
				n.rt = put(n.rt, p, level);
		}
		n.N = 1 + size(n.lb) + size(n.rt);

		return n;
	}

	public boolean contains(Point2D p) {
		return contains(root, p);
	}

	private boolean contains(Node n, Point2D p) {
		if (n == null)
			return false;

		double px = p.x();
		double nx = n.p.x();
		double py = p.y();
		double ny = n.p.y();

		if ((px == nx) && (py == ny))
			return true;

		if (n.level % 2 == 0) { // Use x coordinate
			if (px < nx)
				return contains(n.lb, p);
			else
				return contains(n.rt, p);
		} else { // Use y coordinate
			if (py < ny)
				return contains(n.lb, p);
			else
				return contains(n.rt, p);
		}
	}

	public static void main(String[] args) {

		String filename = args[0];
		In in = new In(filename);

		KdTree kt = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kt.insert(p);
			StdOut.println("Size of tree = " + kt.size());
		}

		filename = args[0];
		in = new In(filename);

		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			StdOut.println ("Tree contains " + p.toString() + " = " + kt.contains(p));
		}

		Point2D p = new Point2D(0.4, 0.3);
		StdOut.println ("Tree contains " + p.toString() + " = " + kt.contains(p));
		kt.insert(p);
		StdOut.println("Size of tree = " + kt.size());
		kt.insert(p);
		StdOut.println("Size of tree = " + kt.size());
	}

}
