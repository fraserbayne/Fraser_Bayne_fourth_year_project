package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Generators.GraphTheory.Graph.Graph;
import model.Generators.GraphTheory.Graph.Node;

public class GraphNodeTests {
	Graph graph;

	@Before
	public void setUp() {

		graph = new Graph();

	}

	@Test
	public void addNullNode() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value = null;
		assertFalse(graph.addNode(value));
		assertNull(graph.findNode(value));

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());
	}

	@Test
	public void addEmptyNode() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value = "";
		assertTrue(graph.addNode(value));
		assertTrue(graph.findNode(value).getValue() == value);

		assertTrue(graph.nodeCount() == 1);
		assertFalse(graph.getNodes().isEmpty());
	}

	@Test
	public void addValidNode() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value = "0000";
		assertTrue(graph.addNode(value));
		Node node = graph.findNode(value);
		assertTrue(node.getValue() == value);

		assertTrue(graph.getNodes().contains(node));
		assertTrue(graph.nodeCount() == 1);
		assertFalse(graph.getNodes().isEmpty());

		assertFalse(graph.addNode("0000"));
	}

	@Test
	public void addNonNumericalValueNode() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value = "abcd";
		assertTrue(graph.addNode(value));
		Node node = graph.findNode(value);
		assertTrue(node.getValue() == value);

		assertTrue(graph.getNodes().contains(node));
		assertTrue(graph.nodeCount() == 1);
		assertFalse(graph.getNodes().isEmpty());

		assertFalse(graph.addNode("abcd"));
	}

	@Test
	public void addDifferentLengthNodes() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		assertTrue(graph.addNode("00000"));
		assertTrue(graph.addNode("0000"));

		assertFalse(graph.getNodes().isEmpty());
		assertTrue(graph.nodeCount() == 2);

	}

	@Test
	public void findNode() {

		String value = "0000";
		assertNull(graph.findNode(value));

		graph.addNode(value);

		assertTrue(graph.findNode(value).getValue() == value);

	}

	@Test
	public void removeUnreachableNode() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value = "0000";

		graph.addNode(value);

		assertNotNull(graph.findNode(value));

		assertTrue(graph.nodeCount() == 1);
		assertFalse(graph.getNodes().isEmpty());

		graph.removeUnreachableNodes();

		assertNull(graph.findNode(value));

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

	}

	@Test
	public void removeReachableNodes() {

		assertTrue(graph.nodeCount() == 0);
		assertTrue(graph.getNodes().isEmpty());

		String value1 = "0000";
		String value2 = "0001";

		graph.addEdge(new Node(value1), new Node(value2));

		assertTrue(graph.nodeCount() == 2);
		assertFalse(graph.getNodes().isEmpty());

		graph.removeUnreachableNodes();

		assertNotNull(graph.findNode(value1));
		assertNotNull(graph.findNode(value2));

		assertTrue(graph.nodeCount() == 2);
		assertFalse(graph.getNodes().isEmpty());

	}

}
