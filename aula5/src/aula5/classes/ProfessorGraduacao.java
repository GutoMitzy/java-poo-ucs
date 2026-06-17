package aula5.classes;
import aula5.interfaces.Professor;

public class ProfessorGraduacao extends PessoaBase implements Professor{
	private String nome;
	private int cargaHoraria;
	
	public ProfessorGraduacao(String nome, int cargaHoraria) {
		super(nome, cargaHoraria);
	}

	public float obtemTitulacao() {
		return GRADUACAO;
	}
	
	public double obtemSalario() {
		return this.cargaHoraria * VALOR_HORA * 4 * GRADUACAO;
	}
}
