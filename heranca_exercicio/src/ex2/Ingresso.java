package ex2;

public class Ingresso {
	private float valor;
	
	public Ingresso() {}

	public Ingresso(float valor) {
		this.valor = valor;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public void imprimeValor() {
		System.out.println("Valor do Ingresso: R$" + this.valor);
	}

	
}
