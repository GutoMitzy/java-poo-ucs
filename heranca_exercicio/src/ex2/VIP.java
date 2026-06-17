package ex2;

public class VIP extends Ingresso{
	private float valorAdicional;
	private Ingresso ingresso;
	
	public VIP(float valorAdicional, Ingresso ingresso) {
		this.valorAdicional = valorAdicional;
		this.ingresso = ingresso;
	}

	public void imprimeVIP() {
		System.out.println("Valor do Ingresso VIP: R$" + ingresso.getValor() + valorAdicional);
	}

	public float getValorAdicional() {
		return valorAdicional;
	}

	public void setValorAdicional(float valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public Ingresso getIngresso() {
		return ingresso;
	}

	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
}
