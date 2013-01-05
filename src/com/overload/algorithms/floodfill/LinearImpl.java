package com.overload.algorithms.floodfill;

import java.util.LinkedList;

import com.overload.algorithms.floodfill.Floodfill.FillResult;
import com.overload.algorithms.floodfill.Floodfill.Flags;
import com.overload.loc.Locatable;
import com.overload.loc.Node;

/**
 * A linear (horizontal) flood fill implementation.
 * @author Odell
 */
class LinearImpl implements AlgorithmDefinition {
	
	private Flags flags;
	private boolean eight;
	
	@Override
	public void setEight(boolean eight) {
		this.eight = eight;
	}

	@Override
	public void setFlags(Flags flags) {
		this.flags = flags;
	}

	@Override
	public void fill(final Locatable start, final FillResult fr) {
		final LinkedList<Node> q = new LinkedList<Node>();
		Node curr = new Node(start);
		do {
			// search west
			final int startX = curr.getX();
			Node active = curr.clone();
			while (!flags.blocked(active.shift(-1, 0)));
			
			// start flooding and searching north and south
			boolean north = true, south = true;
			while (active.shift(1, 0).getX() < startX) {
				// set
				fr.locationFound(active.clone());
				
				if (flags.blocked(active.shift(0, -1))) { // validate above row
					if (eight) {
						if (!flags.blocked(active.shift(-1, 0))) { // goto left
							if (north) {
								q.offer(active.clone());
							}
						}
						active.shift(2, 0); // goto right
						if (!(north = flags.blocked(active))) {
							q.offer(active.clone());
						}
						active.shift(-1, 0); // goto middle
					} else {
						north = true;
					}
				} else {
					if (north) {
                        q.offer(active.clone());
                        north = false;
                    }
				}
				if (flags.blocked(active.shift(0, 2))) { // validate below row
					if (eight) {
						if (!flags.blocked(active.shift(-1, 0))) { // goto left
							if (south) {
								q.offer(active.clone());
							}
						}
						active.shift(2, 0); // goto right
						if (!(south = flags.blocked(active))) {
							q.offer(active.clone());
						}
						active.shift(-1, 0); // goto middle
					} else {
						south = true;
					}
				} else {
					if (south) {
                        q.offer(active.clone());
                        south = false;
                    }
				}
				active.shift(0, -1);
			}
			for (; !flags.blocked(active); active.shift(1, 0)) { // contents are copied from while loop
				// set
				fr.locationFound(active.clone());
				
				if (flags.blocked(active.shift(0, -1))) { // validate above row
					if (eight) {
						if (!flags.blocked(active.shift(-1, 0))) { // goto left
							if (north) {
								q.offer(active.clone());
							}
						}
						active.shift(2, 0); // goto right
						if (!(north = flags.blocked(active))) {
							q.offer(active.clone());
						}
						active.shift(-1, 0); // goto middle
					} else {
						north = true;
					}
				} else {
					if (north) {
                        q.offer(active.clone());
                        north = false;
                    }
				}
				if (flags.blocked(active.shift(0, 2))) { // validate below row
					if (eight) {
						if (!flags.blocked(active.shift(-1, 0))) { // goto left
							if (south) {
								q.offer(active.clone());
							}
						}
						active.shift(2, 0); // goto right
						if (!(south = flags.blocked(active))) {
							q.offer(active.clone());
						}
						active.shift(-1, 0); // goto middle
					} else {
						south = true;
					}
				} else {
					if (south) {
                        q.offer(active.clone());
                        south = false;
                    }
				}
				active.shift(0, -1);
			}
		} while ((curr = q.poll()) != null);
	}
	
}
