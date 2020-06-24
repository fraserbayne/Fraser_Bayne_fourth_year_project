package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.DeBruijnWordChecker;
import model.DeBruijnWordCheckerResults;
import model.Generators.LyndonWords;
import model.Generators.MartinsAlgorithm;
import model.Generators.GraphTheory.GraphTheory;

public class GeneratorTests {
	MartinsAlgorithm martinsAlgorithm;
	LyndonWords lyndonWords;
	GraphTheory graphTheory;

	@Before
	public void setUp() {

		martinsAlgorithm = new MartinsAlgorithm();
		lyndonWords = new LyndonWords();
		graphTheory = new GraphTheory();
	}

	@Test
	public void correctMartins() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker
				.checkDeBruijnWord(martinsAlgorithm.generateDeBruijnWord(2, 2), 2);

		assertTrue(r.isCorrect());
	}

	@Test
	public void incorrectMartins() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker
				.checkDeBruijnWord(martinsAlgorithm.generateDeBruijnWord(2, 2), 3);

		assertFalse(r.isCorrect());
	}

	@Test
	public void correctLyndonWords() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(lyndonWords.generateDeBruijnWord(2, 2), 2);

		assertTrue(r.isCorrect());
	}

	@Test
	public void incorrectLyndonWords() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(lyndonWords.generateDeBruijnWord(2, 2), 3);

		assertFalse(r.isCorrect());
	}

	@Test
	public void correctGraphTheory() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(graphTheory.generateDeBruijnWord(2, 2), 2);

		assertTrue(r.isCorrect());
	}

	@Test
	public void incorrectGraphTheory() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(graphTheory.generateDeBruijnWord(2, 2), 3);

		assertFalse(r.isCorrect());
	}

	@Test
	public void graphTheoryStartCode() {

		String startCode = "132";
		String deBruijnWord = graphTheory.generateDeBruijnWord(4, 4, startCode);
		assertTrue(deBruijnWord.startsWith(startCode));
		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(deBruijnWord, 4);

		assertTrue(r.isCorrect());
	}

	@Test
	public void graphTheoryProhibitedCodes() {
		ArrayList<String> prohibitedCodes = new ArrayList<String>();
		prohibitedCodes.add("000");
		prohibitedCodes.add("111");
		prohibitedCodes.add("101");
		prohibitedCodes.add("010");

		String deBruijnWord = graphTheory.generateDeBruijnWord(2, 3, prohibitedCodes);
		for (String string : prohibitedCodes) {
			assertFalse(deBruijnWord.contains(string));
		}
		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord(deBruijnWord, 3);

		assertFalse(r.isCorrect());
		assertTrue(r.isCorrect(prohibitedCodes));
	}

	@Test
	public void checkGraphTheoryProhibitedCodesNoPathFound() {
		ArrayList<String> prohibitedCodes = new ArrayList<String>();
		prohibitedCodes.add("101");
		prohibitedCodes.add("001");
		assertTrue(graphTheory.generateDeBruijnWord(2, 3, prohibitedCodes) == null);
	}

	@Test
	public void graphTheoryProhibitedCodesAllCodesProhibited() {
		ArrayList<String> prohibitedCodes = new ArrayList<String>();
		prohibitedCodes.add("00");
		prohibitedCodes.add("11");
		prohibitedCodes.add("10");
		prohibitedCodes.add("01");

		assertTrue(graphTheory.generateDeBruijnWord(2, 2, prohibitedCodes) == null);

	}

}
