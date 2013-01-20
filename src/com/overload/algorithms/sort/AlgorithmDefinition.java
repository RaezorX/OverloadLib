package com.overload.algorithms.sort;

/**
 * A sorting algorithm model.
 * @author Odell
 */
interface AlgorithmDefinition {
	
	/**
	 * Sorts the array.
	 * @param arr the array to sort.
	 * @return the sorted instance array.
	 * @throws UnsupportedOperationException if the data type is not supported.
	 */
	public int[] sort(int[] arr);
	
}