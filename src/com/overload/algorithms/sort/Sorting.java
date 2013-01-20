package com.overload.algorithms.sort;

/**
 * A collection of optimized sorting algorithms.<br>
 * A sorting class instance is NOT thread-safe, however static methods are thread-safe.
 * @author Odell
 */
public class Sorting implements AlgorithmDefinition {
	
	protected final Algorithm algorithm;
	protected final AlgorithmDefinition definition;
	
	/**
	 * Creates a sorting provider given an algorithm type.
	 * @param alg the algorithm implementation to use.
	 */
	public Sorting(final Algorithm alg) {
		if (alg == null)
			throw new IllegalArgumentException("algorithm can't be null");
		this.algorithm = alg;
		this.definition = alg.newDefinition();
	}
	
	/**
	 * Returns the algorithm of this sorting instance.
	 * @return the algorithm of this sorting instance.
	 */
	public final Algorithm getAlgorithm() {
		return algorithm;
	}
	
	@Override
	public int[] sort(int[] arr) {
		synchronized (this) {
			return sort(definition, arr);
		}
	}
	
	// Static access
	
	/**
	 * Sorts the array using the given algorithm type.
	 * @param alg the algorithm to use to sort.
	 * @param arr the array to sort.
	 * @return the sorted instance array.
	 */
	public static int[] sort(Algorithm alg, int[] arr) {
		if (alg == null)
			return null;
		return sort(alg.newDefinition(), arr);
	}
	
	private static int[] sort(AlgorithmDefinition def, int[] arr) {
		if (arr == null || arr.length <= 1)
			return null;
		return def.sort(arr);
	}
	
	/**
	 * Algorithm set for this sorting collection.
	 * @author Odell
	 */
	public enum Algorithm {
		
		/**
		 * {@link RadixSort}
		 */
		RADIX (new AlgorithmAccessor() {
			public AlgorithmDefinition newDefinition() {
				return new RadixSort();
			}
		});
		
		private final AlgorithmAccessor accessor;
		
		Algorithm(AlgorithmAccessor accessor) {
			this.accessor = accessor;
		}
		
		AlgorithmDefinition newDefinition() {
			return accessor.newDefinition();
		}
		
		private interface AlgorithmAccessor {
			AlgorithmDefinition newDefinition();
		}
		
	}
	
}