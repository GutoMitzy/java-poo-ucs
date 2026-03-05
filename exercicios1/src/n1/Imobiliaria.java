package n1;
import java.util.Scanner;

public class Imobiliaria {
	public static void main(String[] args) {
		Terreno ter = new Terreno();
		
		Scanner sc = new Scanner(System.in);
		float valor;
		
		System.out.println("Digite o valor do comprimento (em m): ");
		valor = sc.nextFloat();
		ter.setComprimento(valor);
		
		System.out.println("Digite o valor da largura (em m): ");
		valor = sc.nextFloat();
		ter.setLargura(valor);
		
		
		System.out.println("Área do terreno: " + ter.calculaArea() + "m².");
		
		sc.close();
	}
}
