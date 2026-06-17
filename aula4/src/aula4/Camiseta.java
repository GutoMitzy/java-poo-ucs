package aula4;

public class Camiseta {
	private int nrUniforme;
	private String cor;
	
	public Camiseta() {}
	
	public Camiseta(int nrUniforme, String cor) {
		this.nrUniforme = nrUniforme;
		this.cor = cor;
	}

	public int getNrUniforme() {
		return nrUniforme;
	}

	public void setNrUniforme(int nrUniforme) {
		this.nrUniforme = nrUniforme;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public String toString() {
		return "Camiseta [nrUniforme=" + nrUniforme + ", cor=" + cor + "]";
	}
	
}
