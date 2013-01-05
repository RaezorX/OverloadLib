package com.overload.loc;

/**
 * A class based wrapper for the Locatable3D interface.
 * @author Odell
 */
public class Node3D implements Locatable3D {

	protected int x, y, z;
	
	/**
	 * Creates a new Node3D object with the given x,y,z coordinates.
	 * @param x
	 * 		the X position of this Node3D.
	 * @param y
	 * 		the Y position of this Node3D.
	 * @param z
	 * 		the Z position of this Node3D.
	 */
	public Node3D(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a new Node3D object by cloning the given Locatable3D.
	 * @param loc
	 * 		the Locatable3D to use to create this Node3D.
	 */
	public Node3D(final Locatable3D loc) {
		this(loc.getX(), loc.getY(), loc.getZ());
	}
	
	/**
	 * Modifies the internal x,y,z positions of this Node3D.
	 * @param x
	 * 		the value to shift the X position of this Node3D.
	 * @param y
	 * 		the value to shift the Y position of this Node3D.
	 * @param z
	 * 		the value to shift the Z position of this Node3D.
	 * @return this Node3D.
	 */
	public Node3D shift(final int x, final int y, final int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Creates a copy of this Node3D shifted by the given values.
	 * @param x
	 * 		the value to shift the X position of this Node3D.
	 * @param y
	 * 		the value to shift the Y position of this Node3D.
	 * @param z
	 * 		the value to shift the Z position of this Node3D.
	 * @return a copy of this Node3D shifted.
	 */
	public Node3D derive(final int x, final int y, final int z) {
		return new Node3D(getX() + x, getY() + y, getZ() + z);
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int getZ() {
		return z;
	}
	
	/**
	 * Sets the X position of this Node3D.
	 * @param x
	 * 		the new X position of this Node3D.
	 */
	public void setX(final int x) {
		this.x = x;
	}
	
	/**
	 * Sets the Y position of this Node3D.
	 * @param y
	 * 		the new Y position of this Node3D.
	 */
	public void setY(final int y) {
		this.y = y;
	}
	
	/**
	 * Sets the Z position of this Node3D.
	 * @param z
	 * 		the new Z position of this Node3D.
	 */
	public void setZ(final int z) {
		this.z = z;
	}
	
	/**
	 * @return a Node object of this Node3D's x,y coordinates.
	 */
	public Node flatten() {
		return new Node(x, y);
	}
	
	@Override
	public Node3D clone() {
		return new Node3D(x, y, z);
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Locatable3D && ((Locatable3D) o).getX() == getX() && ((Locatable3D) o).getY() == getY() && ((Locatable3D) o).getZ() == getZ();
	}
	
	@Override
	public int hashCode() {
		int hash = 373;
		hash = 31 * hash + x;
		hash = 31 * hash + y;
		hash = 31 * hash + z;
		return hash;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("Node3D(").append(getX()).append(",").append(getY()).append(",").append(getZ()).append(")").toString();
	}
	
	/**
	 * A class based wrapper for the Locatable3D.Double interface.
	 * @author Odell
	 */
	public static class Double implements Locatable3D.Double {

		protected double x, y, z;
		
		/**
		 * Creates a new Node3D.Double object with the given x,y,z coordinates.
		 * @param x
		 * 		the X position of this Node3D.Double.
		 * @param y
		 * 		the Y position of this Node3D.Double.
		 * @param z
		 * 		the Z position of this Node3D.Double.
		 */
		public Double(final double x, final double y, final double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		/**
		 * Creates a new Node3D.Double object by cloning the given Locatable3D.Double.
		 * @param loc
		 * 		the Locatable3D.Double to use to create this Node3D.Double.
		 */
		public Double(final Locatable3D.Double loc) {
			this(loc.getX(), loc.getY(), loc.getZ());
		}
		
		/**
		 * Modifies the internal x,y,z positions of this Node3D.Double.
		 * @param x
		 * 		the value to shift the X position of this Node3D.Double.
		 * @param y
		 * 		the value to shift the Y position of this Node3D.Double.
		 * @param z
		 * 		the value to shift the Z position of this Node3D.Double.
		 * @return this Node3D.Double.
		 */
		public Double shift(final double x, final double y, final double z) {
			this.x += x;
			this.y += y;
			this.z += z;
			return this;
		}
		
		/**
		 * Creates a copy of this Node3D.Double shifted by the given values.
		 * @param x
		 * 		the value to shift the X position of this Node3D.Double.
		 * @param y
		 * 		the value to shift the Y position of this Node3D.Double.
		 * @param z
		 * 		the value to shift the Z position of this Node3D.Double.
		 * @return a copy of this Node3D.Double shifted.
		 */
		public Double derive(final double x, final double y, final double z) {
			return new Double(getX() + x, getY() + y, getZ() + z);
		}
		
		@Override
		public double getX() {
			return x;
		}
		
		@Override
		public double getY() {
			return y;
		}
		
		@Override
		public double getZ() {
			return z;
		}
		
		/**
		 * Sets the X position of this Node3D.
		 * @param x
		 * 		the new X position of this Node3D.
		 */
		public void setX(final double x) {
			this.x = x;
		}
		
		/**
		 * Sets the Y position of this Node3D.
		 * @param y
		 * 		the new Y position of this Node3D.
		 */
		public void setY(final double y) {
			this.y = y;
		}
		
		/**
		 * Sets the Z position of this Node3D.
		 * @param z
		 * 		the new Z position of this Node3D.
		 */
		public void setZ(final double z) {
			this.z = z;
		}
		
		/**
		 * @return a Node.Double object of this Node3D.Double's x,y coordinates.
		 */
		public Node.Double flatten() {
			return new Node.Double(x, y);
		}
		
		@Override
		public Node3D.Double clone() {
			return new Node3D.Double(x, y, z);
		}
		
		@Override
		public boolean equals(Object o) {
			return o != null && o instanceof Locatable3D.Double && 
					((Locatable3D.Double) o).getX() == getX() && 
					((Locatable3D.Double) o).getY() == getY() && 
					((Locatable3D.Double) o).getZ() == getZ();
		}
		
		@Override
		public int hashCode() {
			double hash = 373.0D;
			hash = 31.0D * hash + x;
			hash = 31.0D * hash + y;
			hash = 31.0D * hash + z;
			return (int) hash;
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append("Node3D.Double(").append(getX()).append(",").append(getY()).append(",").append(getZ()).append(")").toString();
		}
		
	}
	
}