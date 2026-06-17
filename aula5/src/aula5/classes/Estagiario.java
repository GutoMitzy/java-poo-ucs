package aula5.classes;
import aula5.interfaces.Colaborador;


public class Estagiario extends PessoaBase implements Colaborador{
	
	public Estagiario(String nome, int cargaHoraria) {
		super(nome, cargaHoraria);
	}
	
	public double obtemSalario() {
		return VALOR_HORA * getCargaHoraria();
	}

	@Override
	public String toString() {
		return "Estagiario: " + super.getNome() + ", R$" + super.getCargaHoraria() + ".";
	}
	
}
