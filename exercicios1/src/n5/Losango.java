package n5;

public class Losango {
	private float diagonalMaior;
	private float diagonalMenor;

	public void setLosango(float diagonalMaior, float diagonalMenor) {
		this.diagonalMaior = diagonalMaior;
		this.diagonalMenor = diagonalMenor;
	}

	public float getArea() {
		return (this.diagonalMaior * this.diagonalMenor) / 2;
	}
}
