package model.Generators.GraphTheory.Graph;

/**
 * Represents a Node in a graph
 */
public class Node {
	/**
	 * The number of Edges directed towards this Node
	 */
	private int inDegree = 0;
	/**
	 * The number of Edges directed from this Node
	 */
	private int outDegree = 0;
	private String value;

	public Node(String value) {
		this.value = value;
	}

	@Override
	/**
	 * Compares this Node with the given object
	 * If the object is a Node then returns whether this Node has value equal to the given Nodes value
	 * 
	 * @param obj The object to be compared with
	 * @return True if these objects are equal else False
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return ((Node) obj).getValue() == this.value;

		} else {
			return super.equals(obj);
		}
	}

	public int getInDegree() {
		return inDegree;
	}

	public int getOutDegree() {
		return outDegree;
	}

	public String getValue() {
		return value;
	}

	public void incInDegree() {
		inDegree++;
	}

	public void incOutDegree() {
		outDegree++;
	}

}
