package model.Generators.GraphTheory.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a directed graph
 */
public class Graph {
	/**
	 * Maps each Node of the graph with all Edges starting from it
	 */
	private Map<Node, List<Edge>> adjacencyList;
	private int edgeCount;
	private int nodeCount;

	public Graph() {
		edgeCount = 0;
		nodeCount = 0;
		adjacencyList = new HashMap<Node, List<Edge>>();
	}

	/**
	 * If no Edge exists between the given Nodes
	 * Creates a new Edge object between the given Nodes
	 * Adds the Edge to the adjacency list
	 * Updates the in and out degrees of the Nodes
	 * 
	 * If Nodes do not already exist, creates them
	 * 
	 * @param from Node that the edge should start from
	 * @param to Node that the edge should point to
	 * @return True if an Edge was successfully added, False if Edge already exists
	 */
	public boolean addEdge(Node from, Node to) {
		if (from != null & to != null) {
			addNode(from);
			addNode(to);
			if (findEdge(from, to) == null) {
				adjacencyList.get(from).add(new Edge(from, to));
				from.incOutDegree();
				to.incInDegree();
				edgeCount++;
				return true;
			}
		}
		return false;
	}

	/**
	 * If no Node exists with given value
	 * Creates a new Node object
	 * Adds the Node to the adjacency list
	 * increments the node count
	 * 
	 * @param value The value to be given to the new node
	 * @return True if a Node was successfully added, False if Node already exists or value is incorrect length
	 */
	public boolean addNode(String value) {

		if (value != null && findNode(value) == null) {
			Node v = new Node(value);
			adjacencyList.put(v, new ArrayList<Edge>());
			nodeCount++;
			return true;

		} else {
			return false;
		}

	}

	/**
	 * If given Node does not already exist in the Graph
	 * Adds the Node to the adjacency list
	 * increments the node count
	 * 
	 * @param node The Node to be added
	 * @return True if the Node was successfully added, False if Node already exists
	 */
	private boolean addNode(Node node) {

		if (node != null && findNode(node.getValue()) == null) {

			adjacencyList.put(node, new ArrayList<Edge>());
			nodeCount++;
			return true;

		} else {
			return false;
		}

	}

	public int edgeCount() {
		return edgeCount;
	}

	/**
	 * Returns Edge between the given Nodes one exists
	 * 
	 * @param from Node that the edge starts from
	 * @param to Node that the edge points to
	 * @return The Edge if one exists, null if it does not
	 */
	public Edge findEdge(Node from, Node to) {
		if (from != null && to != null) {
			if (findNode(from.getValue()) != null && findNode(to.getValue()) != null) {
				for (Edge v : adjacencyList.get(from)) {
					if (v.getFrom() == from && v.getTo() == to) {
						return v;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns Node with the given value if one exists
	 * 
	 * @param value Value of the node
	 * @return The Node if one exists, null if it does not
	 */
	public Node findNode(String value) {
		if (value != null) {
			for (Node v : adjacencyList.keySet()) {
				if (v.getValue().equals(value)) {
					return v;
				}
			}
		}
		return null;
	}

	/**
	 * Adds all Edges of the adjacency list to a List object and returns it
	 * 
	 * @return the List of edges
	 */
	public List<Edge> getEdges() {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {
			edges.addAll(entry.getValue());
		}
		return edges;
	}

	/**
	 * Returns any Node from the graph
	 * 
	 * @return A Node OR null if this graph contains no Nodes
	 */
	public Node getFirstNode() {
		if (nodeCount > 0) {
			return adjacencyList.entrySet().iterator().next().getKey();
		} else {
			return null;
		}
	}

	/**
	 * Adds all Nodes of the adjacency list to a List object and returns it
	 * 
	 * @return the List of Nodes
	 */
	public List<Node> getNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Entry<Node, List<Edge>> a : adjacencyList.entrySet()) {
			nodes.add(a.getKey());
		}
		return nodes;
	}

	/**
	 * For use when this graph was populated with prohibited codes
	 * Finds a Node that has an in degree less than its out degree
	 * 
	 * @return The Node if one exists, else returns result of getFirstNode which can return null
	 */
	public Node getProhibitedStartNode() {
		for (Entry<Node, List<Edge>> node : adjacencyList.entrySet()) {
			if (node.getKey().getInDegree() < node.getKey().getOutDegree()) {
				return node.getKey();
			}
		}
		return getFirstNode();
	}

	/**
	 * Returns any Edge starting from the given node that has not yet been visited
	 * Marks this Edge as visited
	 * 
	 * @param node The Node where the Edge should start from
	 * @return An unvisited Edge if one exists, null if one does not
	 */
	public Edge getUnvisitedEdge(Node node) {
		for (Edge edge : adjacencyList.get(node)) {
			if (!edge.isVisited()) {
				edge.visit();
				return edge;
			}
		}
		return null;
	}

	public int nodeCount() {
		return nodeCount;

	}

	/**
	 * Remove any Nodes from the graph which are unreachable (0 in degree and out degree)
	 * Adjusts node count accordingly
	 */
	public void removeUnreachableNodes() {
		for (Node node : getNodes()) {
			if (node.getInDegree() == 0 && node.getOutDegree() == 0) {
				adjacencyList.remove(node);
				nodeCount--;
			}
		}

	}
}
