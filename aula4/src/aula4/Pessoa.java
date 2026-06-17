package aula4;

public class Pessoa {
	private static int ultimaPessoa=0;
	private String nome;
	private int codigo;
	
	public Pessoa(String nome) {
		this.nome = nome;
		this.codigo = ultimaPessoa++;
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", codigo=" + codigo + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static int getUltimaPessoa() {
		return ultimaPessoa;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public static void setUltimaPessoa(int valor) {
		ultimaPessoa = valor;
	}
	
	public static void main(String[] args) {
		Pessoa p1 = new Pessoa("Augusto");
		System.out.println(p1);
		
		Pessoa p2 = new Pessoa("Fernando");
		System.out.println(p2);
		
		Pessoa p3 = new Pessoa("Suelen");
		System.out.println(p3);
	}
}

