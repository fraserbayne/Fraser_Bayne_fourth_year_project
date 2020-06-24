package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class used for storing and handling data returned from a DeBruijnWordChecker method call
 *
 */
public class DeBruijnWordCheckerResults {

	/*
	 * List of words that appeared multiple times in the word
	 */
	private ArrayList<String> duplicateWords;
	/*
	 * List of words that were missing from the word
	 */
	private ArrayList<String> missingWords;

	public DeBruijnWordCheckerResults(Set<String> missingWords, Set<String> duplicateWords) {
		this.missingWords = new ArrayList<String>(missingWords);
		this.duplicateWords = new ArrayList<String>(duplicateWords);
	}

	public ArrayList<String> getDuplicates() {
		return duplicateWords;
	}

	public ArrayList<String> getMissing() {
		return missingWords;
	}

	/**
	 * Calculates if the results indicate a valid De Bruijn word
	 * 
	 * @return True if word is correct else False
	 */
	public boolean isCorrect() {

		return missingWords.isEmpty() && duplicateWords.isEmpty();
	}

	/**
	 * Calculates if the results indicate a valid De Bruijn word, ignoring any missing codes given
	 * 
	 * @param prohibit List of codes to ignore
	 * @return
	 */
	public boolean isCorrect(List<String> prohibit) {
		ArrayList<String> rTemp = new ArrayList<String>(missingWords);
		rTemp.removeAll(prohibit);
		return duplicateWords.isEmpty() && rTemp.isEmpty();
	}

}