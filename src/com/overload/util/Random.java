package com.overload.util;

/**
 * Provides access to a variety of random number generating functions.
 * @author Odell
 */
public class Random {

	private static java.util.Random rand = new java.util.Random();
	
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
	 * Generates a random number between 0 and the given max (inclusive).
	 * @param max the inclusive, maximum limit.
	 * @return a random number between 0 and the given max (inclusive).
	 */
	public static int nextIInt(int max) {
		return nextInt(max + 1);
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
	
	/**
	 * Generates an inclusive/inclusive random number between the given values.
	 * @param min
	 * 		the inclusive, minimum limit.
	 * @param max
	 * 		the inclusive, maximum limit.
	 * @return a random number between the given values.
	 */
	public static int nextIInt(int min, int max) {
		return nextInt(min, max + 1);
	}
	
	public static float nextFloat(float max) {
		return max * rand.nextFloat();
	}
	
	public static float nextIFloat(float max) {
		return nextFloat(Math.nextUp(max));
	}
	
	public static float nextFloat(float min, float max) {
		return Math.min(min, max) + nextFloat(Math.abs(max - min));
	}
	
	public static float nextIFloat(float min, float max) {
		return nextFloat(min, Math.nextUp(max));
	}
	
	public static double nextDouble(double max) {
		return max * rand.nextDouble();
	}
	
	public static double nextIDouble(double max) {
		return nextDouble(Math.nextUp(max));
	}
	
	public static double nextDouble(double min, double max) {
		return Math.min(min, max) + nextDouble(Math.abs(max - min));
	}
	
	public static double nextIDouble(double min, double max) {
		return nextDouble(min, Math.nextUp(max));
	}
	
}