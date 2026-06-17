package ex5;
import java.util.Scanner;
import java.util.Arrays;

public class Principal {
	public static void main(String[] args) {
		final int CARROS = 5;
		Automovel[] automoveis = new Automovel[CARROS];
		Scanner sc = new Scanner(System.in);
		
		String marca, nome, cor;
		
		for(int i=0;i<CARROS;i++) {
			System.out.println("Digite a marca:");
			marca = sc.nextLine();
			
			System.out.println("Digite o nome:");
			nome = sc.nextLine();
			
			System.out.println("Digite a cor:");
			cor = sc.nextLine();
			
			automoveis[i].setCor(cor);
			automoveis[i].setMarca(marca);
			automoveis[i].setNome(nome);
		}
		
		System.out.println("Digite a cor de busca:");
		String corBuscada = sc.nextLine();
		
		for(int i=0;i<CARROS;i++) {
			if(automoveis[i] != null && corBuscada.equals(automoveis[i].getCor())) {
				System.out.print(automoveis[i] + " ");
			}
		}
		
		
	}
}
