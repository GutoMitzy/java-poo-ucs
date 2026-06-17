package aula5.classes;
import aula5.interfaces.Professor;

public class ProfessorTecnico extends PessoaBase implements Professor {
	private String nome;
	private int cargaHoraria;
	private float titulacao;
	
	public ProfessorTecnico(String nome, int cargaHoraria) {
		super(nome, cargaHoraria);
	}


	public float obtemTitulacao() {
		return this.titulacao;
	}
	
	public int cargaHoraria() {
		return this.cargaHoraria;
	}
	
	public double obtemSalario() {
		return this.cargaHoraria * VALOR_HORA * 3 * titulacaoAdicional;
	}
}
