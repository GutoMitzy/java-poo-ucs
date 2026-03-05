package n3;
import java.util.Scanner;

public class Calculo {
	public static void main(String[] args) {
		Triangulo tri = new Triangulo();

		Scanner sc = new Scanner(System.in);
		float valor;
		
		System.out.println("Digite o valor do Cateto A: ");
		valor = sc.nextFloat();
		tri.setCatetoA(valor);
		
		System.out.println("Digite o valor do Cateto B: ");
		valor = sc.nextFloat();
		tri.setCatetoB(valor);
		
		System.out.println("Valor da Hipotenusa: " + tri.calculaHipotenusa());
	}
}
