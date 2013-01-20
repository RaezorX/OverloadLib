package com.overload.util.benchmarking;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides highly accurate benchmarking.
 * @author Odell
 */
public class Benchmarking {
	
	public static Map<BenchmarkTask, Timestamp> benchmark(final BenchmarkTask... tasks) {
		final HashMap<BenchmarkTask, Timestamp> timeMap = new HashMap<BenchmarkTask, Timestamp>();
		if (tasks != null && tasks.length > 0) {
			for (int i = 0; i < tasks.length; i++) {
				final BenchmarkTask task = tasks[i];
				if (task == null)
					continue;
				int iterationCount = task.getIterationCount();
				long total = 0, start;
				
				task.prepare();
				task.execute(); // JVM warm up
				for (int c = 0; c < iterationCount; c++) {
					task.prepare();
					// prepare runtime
					System.gc();
					System.runFinalization();
					// execute task
					start = System.nanoTime();
					task.execute();
					total += System.nanoTime() - start;
				}
				timeMap.put(task, new Timestamp((long) Math.ceil((double) total / (double) iterationCount)));
			}
		}
		return timeMap;
	}
	
}