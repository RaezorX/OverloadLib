package org.overload.loc;

public class Node3D implements Locatable3D {

	protected int x, y, z;
	
	public Node3D(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Node3D(final Locatable3D loc) {
		this(loc.getX(), loc.getY(), loc.getZ());
	}
	
	public Node3D shift(final int x, final int y, final int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
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
	
	public void setX(final int x) {
		this.x = x;
	}
	
	public void setY(final int y) {
		this.y = y;
	}
	
	public void setZ(final int z) {
		this.z = z;
	}
	
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
		return (x * 31 + y) * z;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("Node3D(").append(getX()).append(",").append(getY()).append(",").append(getZ()).append(")").toString();
	}
	
	public static class Double implements Locatable3D.Double {

		protected double x, y, z;
		
		public Double(final double x, final double y, final double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Double(final Locatable3D loc) {
			this(loc.getX(), loc.getY(), loc.getZ());
		}
		
		public Double shift(final double x, final double y, final double z) {
			this.x += x;
			this.y += y;
			this.z += z;
			return this;
		}
		
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
		
		public void setX(final double x) {
			this.x = x;
		}
		
		public void setY(final double y) {
			this.y = y;
		}
		
		public void setZ(final double z) {
			this.z = z;
		}
		
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
			return (int)((x * 31 + y) * z);
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append("Node3D.Double(").append(getX()).append(",").append(getY()).append(",").append(getZ()).append(")").toString();
		}
		
	}
	
}