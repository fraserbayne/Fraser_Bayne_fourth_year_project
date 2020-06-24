package model.Generators.GraphTheory.Graph;

/**
 * Represents a single directed edge between 2 Node objects
 */
public class Edge {
	/**
	 * The Node that this Edge starts from
	 */
	private Node from;

	/**
	 * The Node that this Edge points to
	 */
	private Node to;
	private boolean visited;

	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
		visited = false;
	}

	@Override
	/**
	 * Compares this Edge with the given object
	 * If the object is an Edge then returns whether this Edge has equal from and to Nodes with the given object
	 * 
	 * @param obj The object to be compared with
	 * @return True if these objects are equal else False
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			return ((Edge) obj).getFrom().equals(this.getFrom()) && ((Edge) obj).getTo().equals(this.getTo());

		} else {
			return super.equals(obj);
		}
	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	/**
	 * For use in Hierholzers algorithm
	 * 
	 * @return True if this node has been visited by HierholzersAlgorithm else returns false 
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * For use in Hierholzers algorithm
	 * Marks this edge as having been visited
	 */
	public void visit() {
		visited = true;

	}
}
