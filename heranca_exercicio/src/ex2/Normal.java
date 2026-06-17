package ex2;

public class Normal extends Ingresso{
	private Ingresso ingresso;
	
	public Normal(Ingresso ingresso) {
		this.ingresso = ingresso;
	}

	public Ingresso getIngresso() {
		return ingresso;
	}

	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	
	public void imprimeIngresso() {
		System.out.println("Ingresso Normal");
	}
	
}
