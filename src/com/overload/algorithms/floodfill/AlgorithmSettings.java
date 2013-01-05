package com.overload.algorithms.floodfill;

import com.overload.algorithms.floodfill.Floodfill.Flags;

/**
 * Defines set methods for algorithm definitions.
 * @author Odell
 */
interface AlgorithmSettings {
	
	/**
	 * Sets whether the implementation allows eight way flood fill.<br>
	 * Some algorithms may not support both four and eight way flood fill, rendering this method useless.
	 */
	public void setEight(boolean eight);
	
	/**
	 * Sets the collision flags for this flood fill.
	 * @param flags the collision flags for the algorithm implementation.
	 */
	public void setFlags(final Flags flags);
	
}