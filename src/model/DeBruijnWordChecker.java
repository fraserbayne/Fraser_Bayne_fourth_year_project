package model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class used to verify De Bruijn words and universal partial words
 *
 */
public class DeBruijnWordChecker {

	/**
	 * Checks if the given word is a valid De Bruijn word
	 * First Generates a list of codes which should appear in the word
	 * Then calculates each code that appears in the word and updates list of missing and duplicate codes
	 * Then returns a DeBruijnWordCheckerResults which holds a list of any missing and duplicate codes
	 * 
	 * 
	 * @param DeBruijnWord The word to be checked
	 * @param n The code length
	 * @return DeBruijnWordCheckerResults containing a list of all codes that are missing from the word and any duplicates
	 */
	public static DeBruijnWordCheckerResults checkDeBruijnWord(String DeBruijnWord, int n) {

		ArrayList<Character> alphabet = generateAlphabet(DeBruijnWord);

		HashSet<String> remainingCodes = generateAllCodes(alphabet, n);
		HashSet<String> duplicateCodes = new HashSet<String>();

		for (int i = 0; i < DeBruijnWord.length() - n + 1; i++) {

			//Code is length n code in word
			String code = DeBruijnWord.substring(i, i + n);

			//Mark this code as covered, if code has already been covered mark it as duplicate
			if (!remainingCodes.remove(code)) {
				duplicateCodes.add(code);
			}

		}

		//Create new DeBruijnWordCheckerResults to hold the results
		return new DeBruijnWordCheckerResults(remainingCodes, duplicateCodes);
	}

	/**
	 * Checks if the given word is a valid universal partial word taking into account the given universal character
	 * First Generates a list of codes which should appear in the word
	 * Then calculates each code that appears in the word passes it to checkUniversalCharacter along with list of missing and duplicate codes
	 * Finally returns a DeBruijnWordCheckerResults which holds a list of any missing and duplicate codes
	 * 
	 * @param DeBruijnWord
	 * @param n The code length
	 * @param universalCharacter
	 * @return
	 */
	public static DeBruijnWordCheckerResults checkUniversalPartialWord(String DeBruijnWord, int n,
			char universalCharacter) {

		ArrayList<Character> alphabet = generateAlphabet(DeBruijnWord);
		//Removes the universal character from generated alphabet
		alphabet.remove((Character) universalCharacter);
		HashSet<String> remainingCodes = generateAllCodes(alphabet, n);
		HashSet<String> duplicateCodes = new HashSet<String>();

		for (int i = 0; i < DeBruijnWord.length() - n + 1; i++) {

			//Code is length n code in word
			String code = DeBruijnWord.substring(i, i + n);
			checkUniversalCharacter(code, n, universalCharacter, alphabet, remainingCodes, duplicateCodes);
		}

		return new DeBruijnWordCheckerResults(remainingCodes, duplicateCodes);
	}

	/**
	 * Takes a code and marks it as covered or duplicate appropriately
	 * If the code contains a universal character, calls this method recursively with the universal character replaced by each letter in the alphabet
	 * 
	 * @param code The current code being considered
	 * @param n The code length
	 * @param universalCharacter 
	 * @param alphabet The alphabet of this word and universal character
	 * @param remainingCodes List of codes not yet covered in word
	 * @param duplicateCodes List of duplicate codes in word
	 */
	private static void checkUniversalCharacter(String code, int n, char universalCharacter,
			ArrayList<Character> alphabet, HashSet<String> remainingCodes, HashSet<String> duplicateCodes) {

		//If code contains a universal character
		if (code.indexOf(universalCharacter) >= 0) {

			//For each letter in the alphabet
			for (int j = 0; j < alphabet.size(); j++) {
				//Calculate code with the universal character replaced with this letter
				//Then pass this new code recursively
				String newCode = code.replaceFirst(universalCharacter + "", "" + alphabet.get(j));
				checkUniversalCharacter(newCode, n, universalCharacter, alphabet, remainingCodes, duplicateCodes);

			}

		} else {

			//Mark this code as covered, if code has already been covered mark it as duplicate
			if (!remainingCodes.remove(code)) {
				duplicateCodes.add(code);
			}

		}
	}

	/**
	 * 
	 * Generates a HashSet of all unique codes of length n that exist using the given alphabet
	 * 
	 * @param alphabet
	 * @param n The code length
	 * @return The codes generated
	 */
	private static HashSet<String> generateAllCodes(ArrayList<Character> alphabet, int n) {
		HashSet<String> allCodes = new HashSet<String>();
		generateAllCodes(alphabet, "", alphabet.size(), n, allCodes);
		return new HashSet<String>(allCodes);

	}

	/**
	 * 
	 * Adds each letter in the alphabet to the current code and call recursively
	 * Repeat this extension until codes length is n
	 * 
	 * @param alphabet
	 * @param code The current code so far
	 * @param k The alphabet size
	 * @param n Initially the code length, used as depth in recursive calls
	 * @param codes
	 */
	private static void generateAllCodes(ArrayList<Character> alphabet, String code, int k, int n,
			HashSet<String> codes) {

		//If code length is n
		if (n == 0) {

			codes.add(code);

		} else {

			//For each letter in alphabet
			for (int i = 0; i < k; i++) {

				//Extend the code with current letter
				String newCode = code + alphabet.get(i);

				//Calls recursively using n-1 to update the depth of recursion
				generateAllCodes(alphabet, newCode, k, n - 1, codes);
			}
		}
	}

	/**
	 * Extracts alphabet from all existing characters in given word
	 * 
	 * @param word
	 * @return List of characters that appear in word
	 */
	private static ArrayList<Character> generateAlphabet(String word) {
		ArrayList<Character> alphabet = new ArrayList<Character>();

		for (int i = 0; i < word.length(); i++) {

			Character c = word.charAt(i);
			if (!alphabet.contains(c)) {
				alphabet.add(c);

			}
		}
		return alphabet;
	}
}
