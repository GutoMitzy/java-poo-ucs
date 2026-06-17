package aula5.classes;

public abstract class PessoaBase {
	private int cargaHoraria;
	private String nome;
	
	public PessoaBase(String nome, int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
		this.nome = nome;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
