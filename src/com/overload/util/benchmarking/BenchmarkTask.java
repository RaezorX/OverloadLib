package com.overload.util.benchmarking;

/**
 * @author Odell
 */
public interface BenchmarkTask {
	/**
	 * Returns how many calls to the function are to be made to benchmark this task.
	 * @return how many calls to the function are to be made to benchmark this task.
	 */
	public int getIterationCount();
	/**
	 * Prepares this task to be executed.<br>
	 * This function is not included in the benchmarking.
	 */
	public void prepare();
	/**
	 * Executes this task.<br>
	 * This function should be included in the benchmarking.
	 */
	public void execute();
}