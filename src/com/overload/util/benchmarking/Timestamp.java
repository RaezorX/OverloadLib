package com.overload.util.benchmarking;

/**
 * @author Odell
 */
public class Timestamp {
	
	private final long nanos;
	
	public Timestamp(long nanos) {
		this.nanos = nanos;
	}
	
	public long getNanoseconds() {
		return nanos;
	}
	
	public double getMilliseconds() {
		return (double) nanos / 1000000.0D;
	}
	
}