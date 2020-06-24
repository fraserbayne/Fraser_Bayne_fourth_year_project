package model.Generators.GraphTheory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import model.Generators.Generator;
import model.Generators.GraphTheory.Graph.Edge;
import model.Generators.GraphTheory.Graph.Graph;
import model.Generators.GraphTheory.Graph.Node;

/**
 * Class used to generate De Bruijn words using directed graphs
 *
 */
public class GraphTheory implements Generator {

	/**
	 * Populates a Graph object with Nodes and Edges to create a De Bruijn graph for values k and n
	 *
	 * @param k Size of the alphabet
	 * @param n Length of each code
	 * @param graph The Graph to populate
	 * @param prohibit List of prohibited codes that should not be added to the graph
	 */
	private static void populateDeBruijnGraph(int k, int n, Graph graph, ArrayList<String> prohibit) {

		String startString = "";
		Deque<String> agenda = new ArrayDeque<String>();

		//Create initial node
		for (int i = 0; i < n; i++) {
			startString += 0;
		}

		//Add initial node to graph and agenda
		graph.addNode(startString);
		agenda.add(startString);

		while (!agenda.isEmpty()) {

			String currentNode = agenda.removeFirst();
			String start = currentNode.substring(1, n);
			//Expand current node by creating adjacent nodes for each letter in alphabet
			for (int i = 0; i < k; i++) {
				//Calculate node representing FROM currentNode TO current letter
				String newNode = start + i;

				//If not already expanded, add node to agenda
				if (graph.addNode(newNode)) {
					agenda.add(newNode);
				}

				//If this code is not prohibited, add new edge to graph
				if (!prohibit.contains(currentNode + i)) {
					graph.addEdge(graph.findNode(currentNode), graph.findNode(newNode));
				}
			}

		}
		graph.removeUnreachableNodes();
	}

	/**
	 * Returns a valid De Bruijn word for values k and n
	 * Word is calculated by result of Hierholzers algorithm given Graph of size n-1
	 * 
	 */
	@Override
	public String generateDeBruijnWord(int k, int n) {

		//Create and populate graph of size n-1
		Graph graph = new Graph();
		populateDeBruijnGraph(k, n - 1, graph, new ArrayList<String>());

		//Run Hierholzers' algorithm
		return convertToDeBruijnWord(findEulerianPath(graph, graph.getFirstNode()));

	}

	/**
	 * Returns a valid De Bruijn word for values k and n, beginning with the given code
	 * Word is calculated by result of Hierholzers algorithm given Graph of size n-1 and code to start with
	 * 
	 * @param k
	 * @param n
	 * @param startNode The code the word should start with
	 * @return String containing the De Bruijn word
	 */
	public String generateDeBruijnWord(int k, int n, String startNode) {

		//Create and populate graph of size n-1
		Graph graph = new Graph();
		populateDeBruijnGraph(k, n - 1, graph, new ArrayList<String>());

		Node node = graph.findNode(startNode);
		if (node == null) {
			return null;
		}
		//Run Hierholzers' algorithm, starting from specified node
		return convertToDeBruijnWord(findEulerianPath(graph, node));
	}

	/**
	 * Returns a valid De Bruijn word for values k and n, not containing any codes in given list of prohibited codes
	 * Word is calculated by result of Hierholzers algorithm given Graph of size n-1 and prohibited codes
	 * 
	 * @param k
	 * @param n
	 * @param prohibit Any codes that should not appear in the word
	 * @return String containing the De Bruijn word OR null if no path is found
	 */
	public String generateDeBruijnWord(int k, int n, ArrayList<String> prohibit) {

		//Create and populate graph of size n-1
		Graph graph = new Graph();
		populateDeBruijnGraph(k, n - 1, graph, prohibit);

		//Run Hierholzers' algorithm, specifying prohibited nodes
		return convertToDeBruijnWord(findEulerianPath(graph, graph.getProhibitedStartNode()));

	}

	/**
	 * Finds a Eulerian path in a graph if one exists
	 * Will start from the given Node if it is not null
	 * 
	 * @param graph 
	 * @param startNode 
	 * @return List of Edges representing a Eulerian path OR null if no path exists
	 */
	private ArrayList<Edge> findEulerianPath(Graph graph, Node startNode) {
		if (startNode == null) {
			return null;
		}

		//If no path exists, return null
		int oddDegree = 0;
		for (Node n : graph.getNodes()) {
			if (n.getInDegree() != n.getOutDegree()) {
				oddDegree++;
			}
		}
		if (oddDegree != 0 && oddDegree != 2) {
			return null;
		}

		Edge edge = graph.getUnvisitedEdge(startNode);
		ArrayDeque<Edge> back = new ArrayDeque<Edge>();
		ArrayDeque<Edge> forward = new ArrayDeque<Edge>();

		//Create path of Edges
		forwardHelper(forward, edge, graph);

		//Work backwards through the current path
		while (!forward.isEmpty()) {
			edge = forward.pop();

			//Start a new branch from the current Edge and add it to current path
			back.addFirst(edge);
			forwardHelper(forward, graph.getUnvisitedEdge(edge.getFrom()), graph);
		}

		//If a path was found AND is the correct size
		if (!back.isEmpty() && back.size() == graph.edgeCount()) {
			return new ArrayList<Edge>(back);
		} else {
			return null;
		}
	}

	/**
	 * Helper method
	 * 
	 * Constructs list of unvisited Edges starting from the given Edge
	 * Marks each Edge as visited
	 * Ends when no further extension can be made
	 * 
	 * @param forward ArrayDeque in which the Edges are placed
	 * @param edge The Edge to start from
	 * @param graph
	 */
	private static void forwardHelper(ArrayDeque<Edge> forward, Edge edge, Graph graph) {

		//Add Edges to the path until a dead end is reached
		while (edge != null) {
			forward.push(edge);
			edge = graph.getUnvisitedEdge(edge.getTo());
		}
	}

	/**
	 * Helper method
	 * 
	 * Converts a list of Edge objects into a De Bruijn word
	 * 
	 * @param path ArrayList of Edges representing a Eulerian path
	 * @return The De Bruijn word
	 */
	private String convertToDeBruijnWord(ArrayList<Edge> path) {
		//No path found
		if (path == null) {
			return null;
		}

		//Start word with the start Node of the first edge
		String deBruijnWord = path.get(0).getFrom().getValue();
		//Chain together the n-1 rightmost characters of the TO Node of each Edge
		for (Edge e : path) {
			String value = e.getTo().getValue();
			deBruijnWord += (value.substring(value.length() - 1));
		}
		return deBruijnWord;
	}

}
