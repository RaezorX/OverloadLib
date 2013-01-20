package com.overload.util;

/**
 * A high precision timer class.
 * @author Odell
 */
public class NanoTimer {
	
	private Long start = null, timeout = null, pause = null;
	
	private NanoTimer() {
		reset();
	}
	
	private NanoTimer(final long elapseTimeout) {
		timeout = elapseTimeout;
		reset();
	}
	
	/**
	 * @return whether this timer hasn't passed its elapsed timeout.
	 */
	public boolean isActive() {
		return !isPaused() && (timeout == null || getElapsedNanos() < timeout);
	}
	
	/**
	 * @return if this timer has been paused.
	 */
	public boolean isPaused() {
		return pause != null;
	}
	
	/**
	 * Resets this timer so the elapsed time starts over at 0.
	 */
	public void reset() {
		start = System.nanoTime();
		if (isPaused())
			pause = start;
	}
	
	/**
	 * Pauses this timer causing the elapsed time to stop.
	 */
	public void pause() {
		if (pause == null) {
			pause = System.nanoTime();
		}
	}
	
	/**
	 * Resumes the timer, elapsed time will continue to increase.
	 */
	public void resume() {
		if (pause != null) {
			start += System.nanoTime() - pause;
			pause = null;
		}
	}
	
	/**
	 * @return the count of nanoseconds that have elapsed since the timer start or a reset.
	 */
	public long getElapsedNanos() {
		return (isPaused() ? pause : System.nanoTime()) - start;
	}
	
	/**
	 * @return elapsed milliseconds in double precision.
	 */
	public double getElapsedMillis() {
		return ((double) getElapsedNanos()) / 1000000.0D;
	}
	
	/**
	 * @return a plain NanoTimer with no elapsed timeout.
	 */
	public static NanoTimer newInstance() {
		return new NanoTimer();
	}
	
	/**
	 * Creates a new NanoTimer with the given elapsed timeout in nanoseconds.
	 * @param elapseTimeout
	 * 		how many nanoseconds are to elapse before this timer is considered inactive.
	 * @return the new NanoTimer.
	 */
	public static NanoTimer newInstanceNano(final long elapseTimeout) {
		return new NanoTimer(elapseTimeout);
	}
	
	/**
	 * Creates a new NanoTimer with the given elapsed timeout in milliseconds.
	 * @param elapseTimeout
	 * 		how many milliseconds are to elapse before this timer is considered inactive.
	 * @return the new NanoTimer.
	 */
	public static NanoTimer newInstanceMillis(final long elapseTimeout) {
		return new NanoTimer(elapseTimeout * 1000000L);
	}
	
}