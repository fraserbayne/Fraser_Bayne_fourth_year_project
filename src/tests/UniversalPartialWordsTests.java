package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.DeBruijnWordChecker;
import model.UniversalPartialWordsGenerator;

public class UniversalPartialWordsTests {

	@Test
	public void correctStartUniversalPartialWords() {

		ArrayList<String> upwords = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 3, 0, 'x');
		assertFalse(upwords.isEmpty());
		for (String upword : upwords) {
			assertTrue(DeBruijnWordChecker.checkUniversalPartialWord(upword, 3, 'x').isCorrect());
			assertTrue(upword.charAt(0) == 'x');
		}

	}

	@Test
	public void correctMiddleUniversalPartialWords() {

		ArrayList<String> upwords = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 4, 5, 'x');
		assertFalse(upwords.isEmpty());
		for (String upword : upwords) {
			assertTrue(DeBruijnWordChecker.checkUniversalPartialWord(upword, 4, 'x').isCorrect());
			assertTrue(upword.charAt(5) == 'x');
		}

	}

	@Test
	public void correctEndUniversalPartialWords() {

		ArrayList<String> upwords = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 3, 8, 'x');
		assertFalse(upwords.isEmpty());
		for (String upword : upwords) {
			assertTrue(DeBruijnWordChecker.checkUniversalPartialWord(upword, 3, 'x').isCorrect());
			assertTrue(upword.charAt(8) == 'x');
		}

	}

	@Test
	public void universalPartialWordsReversed() {

		ArrayList<String> upwords1 = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 3, 0, 'x');
		ArrayList<String> upwords2 = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 3, 8, 'x');
		ArrayList<String> reversed = new ArrayList<String>();
		for (String upword : upwords2) {
			reversed.add(new StringBuilder(upword).reverse().toString());
		}

		assertTrue(upwords1.equals(reversed));

	}

	@Test
	public void universalPartialWordsInvalidIndex() {

		ArrayList<String> upwords = UniversalPartialWordsGenerator.generateUniversalPartialWords(2, 3, 9, 'x');
		assertTrue(upwords == null);

	}

	@Test
	public void noValidUniversalPartialWords() {

		ArrayList<String> upwords = UniversalPartialWordsGenerator.generateUniversalPartialWords(3, 2, 0, 'x');

		assertTrue(upwords.isEmpty());

	}

}
