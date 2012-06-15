package org.overload.algo.floodfill;

import org.overload.loc.Locatable;

/**
 * A callback to the user for flood fill operations.
 * @author Odell
 */
public interface FillResult {
	
	/**
	 * When flood fill finds a location for the end user.
	 * @param loc
	 * 			The location found.
	 */
	public void locationFound(final Locatable loc);
	
}