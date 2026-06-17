package aula5.main;
import aula5.classes.Estagiario;
import aula5.classes.Funcionario;
import aula5.classes.ProfessorGraduacao;
import aula5.classes.ProfessorTecnico;
import aula5.interfaces.Colaborador;

public class Principal {
	public static void main(String[] args) {
		Colaborador[] c = null;
		
		c[0] = new Estagiario("Augusto", 6);
		c[1] = new Estagiario("Fernanda", 6);
		c[2] = new Estagiario("Eduarda", 6);
		c[3] = new Funcionario("Camila", 10);
		c[4] = new Funcionario("Eduardo", 10);
		c[5] = new Funcionario("Felipe", 10);
		c[6] = new Funcionario("Gustavo", 10);
		c[7] = new ProfessorTecnico("Helena", 10, "Doutorado");
		c[8] = new ProfessorTecnico("Roberta", 12, "Mestrado");
		c[9] = new ProfessorGraduacao("Alexandre", 12, "Mestrado");
		
		
	}
}
