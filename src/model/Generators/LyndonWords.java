package model.Generators;

/**
 * Class used to generate De Bruijn words by chaining together Lyndon words
 *
 */
public class LyndonWords implements Generator {

	@Override
	public String generateDeBruijnWord(int k, int n) {
		StringBuilder DeBruijnSequence = new StringBuilder();
		//Initial call gen(1, 1)
		gen(1, 1, DeBruijnSequence, k, n, new int[n + 1]);
		//Convert sequence to word
		return DeBruijnSequence.append(DeBruijnSequence.subSequence(0, n - 1)).toString();
	}

	/**
	 * 
	 * @param t
	 * @param p
	 * @param DeBruijnSequence StringBuilder used to represent the De Bruijn sequence
	 * @param k The size of the alphabet
	 * @param n The code length
	 * @param a
	 */
	private void gen(int t, int p, StringBuilder DeBruijnSequence, int k, int n, int[] a) {
		int j;
		if (t > n) {
			Printlt(p, DeBruijnSequence, a, n);
		} else {
			a[t] = a[t - p];
			gen(t + 1, p, DeBruijnSequence, k, n, a);
			for (j = a[t - p] + 1; j < k; j++) {
				a[t] = j;
				gen(t + 1, t, DeBruijnSequence, k, n, a);
			}
		}
	}

	/**
	 * Adds to the current sequence only if the length of the Lyndon word divides n
	 * 
	 * @param p
	 * @param sequence StringBuilder onto which the Lyndon words are appended
	 * @param a
	 * @param n
	 */
	private void Printlt(int p, StringBuilder sequence, int[] a, int n) {
		if (n % p == 0) {
			for (int i = 1; i <= p; i++) {
				sequence.append(a[i]);
			}
		}
	}
}
