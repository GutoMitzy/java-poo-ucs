package n4;

public class Funcionario {
	private float salario;
	
	public void setSalario(float salario) {
		this.salario = salario;
	}
	
	public float qtdSalariosMin(float salarioMinimo) {
		return this.salario / salarioMinimo;
	}
	
}
