package model;

import java.util.ArrayList;

public class UniversalPartialWordsGenerator {

	/**
	 * Generates list of universal partial words with a universal character at the given index with the given alphabet size and code length
	 * 
	 * First calculates the correct length of the u-p-words and then performs a brute force search finding each unique combination
	 * of letters in the alphabet of this length. Returns only words that are correct according to DeBruijnWordChecker
	 * 
	 * If
	 * 
	 * @param k The size of the alphabet
	 * @param n The code length
	 * @param i The index of the universal character
	 * @param universalCharacter The character used to display the universal character
	 * @return ArrayList of found u-p-words
	 */
	public static ArrayList<String> generateUniversalPartialWords(int k, int n, int i, char universalCharacter) {
		int newLength = 0;

		//Maximum length the u-p-word can be
		int length = (int) (Math.pow(k, n) + n - k);

		//Index is valid
		if (0 <= i && i < length) {
			//Index is in the middle(neither considered at the end or start)
			if (n - 1 <= i && i < length - (n * (k - 1)) - 1) {
				newLength = (int) (Math.pow(k, n) + n * (2 - k) - 1);
			} else {
				int inverseIndex = i;
				//End
				if (i >= length - (n * (k - 1)) - 1) {
					//Update index to be its distance from the end
					inverseIndex = ((length - i - 1) / (k));
				}
				//Start
				newLength = (int) (Math.pow(k, n) + n - ((inverseIndex + 1) * k) + inverseIndex);
			}

			if (i > newLength / 2) {
				ArrayList<String> reversed = new ArrayList<String>();
				//Reverse each upword
				for (String upword : generateUniversalPartialWords(k, n, newLength, newLength - i - 1,
						universalCharacter)) {
					reversed.add(new StringBuilder(upword).reverse().toString());
				}
				return reversed;
			} else {
				return generateUniversalPartialWords(k, n, newLength, i, universalCharacter);
			}
		} else {
			return null;
		}

	}

	private static ArrayList<String> generateUniversalPartialWords(int k, int n, int newLength, int index,
			char universalCharacter) {

		ArrayList<String> found = new ArrayList<String>();
		generateUniversalPartialWords(k, n, newLength, "", index, new ArrayList<String>(), universalCharacter, found);

		return found;

	}

	/**
	 * Generates list of universal partial words with a universal character at the given index with the given alphabet size and code length
	 * Adds any found u-p-words to a list
	 * 
	 * Recursively extends the given u-p-word for each letter in the given alphabet
	 * At each step adds any new code found to covered codes list
	 * Ends when either the current u-p-word is of the correct length, adding it to the current list if it is correct
	 * OR ends when a code is found that already exists in the covered codes list, indicating that this u-p-word is incorrect
	 * 
	 * @param k The alphabet size
	 * @param n The code length
	 * @param wordLength The current depth, indicates when u-p-word is complete
	 * @param upword The u-p-word so far
	 * @param index The index the universal character is to be placed
	 * @param covered List of codes covered so far in this u-p-word
	 * @param universalCharacter The character used to display the universal character
	 * @param found List of u-p-words found so far overall
	 */
	private static void generateUniversalPartialWords(int k, int n, int wordLength, String upword, int index,
			ArrayList<String> covered, char universalCharacter, ArrayList<String> found) {

		//u-p-word is complete
		if (upword.length() == wordLength) {
			if (DeBruijnWordChecker.checkUniversalPartialWord(upword, n, universalCharacter).isCorrect()) {
				//Collect only correct results
				found.add(upword);
			}
		} else {
			//Reached index of universal character
			if (upword.length() == index) {
				//Recurse adding ONLY the universal character to the u-p-word
				generateUniversalPartialWords(k, n, wordLength, upword + universalCharacter, index,
						new ArrayList<String>(covered), universalCharacter, found);
			} else {
				//u-p-word long enough to contain codes of length n
				if (upword.length() >= n) {
					String currentcode = upword.substring(upword.length() - n);
					//If universal character not yet reached, mark current code as covered
					if (currentcode.indexOf(universalCharacter) < 0) {
						if (!covered.contains(currentcode)) {
							covered.add(currentcode);
						} else {
							//This u-p-word is incorrect, stop recursing
							return;
						}
					} else {
						//If universal character reached, mark code for each letter as covered
						for (int i = 0; i < k; i++) {
							String newCode = currentcode.replaceFirst(universalCharacter + "", i + "");
							if (!covered.contains(newCode)) {
								covered.add(newCode);
							} else {
								//This u-p-word is incorrect, stop recursing
								return;
							}
						}
					}
				}
				//Continue for each letter
				for (int i = 0; i < k; i++) {
					generateUniversalPartialWords(k, n, wordLength, upword + i, index, new ArrayList<String>(covered),
							universalCharacter, found);
				}
			}
		}
	}

	/**
	 * Calculates the maximum length for a universal partial word with given alphabet size and code length
	 * This indicates the maximum index for a universal character
	 * 
	 * @param k The alphabet size
	 * @param n The code length
	 * @return The length/maximum index
	 */
	public static int getMaxIndex(int k, int n) {

		return (int) (Math.pow(k, n) + n - k) - 1;

	}
}
