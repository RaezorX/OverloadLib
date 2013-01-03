package org.overload.algorithms.floodfill;

import org.overload.algorithms.floodfill.Floodfill.FillResult;
import org.overload.loc.Locatable;

/**
 * A flood fill algorithm model.
 * @author Odell
 */
interface AlgorithmDefinition extends AlgorithmSettings {
	
	/**
	 * Flood fills from the given starting location, passing results to the given FillResult.
	 * @param start start location
	 * @param fr the FillResult callback
	 */
	public void fill(final Locatable start, final FillResult fr);
	
}