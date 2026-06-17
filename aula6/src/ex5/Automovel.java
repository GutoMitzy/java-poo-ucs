package ex5;

public class Automovel {
	private String marca;
	private String nome;
	private String cor;
	
	public Automovel() {}
	
	public Automovel(String marca, String nome, String cor) {
		this.marca = marca;
		this.nome = nome;
		this.cor = cor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Override
	public String toString() {
		return "Automovel [marca=" + marca + ", nome=" + nome + ", cor=" + cor + "]";
	}
	
	
}
