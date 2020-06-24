package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.DeBruijnWordChecker;
import model.DeBruijnWordCheckerResults;

public class DeBruijnWordCheckerTests {

	@Test
	public void checkCorrect() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("00110", 2);
		assertTrue(r.isCorrect());
		assertTrue(r.getDuplicates().isEmpty());
		assertTrue(r.getMissing().isEmpty());
	}

	@Test
	public void checkMissing() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("0110", 2);
		assertFalse(r.isCorrect());
		assertTrue(r.getDuplicates().isEmpty());
		assertTrue(r.getMissing().contains("00"));
	}

	@Test
	public void checkDuplicate() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("000110", 2);

		assertFalse(r.isCorrect());
		assertTrue(r.getDuplicates().contains("00"));
		assertTrue(r.getMissing().isEmpty());

	}

	@Test
	public void checkProhbited() {
		ArrayList<String> prohibitedCodes = new ArrayList<String>();
		prohibitedCodes.add("00");

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("0110", 2);

		assertFalse(r.isCorrect());
		assertTrue(r.isCorrect(prohibitedCodes));

		assertTrue(r.getDuplicates().isEmpty());
		assertTrue(r.getMissing().contains("00"));
	}

	@Test
	public void checkIncorrectCodeLength() {

		ArrayList<String> prohibitedCodes = new ArrayList<String>();

		prohibitedCodes.add("000");
		prohibitedCodes.add("100");
		prohibitedCodes.add("111");
		prohibitedCodes.add("101");
		prohibitedCodes.add("010");

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("00110", 3);
		assertFalse(r.isCorrect());
		assertTrue(r.isCorrect(prohibitedCodes));

		assertTrue(r.getDuplicates().isEmpty());
		assertTrue(r.getMissing().equals(prohibitedCodes));
	}

	@Test
	public void checkCorrectMultipleSolutions() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("00110", 2);
		assertTrue(r.isCorrect());

		r = DeBruijnWordChecker.checkDeBruijnWord("10011", 2);
		assertTrue(r.isCorrect());
	}

	@Test
	public void checkEmpty() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkDeBruijnWord("", 0);
		assertTrue(r.isCorrect());

	}

	@Test
	public void checkCorrectUniversalPartialWord() {

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkUniversalPartialWord("x011", 2, 'x');
		assertTrue(r.isCorrect());
		assertTrue(r.getDuplicates().isEmpty());
		assertTrue(r.getMissing().isEmpty());
	}

	@Test
	public void checkIncorrectUniversalPartialWord() {
		ArrayList<String> missingCodes = new ArrayList<String>();

		missingCodes.add("00");
		missingCodes.add("01");
		missingCodes.add("10");

		DeBruijnWordCheckerResults r = DeBruijnWordChecker.checkUniversalPartialWord("10x011", 2, 'x');
		assertFalse(r.isCorrect());
		assertTrue(r.getMissing().isEmpty());
		assertTrue(r.getDuplicates().equals(missingCodes));

	}

}
