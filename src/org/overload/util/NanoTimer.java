package org.overload.util;

/**
 * A high precision timer class.
 * @author Odell
 */
public class NanoTimer {
	
	private Long start = null, target = null, pause = null;
	
	private NanoTimer() {
		reset();
	}
	
	private NanoTimer(final long elapseTarget) {
		target = elapseTarget;
		reset();
	}
	
	/**
	 * @return whether this timer is active.
	 */
	public boolean isActive() {
		return !isPaused() && (target == null || getElapsedNanos() < target);
	}
	
	public boolean isPaused() {
		return pause != null;
	}
	
	public void appendNanos(final long nanos) {
		start += nanos;
	}
	
	public void appendMillis(final long millis) {
		appendNanos(millis * 1000000L);
	}
	
	public void reset() {
		start = System.nanoTime();
	}
	
	public void pause() {
		if (pause == null) {
			pause = System.nanoTime();
		}
	}
	
	public void resume() {
		if (pause != null) {
			appendNanos(System.nanoTime() - pause);
			pause = null;
		}
	}
	
	public long getElapsedNanos() {
		return System.nanoTime() - start;
	}
	
	public double getElapsedMillis() {
		return ((double) getElapsedNanos()) / 1000000.D;
	}
	
	public static NanoTimer newInstance() {
		return new NanoTimer();
	}
	
	public static NanoTimer newInstanceNano(final long elapseTarget) {
		return new NanoTimer(elapseTarget);
	}
	
	public static NanoTimer newInstanceMillis(final long elapseTarget) {
		return new NanoTimer(elapseTarget * 1000000L);
	}
	
}