package ex2;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Questao2 {
	public static void main(String[] args) throws InputMismatchException{
		Scanner s = new Scanner(System.in);
		int x, y;
		
		try {
			System.out.println("Eu sei dividir!");
			System.out.println("Informe o primeiro valor:");
			
			while() {
				x = s.nextInt();
				System.out.println("Informe o segundo valor:");
				
				y = s.nextInt();
				double r = (x+y);
				
				System.out.println("O resultado da soma é " + r);			
			}
		} catch (InputMismatchException i) {
			System.out.println("O valor não é um número inteiro!");
		}
	}
}
