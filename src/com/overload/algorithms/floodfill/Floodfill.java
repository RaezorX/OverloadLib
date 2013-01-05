package com.overload.algorithms.floodfill;

import com.overload.loc.Locatable;

/**
 * 2D grid-based flood fill algorithm collection.
 * This flood fill class is NOT thread-safe.
 * @author Odell
 */
public class Floodfill implements AlgorithmSettings {
	
	protected final Algorithm algorithm;
	protected final AlgorithmDefinition definition;
	
	/**
	 * Creates a flood fill provider given an algorithm type.
	 * @param alg the algorithm implementation to use.
	 * @param flags the collision flags for hit detection.
	 */
	public Floodfill(final Algorithm alg, final Flags flags) {
		this.algorithm = alg;
		this.definition = alg.newDefinition();
		setFlags(flags);
	}
	
	/**
	 * @return the algorithm of this flood fill.
	 */
	public final Algorithm getAlgorithm() {
		return algorithm;
	}
	
	@Override
	public void setEight(boolean eight) {
		this.definition.setEight(eight);
	}
	
	@Override
	public void setFlags(final Flags flags) {
		this.definition.setFlags(flags);
	}
	
	/**
	 * This algorithm uses a callback to return locations it's found.<br>
	 * This allows it to run way faster.<br>
	 * Don't forget that the flags must return true for a location that the fill result has returned!
	 * @param start
	 * 			The starting location.
	 */
	public void fill(final Locatable start, final FillResult fr) {
		this.definition.fill(start, fr);
	}
	
	/**
	 * A callback to the user for flood fill operations.
	 * @author Odell
	 */
	public interface FillResult {
		
		/**
		 * When flood fill finds a location for the end user.<br>
		 * After this call finishes, subsequent calls to the flags interface should return this location as blocked.
		 * @param loc the location found.
		 */
		public void locationFound(final Locatable loc);
		
	}
	
	/**
	 * A collision flag provider.
	 * @author Odell
	 */
	public interface Flags {
		/**
		 * Determines whether loc is blocked or not.<br>
		 * @param loc a locatable which can be tested.
		 * @return whether the given locatable is blocked or not.
		 */
		public boolean blocked(final Locatable loc);
	}
	
	/**
	 * Algorithm set for this raster-based flood fill collection.
	 * @author Odell
	 */
	public enum Algorithm {
		
		/**
		 * {@link QueueImpl}
		 */
		QUEUE (new AlgorithmAccessor() {
			public AlgorithmDefinition newDefinition() {
				return new QueueImpl();
			}
		}),
		/**
		 * {@link JPSImpl}
		 */
		LINEAR (new AlgorithmAccessor() {
			public AlgorithmDefinition newDefinition() {
				return new LinearImpl();
			}
		});
		
		private final AlgorithmAccessor accessor;
		
		Algorithm(AlgorithmAccessor accessor) {
			this.accessor = accessor;
		}
		
		AlgorithmDefinition newDefinition() {
			return accessor.newDefinition();
		}
		
		private interface AlgorithmAccessor {
			AlgorithmDefinition newDefinition();
		}
		
	}
	
}