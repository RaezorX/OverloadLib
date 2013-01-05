package com.overload.algorithms.pathfinding;

import java.util.List;

import com.overload.loc.Locatable;

/**
 * A pathfinding algorithm model.
 * @author Odell
 */
interface AlgorithmDefinition extends AlgorithmSettings {
	
	/**
	 * Finds a path between the given start and end locations.
	 * @param start start location
	 * @param end destination location
	 * @return a path from start to end, or null if path not found.
	 */
	public List<Locatable> findPath(final Locatable start, final Locatable end);
	
}