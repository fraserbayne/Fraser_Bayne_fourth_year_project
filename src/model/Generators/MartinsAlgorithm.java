package model.Generators;

import java.util.HashSet;

/**
 * Class used to generate De Bruijn words using Martins' algorithm
 *
 */
public class MartinsAlgorithm implements Generator {

	@Override
	public String generateDeBruijnWord(int k, int n) {

		String DeBruijnWord = "";
		boolean extension;
		HashSet<String> covered = new HashSet<String>();

		//Initialise word to (k - 1)^n-1
		for (int i = 0; i < n - 1; i++) {
			DeBruijnWord += k - 1;
		}

		do {
			extension = false;
			//For each letter in the alphabet
			for (int i = 0; i < k; i++) {
				//Calculate new code resulting from letter being added to DeBruijnWord
				String newCode = DeBruijnWord.substring(DeBruijnWord.length() - (n - 1)) + i;
				//If newCode does not already appear in DeBruijnWord
				if (!covered.contains(newCode)) {
					//Mark code as covered, extend DeBruijnWord with this letter and continue extension
					covered.add(newCode);
					DeBruijnWord += i;
					extension = true;
					break;
				}

			}
		} while (extension);

		//Extension will only cease when DeBruijnWord contains a valid De Bruijn word
		return DeBruijnWord;
	}
}
