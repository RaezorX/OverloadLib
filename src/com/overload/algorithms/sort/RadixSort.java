package com.overload.algorithms.sort;

/**
 * A non-comparative lexicographical sorting algorithm boasting O(k*n) performance.
 * @author Odell
 */
class RadixSort implements AlgorithmDefinition {
	
	@Override
	public int[] sort(int[] input) {
		int[] temp1 = input;
		int[] temp2 = new int[input.length];
		final int bitlength = 8;
		final int radix = 1 << bitlength;
		final int mask = radix - 1;
		
		for (int exp = 0; exp < Integer.SIZE; exp += bitlength) {
			// stores just the count of items in the buckets
			int[] buckets = new int[radix];
			// count how many items go in each bucket
			for (int i = temp1.length - 1; i >= 0; i--) {
				buckets[(temp1[i] >>> exp) & mask]++;
			}
			// for each bucket, calculate what index immediately follows
			// where it's items are to be copied to the temp array
			for (int i = 1; i < buckets.length; i++) {
				buckets[i] += buckets[i - 1];
			}
			// using each bucket, distribute the input items into the temp array
			for (int i = temp1.length - 1; i >= 0; i--) {
				temp2[--buckets[(temp1[i] >>> exp) & mask]] = temp1[i];
			}
			// swap the output arrays
			int[] swap = temp1;
			temp1 = temp2;
			temp2 = swap;
		}
		// ensures the input array contains the sorted data
		if (input == temp2) {
			System.arraycopy(temp1, 0, input, 0, temp1.length);
		}
		return input;
	}
	
	/*
	public static void main(String[] args) {
		int size = 10000000;
		final int[] rand = Random.nextInts(size, size);
		
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