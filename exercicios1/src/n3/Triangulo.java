package n3;
import java.lang.Math;

public class Triangulo {
	private float a;
	private float b;
	
	public void setCatetoA(float catetoA) {
		this.a = catetoA;
	}
	
	public void setCatetoB(float catetoB) {
		this.b = catetoB;
	}
	
	public float calculaHipotenusa() {
		return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b,  2));
	}
}
