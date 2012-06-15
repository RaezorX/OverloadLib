package org.overload.algo.floodfill;

import java.util.LinkedList;

import org.overload.algo.Flags;
import org.overload.loc.Locatable;
import org.overload.loc.Node;

public class Floodfill3D {
	
	public enum Algorithm {
		
		QUEUE_4, 
		QUEUE_8,
		LINEAR_4, 
		LINEAR_8;
		
		public String getDescription() {
			return toString();
		}
		
		public String toString() {
			switch (this) {
				case QUEUE_4:
					return "4-way queue";
				case QUEUE_8:
					return "8-way queue";
				case LINEAR_4:
					return "4-way linear";
				case LINEAR_8:
					return "8-way linear";
				default:
					return null;
			}
		}
		
	}
	
	/**
	 * DEBUG = 0 -> no debuggging
	 * DEBUG = 1 -> elapsed debugging
	 * DEBUG = 2 -> node debugging
	 */
	static int DEBUG = 1;
	static long NANO_ALL, NANO_BLOCKED;
	
	protected final Algorithm algorithm;
	protected final AlgorithmDef def;
	
	private Flags<Locatable> flags = null;
	
	public Floodfill3D(final Algorithm alg) {
		this.algorithm = alg;
		switch (algorithm) {
			case QUEUE_4:
				this.def = new QueueAlgorithm(false);
				break;
			case QUEUE_8:
				this.def = new QueueAlgorithm(true);
				break;
			case LINEAR_4:
				this.def = new LinearAlgorithm(false);
				break;
			case LINEAR_8:
				this.def = new LinearAlgorithm(true);
				break;
			default:
				this.def = null;
		}
	}
	
	public Floodfill3D(final Algorithm alg, final Flags<Locatable> flags) {
		this(alg);
		setFlags(flags);
	}
	
	public final Algorithm getAlgorithm() {
		return algorithm;
	}
	
	public final void setFlags(final Flags<Locatable> flags) {
		this.flags = flags;
	}
	
	/**
	 * This algorithm uses a callback to return locations it's found.<br>
	 * This allows it to run way faster.<br>
	 * Don't forget that the flags must return true for a location that the fill result has returned!
	 * @param start
	 * 			The starting location.
	 */
	public void fill(final Locatable start, final FillResult fr) {
		def.fill(flags, new Node(start), fr);
	}
	
	private interface AlgorithmDef {
		void fill(final Flags<Locatable> flags, final Node start, final FillResult fr);
	}
	
	/**
	 * A queue (expanding) flood fill implementation.
	 * @author Odell
	 */
	private class QueueAlgorithm implements AlgorithmDef {
		
		private final boolean eight;
		
		public QueueAlgorithm(boolean eight) {
			this.eight = eight;
		}

		@Override
		public void fill(final Flags<Locatable> flags, final Node start, final FillResult fr) {
			final LinkedList<Node> q = new LinkedList<Node>();
			Node curr = start;
			do {
				if (flags.blocked(curr))
					continue;
				fr.locationFound(curr);
				final Node[] expand = eight ? new Node[] {
					curr.derive(0, -1), curr.derive(1, 0), curr.derive(0, 1), curr.derive(-1, 0),
					curr.derive(1, -1), curr.derive(1, 1), curr.derive(-1, 1), curr.derive(-1, -1),
				} : new Node[] {
					curr.derive(0, -1), curr.derive(1, 0), curr.derive(0, 1), curr.derive(-1, 0)
				};
				for (final Node temp : expand) {
					if (!flags.blocked(temp))
						q.offer(temp);
				}
			} while ((curr = q.poll()) != null);
		}
		
	}
	
	/**
	 * A linear (horizontal) flood fill implementation.
	 * @author Odell
	 */
	private class LinearAlgorithm implements AlgorithmDef {
		
		private final boolean eight;
		
		public LinearAlgorithm(boolean eight) {
			this.eight = eight;
		}

		@Override
		public void fill(final Flags<Locatable> flags, final Node start, final FillResult fr) {
			final LinkedList<Node> q = new LinkedList<Node>();
			Node curr = start;
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
							active.shift(-1, 0); // goto center
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
							active.shift(-1, 0); // goto center
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
							active.shift(-1, 0); // goto center
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
							active.shift(-1, 0); // goto center
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
	
}