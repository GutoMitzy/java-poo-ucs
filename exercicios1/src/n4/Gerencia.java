package n4;
import java.util.Scanner;

public class Gerencia {
	public static void main(String[] args) {
		Funcionario fc = new Funcionario();
		Scanner sc = new Scanner(System.in);
		float valor;
		
		System.out.println("Digite o salário: ");
		valor = sc.nextFloat();
		fc.setSalario(valor);
		
		System.out.println("O funcionário recebe " + fc.qtdSalariosMin(1621) + " salários mínimos!");
		
		sc.close();
	}
}

