package com.overload.matrix;

/**
 * A simple matrix class which only supported multiplication.
 */
public class Matrix {
	
	protected final int[][] data;

	public Matrix(final int[][] data) {
		this.data = data;
	}

	/**
	 * Standard matrix multiplication process. ~ O(N^3)
	 * @param other
	 * 		the other matrix to multiply.
	 * @return a new Matrix representing the multiplied matrices.
	 */
	public Matrix multiply(Matrix other) {
		if (other == null)
			return this.clone();
		if (data.length != other.data[0].length)
			return null;
		final int[][] result = new int[other.data.length][data[0].length];
		for (int j = 0; j < data[0].length; j++) { // iterate over matrix1 rows (also result rows)
			for (int i2 = 0; i2 < other.data.length; i2++) { // iterate over matrix2 columns (also result columns)
				result[i2][j] = 0;
				for (int i = 0; i < data.length; i++) { // iterate over matrix1 columns (also matrix2 rows)
					result[i2][j] += data[i][j] * other.data[i2][i];
				}
			}
		}
		return new Matrix(result);
	}
	
	public void print() {
		System.out.println(printToString());
	}
	
	@Override
	public String toString() {
		return printToString();
	}
	
	/*
	 * ┌──┬──┬──┬──┬──┐
	 * │0 │1 │2 │3 │4 │
	 * ├──┼──┼──┼──┼──┤
	 * │5 │6 │7 │8 │9 │
	 * ├──┼──┼──┼──┼──┤
	 * │10│11│12│13│14│
	 * └──┴──┴──┴──┴──┘
	 */
	private String printToString() {
		int cellWidth = 1;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				int n = data[i][j];
				cellWidth = Math.max(cellWidth, String.valueOf(n).length());
			}
		}
		int j = 0;
		final int dMax = 1 + data[0].length * 2;
		final StringBuilder b = new StringBuilder(data.length * dMax * cellWidth);
		for (int d = 0; d < dMax; d++) {
			if (d % 2 == 0) {
				if (d == 0) {
					b.append("┌");
					for (int i = 0; i < data.length; i++) {
						for (int c = 0; c < cellWidth; c++)
							b.append("─");
						if (i < data.length - 1)
							b.append("┬");
					}
					b.append("┐");
					b.append("\n");
				} else if (d == dMax - 1) {
					b.append("└");
					for (int i = 0; i < data.length; i++) {
						for (int c = 0; c < cellWidth; c++)
							b.append("─");
						if (i < data.length - 1)
							b.append("┴");
					}
					b.append("┘");
				} else {
					b.append("├");
					for (int i = 0; i < data.length; i++) {
						for (int c = 0; c < cellWidth; c++)
							b.append("─");
						if (i < data.length - 1)
							b.append("┼");
					}
					b.append("┤");
					b.append("\n");
				}
			} else {
				for (int i = 0; i < data.length; i++) {
					b.append("│");
					final int n = data[i][j];
					final String s = String.valueOf(n);
					b.append(s);
					final int cellTemp = cellWidth - s.length();
					for (int ct = 0; ct < cellTemp; ct++)
						b.append(" ");
				}
				b.append("│");
				b.append("\n");
				j++;
			}
		}
		return b.toString();
	}
	
	@Override
	public Matrix clone() {
		return new Matrix(matrixClone(data));
	}
	
	protected int[][] matrixClone(final int[][] matrix) {
		int[][] result = matrix.clone();
		for (int i = 0; i < result.length; i++)
			result[i] = matrix[i].clone();
		return result;
	}
	
	public static Matrix multiplyAll(final Matrix... matrices) {
		Matrix result = null;
		for (int m = 0; m < matrices.length; m++) {
			if (result != null) {
				Matrix temp = result.multiply(matrices[m]);
				if (temp == null)
					throw new IncompleteMMException(result);
				result = temp;
			} else {
				result = matrices[m];
			}
		}
		return result;
	}
	
	/**
	 * Incomplete Matrix Multiplication Exception
	 * Occurs when an array of matrices are multiplied together but an error occurs in one of the multiplications.
	 */
	public static class IncompleteMMException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		private Matrix matrix;
		
		public IncompleteMMException() {
			matrix = null;
		}
		
		public IncompleteMMException(Matrix abstractMatrix) {
			matrix = abstractMatrix;
		}
		
		public Matrix getIncompleteMatrix() {
			return matrix;
		}
		
	}
	
}