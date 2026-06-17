package aula5.classes;
import aula5.interfaces.Colaborador;

public class Funcionario implements Colaborador{
	private String nome;
	private int cargaHoraria;

	public Funcionario() {}
	
	public Funcionario(String nome, int cargaHoraria) {
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCargaHoraria() {
		return this.cargaHoraria;
	}

	public double obtemSalario() {
		return VALOR_HORA * getCargaHoraria() * 2;
	}
}
