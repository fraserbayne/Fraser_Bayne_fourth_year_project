package model.Generators;

/**
 * Interface used by classes used to generate standard De Bruijn words
 *
 */
public interface Generator {
	/**
	 * Returns a valid De Bruijn word for the given alphabet size and code length
	 * @param k Alphabet size
	 * @param n Code length
	 * @return String containing the De Bruijn word
	 */
	public String generateDeBruijnWord(int k, int n);

}
