package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Generators.GraphTheory.Graph.Edge;
import model.Generators.GraphTheory.Graph.Node;

public class EdgeTests {
	Edge edge1;
	Edge egde2;
	Edge edge3;

	@Before
	public void setUp() {

		edge1 = new Edge(new Node("0000"), new Node("0001"));
		egde2 = new Edge(new Node("0000"), new Node("0001"));
		edge3 = new Edge(new Node("0000"), new Node("0001"));
	}

	@Test
	public void edgeCreation() {
		Node node1 = new Node("0000");
		Node node2 = new Node("0001");

		Edge edge = new Edge(node1, node2);
		assertTrue(edge.getFrom().equals(node1));
		assertTrue(edge.getTo().equals(node2));
	}

	@Test
	public void equalsSameValue() {

		assertTrue(edge1.getFrom().equals(egde2.getFrom()));
		assertTrue(edge1.getTo().equals(egde2.getTo()));
	}

	@Test
	public void equalsReflexive() {
		assertTrue(edge1.equals(edge1));
	}

	@Test
	public void equalsSymmetric() {
		assertTrue(edge1.equals(egde2));
		assertTrue(egde2.equals(edge1));
	}

	@Test
	public void equalsTransitive() {
		assertTrue(edge1.equals(egde2));
		assertTrue(egde2.equals(edge3));
		assertTrue(edge3.equals(edge1));
	}

	@Test
	public void equalsObject() {
		Object Obj = new Object();
		assertFalse(edge1.equals(Obj));
	}

	@Test
	public void equalsDifferentValue() {
		Edge edge = new Edge(new Node("0000"), new Node("0000"));
		assertFalse(edge.equals(edge1));
	}

	@Test
	public void equalsNull() {
		assertFalse(edge1.equals(null));
	}

	@Test
	public void equalsConsistency() {
		assertTrue(edge1.equals(egde2));
		assertTrue(edge1.equals(egde2));
	}

}
