package com.overload.algorithms.sort;

/**
 * A non-comparative lexicographical sorting algorithm boasting O(k*n) performance.
 * @author Odell
 */
class RadixSort implements AlgorithmDefinition {
	
	@Override
	public int[] sort(final int[] arr) {
		int[] input = arr;
		int[] temp = new int[arr.length];
		final int[] bitLengths = new int[] { 8, 8, 8, 7, 1 };
		
		for (int b = 0, exp = 0; b < bitLengths.length; exp += bitLengths[b], b++) {
			final int bitlength = bitLengths[b];
			final int radix = 1 << bitlength;
			final int mask = radix - 1;
			// stores just the count of items in the buckets
			final int[] buckets = new int[radix];
			// count how many items go in each bucket
			for (int i = input.length - 1; i >= 0; i--) {
				int bIndex = input[i] >>> exp & mask;
				if (bitlength == 1) // takes care of signed integers
					bIndex ^= mask;
				buckets[bIndex]++;
			}
			// for each bucket, calculate what index immediately follows
			// where it's items are to be copied to the temp array
			for (int i = 1; i < buckets.length; i++) {
				buckets[i] += buckets[i - 1];
			}
			// using each bucket, distribute the input items into the temp array
			for (int i = input.length - 1; i >= 0; i--) {
				int bIndex = input[i] >>> exp & mask;
				if (bitlength == 1) // takes care of signed integers
					bIndex ^= mask;
				temp[--buckets[bIndex]] = input[i];
			}
			// swap the arrays
			final int[] swap = input;
			input = temp;
			temp = swap;
		}
		// ensures the input array contains the sorted data
		if (arr == temp) {
			System.arraycopy(temp, 0, arr, 0, temp.length);
		}
		return arr;
	}
	
	/*
	public static void main(String[] args) {
		int size = 1000000;
		final int[] rand = Random.nextInts(size, -size, size);
		
		BenchmarkTask radixTask = new BenchmarkTask() {

			int[] input;
			
			@Override
			public int getIterationCount() {
				return 1;
			}

			@Override
			public void prepare() {
				input = rand.clone();
			}

			@Override
			public void execute() {
				//System.out.println(Arrays.toString(input));
				Sorting.sort(Algorithm.RADIX, input);
				//System.out.println(Arrays.toString(input));
				//System.out.println();
			}
			
		};
		BenchmarkTask qSortTask = new BenchmarkTask() {

			int[] input;
			
			@Override
			public int getIterationCount() {
				return 1;
			}

			@Override
			public void prepare() {
				input = rand.clone();
			}

			@Override
			public void execute() {
				//System.out.println(Arrays.toString(input));
				Arrays.sort(input);
				//System.out.println(Arrays.toString(input));
				//System.out.println();
			}
			
		};
		Map<BenchmarkTask, Timestamp> times = Benchmarking.benchmark(radixTask, qSortTask);
		Timestamp radixStamp = times.get(radixTask);
		System.out.println(radixStamp.getMilliseconds());
		Timestamp qStamp = times.get(qSortTask);
		System.out.println(qStamp.getMilliseconds());
	}
	*/
	
}