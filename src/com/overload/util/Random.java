package com.overload.util;

/**
 * Provides access to a variety of random number generating functions.
 * @author Odell
 */
public class Random {

	private static java.util.Random rand = new Random().getImpl();
	
	protected Random() {}
	
	java.util.Random getImpl() {
		return new java.util.Random();
	}
	
	/**
	 * Generates a random number between 0 and the given max (exclusive).
	 * @param max
	 * 		the exclusive, maximum limit.
	 * @return a random number between 0 and the given max (exclusive).
	 */
	public static int nextInt(int max) {
		return rand.nextInt(max);
	}
	
	/**
	 * Generates an inclusive/exclusive random number between the given values.
	 * @param min
	 * 		the inclusive, minimum limit.
	 * @param max
	 * 		the exclusive, maximum limit.
	 * @return a random number between the given values.
	 */
	public static int nextInt(int min, int max) {
		return Math.min(min, max) + rand.nextInt(Math.abs(max - min));
	}
	
	public static float nextFloat(float max) {
		return max * rand.nextFloat();
	}
	
	public static float nextFloat(float min, float max) {
		return Math.min(min, max) + nextFloat(Math.abs(max - min));
	}
	
	public static double nextDouble(double max) {
		return max * rand.nextDouble();
	}
	
	public static double nextDouble(double min, double max) {
		return Math.min(min, max) + nextDouble(Math.abs(max - min));
	}
	
	public static int[] nextInts(int n, int max) {
		final int[] arr = new int[Math.max(0, n)];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = nextInt(max);
		}
		return arr;
	}
	
	public static int[] nextInts(int n, int min, int max) {
		final int[] arr = new int[Math.max(0, n)];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = nextInt(min, max);
		}
		return arr;
	}
	
}