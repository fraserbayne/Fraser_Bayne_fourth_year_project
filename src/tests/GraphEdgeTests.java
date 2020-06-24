package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Generators.GraphTheory.Graph.Edge;
import model.Generators.GraphTheory.Graph.Graph;
import model.Generators.GraphTheory.Graph.Node;

public class GraphEdgeTests {
	Graph graph;

	@Before
	public void setUp() {

		graph = new Graph();

	}

	@Test
	public void addValidEdge() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());

		Node node1 = new Node("0000");
		Node node2 = new Node("0000");

		assertTrue(graph.addEdge(node1, node2));
		assertNotNull(graph.findEdge(node1, node2));

		assertTrue(graph.edgeCount() == 1);
		assertFalse(graph.getEdges().isEmpty());
	}

	@Test
	public void addEdgeWithNewNodes() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());
		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value1 = "0000";
		String value2 = "0001";
		Node node1 = new Node(value1);
		Node node2 = new Node(value2);

		assertNull(graph.findNode(value1));
		assertNull(graph.findNode(value2));

		assertTrue(graph.addEdge(node1, node2));
		assertNotNull(graph.findEdge(node1, node2));

		//Nodes have been added to graph
		assertTrue(graph.findNode(value1).equals(node1));
		assertTrue(graph.findNode(value2).equals(node2));

		assertTrue(graph.edgeCount() == 1);
		assertFalse(graph.getEdges().isEmpty());
		assertTrue(graph.nodeCount() == 2);
		assertFalse(graph.getNodes().isEmpty());
	}

	@Test
	public void addNullEdge() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());

		Node node1 = null;
		Node node2 = null;
		assertFalse(graph.addEdge(node1, node2));
		assertNull(graph.findEdge(node1, node2));

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());
	}

	@Test
	public void addEmptyEdge() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());

		Node node1 = new Node("");
		Node node2 = new Node("");

		assertTrue(graph.addEdge(node1, node2));
		assertNotNull(graph.findEdge(node1, node2));

		assertTrue(graph.edgeCount() == 1);
		assertFalse(graph.getEdges().isEmpty());
	}

	@Test
	public void addNonNumericalValueEdge() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());

		Node node1 = new Node("abcd");
		Node node2 = new Node("efgh");

		assertTrue(graph.addEdge(node1, node2));
		assertNotNull(graph.findEdge(node1, node2));

		assertTrue(graph.edgeCount() == 1);
		assertFalse(graph.getEdges().isEmpty());
	}

	@Test
	public void addDifferentLengthEdge() {

		assertTrue(graph.edgeCount() == 0);
		assertTrue(graph.getEdges().isEmpty());

		String value1 = "00000";
		String value2 = "000001";
		Node node1 = new Node(value1);
		Node node2 = new Node(value2);

		assertTrue(graph.addEdge(node1, node2));
		assertNotNull(graph.findEdge(node1, node2));

		assertNotNull(graph.findNode(value1));
		assertNotNull(graph.findNode(value2));

		assertFalse(graph.getEdges().isEmpty());
		assertTrue(graph.edgeCount() == 1);

	}

	@Test
	public void findEdge() {

		String value1 = "0000";
		String value2 = "0001";
		Node node1 = new Node(value1);
		Node node2 = new Node(value2);

		assertNull(graph.findEdge(node1, node2));

		assertTrue(graph.addEdge(node1, node2));

		Edge edge = graph.findEdge(node1, node2);
		assertTrue(edge.getFrom().equals(node1));
		assertTrue(edge.getTo().equals(node2));

	}

	@Test
	public void getUnvisitedEdge() {

		String value1 = "0000";
		String value2 = "0001";

		graph.addEdge(new Node(value1), new Node(value2));

		Edge edge = graph.findEdge(graph.findNode(value1), graph.findNode(value2));

		assertFalse(edge.isVisited());

		assertTrue(graph.getUnvisitedEdge(graph.findNode(value1)) == edge);

		assertTrue(edge.isVisited());
		assertNull(graph.getUnvisitedEdge(graph.findNode(value1)));

	}

	@Test
	public void getVisitedEdge() {

		String value1 = "0000";
		String value2 = "0001";

		graph.addEdge(new Node(value1), new Node(value2));

		Edge edge = graph.findEdge(graph.findNode(value1), graph.findNode(value2));

		assertFalse(edge.isVisited());
		edge.visit();
		assertTrue(edge.isVisited());

		assertNull(graph.getUnvisitedEdge(graph.findNode(value1)));

	}
}
