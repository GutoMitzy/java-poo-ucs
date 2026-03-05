package intro;

import java.util.Scanner;

public class somador2 {
	public static void main(String[] args) {
		int a , b, c;
		Scanner sc;
		somador2 s2 = new somador2();
		
		sc = new Scanner(System.in);
		
		System.out.print("Informe o primeiro valor:");
		a = sc.nextInt();
		
		System.out.print("Informe o segundo valor:");
		b = sc.nextInt();
		
		System.out.println("A soma é: " + s2.soma(a, b));
		
		sc.close();
	}

	public int soma(int a, int b) {
		return a+b;
	}
}
