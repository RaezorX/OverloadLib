package org.overload.loc;

public class Node implements Locatable {

	protected int x, y;
	
	public Node(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public Node(final Locatable loc) {
		this(loc.getX(), loc.getY());
	}
	
	public Node shift(final int x, final int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Node derive(final int x, final int y) {
		return new Node(getX() + x, getY() + y);
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	public void setX(final int x) {
		this.x = x;
	}
	
	public void setY(final int y) {
		this.y = y;
	}
	
	@Override
	public Node clone() {
		return new Node(x, y);
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Locatable && ((Locatable) o).getX() == getX() && ((Locatable) o).getY() == getY();
	}
	
	@Override
	public int hashCode() {
		return x * 31 + y;
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("Node(").append(getX()).append(",").append(getY()).append(")").toString();
	}
	
	public static class Double implements Locatable.Double {
		
		protected double x, y;
		
		public Double(final double x, final double y) {
			this.x = x;
			this.y = y;
		}
		
		public Double(final Locatable.Double loc) {
			this(loc.getX(), loc.getY());
		}
		
		public Double shift(final double x, final double y) {
			this.x += x;
			this.y += y;
			return this;
		}
		
		public Double derive(final double x, final double y) {
			return new Double(getX() + x, getY() + y);
		}
		
		@Override
		public double getX() {
			return x;
		}
		
		@Override
		public double getY() {
			return y;
		}
		
		public void setX(final double x) {
			this.x = x;
		}
		
		public void setY(final double y) {
			this.y = y;
		}
		
		@Override
		public Double clone() {
			return new Double(x, y);
		}
		
		@Override
		public boolean equals(Object o) {
			return o != null && o instanceof Locatable.Double && ((Locatable.Double) o).getX() == getX() && ((Locatable.Double) o).getY() == getY();
		}
		
		@Override
		public int hashCode() {
			return (int)(x * 31.0D + y);
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append("Node.Double(").append(getX()).append(",").append(getY()).append(")").toString();
		}
		
	}
	
}