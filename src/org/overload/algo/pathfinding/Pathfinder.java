package org.overload.algo.pathfinding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.overload.algo.Flags;
import org.overload.loc.Locatable;
import org.overload.loc.PLocatable;

/**
 * 2D raster-based pathfinding algorithm collection.
 * @author Odell
 */
public class Pathfinder {
	
	/**
	 * Algorithm set for this raster-based pathfinding collection.
	 * @author Odell
	 */
	public enum Algorithm {
		
		ASTAR_4,
		ASTAR_8;
		//DIJKSTRA_4, 
		//DIJKSTRA_8;
		
		/**
		 * @return a simple description of this algorithm.
		 */
		public String getDescription() {
			return toString();
		}
		
		public String toString() {
			switch (this) {
				case ASTAR_4:
					return "4-way AStar";
				case ASTAR_8:
					return "8-way AStar";
					/*
				case DIJKSTRA_4:
					return "4-way Dijkstra";
				case DIJKSTRA_8:
					return "8-way Dijkstra";
					*/
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
	
	private Flags<PLocatable> flags = null;
	
	/**
	 * Creates a pathfinding provider given an algorithm type.
	 * @param alg
	 * 		the algorithm implementation to use.
	 */
	public Pathfinder(final Algorithm alg) {
		this.algorithm = alg;
		switch (algorithm) {
			case ASTAR_4:
				this.def = new AStarAlgorithm(false);
				break;
			case ASTAR_8:
				this.def = new AStarAlgorithm(true);
				break;
				/*
			case DIJKSTRA_4:
				this.def = new DijkstraAlgorithm(false);
				break;
			case DIJKSTRA_8:
				this.def = new DijkstraAlgorithm(true);
				break;
				*/
			default:
				this.def = null;
		}
	}
	
	/**
	 * Creates a pathfinding provider given an algorithm type and collision flags.
	 * @param alg
	 * 		the algorithm implementation to use.
	 * @param flags
	 * 		the collision flags for hit detection.
	 */
	public Pathfinder(final Algorithm alg, final Flags<PLocatable> flags) {
		this(alg);
		setFlags(flags);
	}
	
	/**
	 * @return the algorithm of this pathfinding.
	 */
	public final Algorithm getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Assigns the collision flags of this flood fill.
	 * @param flags
	 * 		the collision flags for the algorithm implementation.
	 */
	public final void setFlags(final Flags<PLocatable> flags) {
		this.flags = flags;
	}
	
	/**
	 * Finds a path from the start to the end.
	 * @param start
	 * 		the starting location.
	 * @param end
	 * 		the destination location.
	 * @return a valid path to the destination, otherwise null if there was no path.
	 */
	public synchronized LinkedList<Locatable> findPath(final Locatable start, final Locatable end) {
		return def.findPath(flags, start, end);
	}
	
	private interface AlgorithmDef {
		LinkedList<Locatable> findPath(final Flags<PLocatable> flags, final Locatable start, final Locatable end);
	}
	
	/**
	 * Advanced Pathfinding algorithm first described by </br>
	 * Peter Hart, Nils Nilsson, and Bertram Raphael in 1968.</br>
	 * @author Odell
	 */
	private class AStarAlgorithm implements AlgorithmDef {
		
		private final boolean eight;
		
		private PriorityQueue<ANode> open;
		private HashSet<ANode> closed;
		private HashMap<ANode, ANode> parentMap;
		private ANode curr;
		private Locatable dest;
		
		public AStarAlgorithm(boolean eight) {
			this.eight = eight;
			open = new PriorityQueue<ANode>(10, new DoubleComparator<ANode>() {
				public double compareD(ANode n1, ANode n2) {
					return n1.getF() - n2.getF();
				}
			});
			closed = new HashSet<ANode>();
			parentMap = new HashMap<ANode, ANode>();
			curr = null;
			dest = null;
		}
		
		private ANode retrieveInstance(final PriorityQueue<ANode> pq, final ANode node) {
			final Iterator<ANode> nI = pq.iterator();
			while (nI.hasNext()) {
				final ANode n = nI.next();
				if (n != null && n.equals(node))
					return n;
			}
			return null;
		}
		
		@Override
		public LinkedList<Locatable> findPath(final Flags<PLocatable> flags, final Locatable start, final Locatable end) {
			try {
				NANO_ALL = NANO_BLOCKED = System.nanoTime();
				curr = new ANode(start);
				dest = end;
				do {
					if (curr.equals(end))
						return resolve(curr);
					closed.add(curr);
					for (final PNode n : curr.getOpenAdjacents(flags, eight)) {
						if (closed.contains(n))
							continue;
						final ANode node = (ANode) n;
						if (!open.contains(node)) {
							node.setParent(curr);
							open.offer(node);
						} else if ((curr.getG() + curr.getMoveCost(node)) < node.getG()) { // G score of node with current node as it's parent
							final ANode instanceNode = retrieveInstance(open, node);
							if (instanceNode != null)
								instanceNode.setParent(curr);
						}
					}
				} while ((curr = open.poll()) != null);
				return null;
			} finally {
				open.clear();
				closed.clear();
				curr = null;
				dest = null;
				if (DEBUG > 0) {
					System.out.println("Total elapsed time: " + (((double)(System.nanoTime() - NANO_ALL)) / 1000000.0D) + " milliseconds.");
					System.out.println("Elapsed time: " + (((double)(System.nanoTime() - NANO_BLOCKED)) / 1000000.0D) + " milliseconds.");
				}
			}
		}
		
		private LinkedList<Locatable> resolve(ANode target) {
			if (target == null)
				return null;
			final LinkedList<Locatable> path = new LinkedList<Locatable>();
			do {
				path.addFirst(target);
			} while ((target = target.getParent()) != null);
			return path;
		}
		
		/**
		 * A node used for AStar.
		 * @author Odell
		 */
		private class ANode extends PNode {
			
			/**
			 * the estimated (heuristic) cost to reach the destination from here.
			 */
			private java.lang.Double h;
			/**
			 * the exact cost to reach this node from the starting node.
			 */
			private java.lang.Double g;
			/**
			 * As the algorithm runs the F value of a node tells us how expensive we think it will be to reach our goal by way of that node.
			 */
			private java.lang.Double f;
			
			public ANode(final int x, final int y) {
				super(x, y);
				h = g = f = null;
			}
			
			public ANode(final Locatable n) {
				this(n.getX(), n.getY());
			}
			
			public ANode getParent() {
				return parentMap.get(this);
			}
			
			/**
			 * @return the previous parent node.
			 */
			protected ANode setParent(final ANode node) {
				g = null;
				return parentMap.put(this, node);
			}
			
			protected double getF() {
				if (g == null || h == null || f == null) {
					f = getG() + getH();
				}
				return f;
			}
			
			protected double getG() {
				if (g == null) {
					final ANode parent = getParent();
					g = parent != null ? parent.getG() + parent.getMoveCost(this) : 0.0D;
				}
				return g;
			}
			
			private double getMoveCost(final ANode node) {
				if (node == null)
					return 0;
				return (x == node.x || y == node.y) ? STRAIGHT : DIAGONAL;
			}
			
			protected double getH() {
				if (h == null) {
					if (eight) {
						h = Heuristics.diagonal(this, dest);
					} else {
						h = Heuristics.manhattan(this, dest);
					}
					if (DEBUG > 1) {
						System.out.println(toString());
						System.out.println("F: " + getF() + ", G: " + getG() + ", H: " + h);
						System.out.println();
					}
				}
				return h;
			}

			@Override
			protected PNode[] getAdjacents(boolean eight) {
				return eight ? new ANode[] {
					new ANode(x, y - 1), new ANode(x + 1, y - 1), new ANode(x + 1, y), new ANode(x + 1, y + 1),
					new ANode(x, y + 1), new ANode(x - 1, y + 1), new ANode(x - 1, y), new ANode(x - 1, y - 1)
				} : new ANode[] {
					new ANode(x, y - 1), new ANode(x + 1, y), new ANode(x, y + 1), new ANode(x - 1, y)
				};
			}
			
			@Override
			public String toString() {
				return super.toString();
			}
			
		}
		
	}
	/*
	private class DijkstraAlgorithm implements AlgorithmDef {
		
		private final boolean eight;
		
		public DijkstraAlgorithm(boolean eight) {
			this.eight = eight;
		}

		@Override
		public LinkedList<Locatable> findPath(final Flags flags, final Locatable start, final Locatable end) {
			return null;
		}
		
		private class DNode extends Node {
			
			public DNode(final int x, final int y) {
				super(x, y);
			}
			
			public DNode(final Node n) {
				this(n.x, n.y);
			}

			@Override
			protected Node[] getAdjacents(boolean eight) {
				return eight ? new DNode[] {
					new DNode(x, y - 1), new DNode(x + 1, y - 1), new DNode(x + 1, y), new DNode(x + 1, y + 1),
					new DNode(x, y + 1), new DNode(x - 1, y + 1), new DNode(x - 1, y), new DNode(x - 1, y - 1)
				} : new DNode[] {
					new DNode(x, y - 1), new DNode(x + 1, y), new DNode(x, y + 1), new DNode(x - 1, y)
				};
			}
			
		}
		
	}
	*/
	
	static class Heuristics {
		
		/**
		 * Finds the Manhattan distance between two locatables.
		 *
		 * @param start The starting locatable.
		 * @param dest The destination locatable.
		 * @return The Manhattan distance between the two locatables.
		 */
		public static double manhattan(final Locatable start, final Locatable dest) {
			return Math.abs(start.getX() - dest.getX()) + Math.abs(start.getY() - dest.getY());
		}
		
		/**
		 * Finds the diagonal distance respectively to the grid.
		 * 
		 * @param start The starting locatable.
		 * @param dest The destination locatable.
		 * @return The diagonal distance between the two locatables.
		 */
		public static double diagonal(final Locatable start, final Locatable dest) {
			final int xDiff = Math.abs(dest.getX() - start.getX()), yDiff = Math.abs(dest.getY() - start.getY());
			final long diagonal = Math.min(xDiff, yDiff);
			final long manhattan = xDiff + yDiff;
			return (((double) diagonal) * PNode.DIAGONAL) + (double)(manhattan - (diagonal << 1));
		}
		
		/**
		 * Finds the diagonal distance between two locatables.
		 * 
		 * @param start The starting locatable.
		 * @param dest The destination locatable.
		 * @return The diagonal distance between the two locatables.
		 */
		public static double euclidean(final Locatable start, final Locatable dest) {
			final long xDist = dest.getX() - start.getX(), yDist = dest.getY() - start.getY();
			return Math.sqrt((xDist * xDist) + (yDist * yDist));
		}
		
	}
	
	private abstract class DoubleComparator<E> implements Comparator<E> {
		
		public abstract double compareD(E arg0, E arg1);
		
		@Override
		public int compare(E arg0, E arg1) {
			final double cD = compareD(arg0, arg1);
			if (cD < 0) return -1;
			if (cD > 0) return 1;
			return 0;
		}
		
	}
	
}