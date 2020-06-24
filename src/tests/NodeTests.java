package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Generators.GraphTheory.Graph.Graph;
import model.Generators.GraphTheory.Graph.Node;

public class NodeTests {
	Graph graph;
	Node node1;
	Node node2;
	Node node3;

	@Before
	public void setUp() {

		node1 = new Node("0000");
		node2 = new Node("0000");
		node3 = new Node("0000");
	}

	@Test
	public void nodeCreation() {
		String value = "0000";

		Node node = new Node(value);
		assertTrue(node.getValue() == value);
	}

	@Test
	public void equalsSameValue() {

		assertTrue(node1.getValue() == node2.getValue());

	}

	@Test
	public void equalsReflexive() {
		assertTrue(node1.equals(node1));
	}

	@Test
	public void equalsSymmetric() {
		assertTrue(node1.equals(node2));
		assertTrue(node2.equals(node1));
	}

	@Test
	public void equalsTransitive() {
		assertTrue(node1.equals(node2));
		assertTrue(node2.equals(node3));
		assertTrue(node3.equals(node1));
	}

	@Test
	public void equalsObject() {
		Object Obj = new Object();
		assertFalse(node1.equals(Obj));
	}

	@Test
	public void equalsDifferentValue() {
		Node node = new Node("0001");
		assertFalse(node.equals(node1));
	}

	@Test
	public void equalsNull() {
		assertFalse(node1.equals(null));
	}

	@Test
	public void equalsConsistency() {
		assertTrue(node1.equals(node2));
		assertTrue(node1.equals(node2));
	}

}
