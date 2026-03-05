package intro;

import java.util.Scanner;

public class somador {
	public static int soma (int x, int y) {
		return x+y;
	}
	public static void main(String[] args) {
		int a , b, c;
		Scanner sc;
		sc = new Scanner(System.in);
		somador2 s = new somador2();
		
		System.out.print("Informe o primeiro valor:");
		a = sc.nextInt();
		
		System.out.print("Informe o segundo valor:");
		b = sc.nextInt();
		
		c = s.soma(a, b);
		System.out.println("O resultado é " + c);
		
		sc.close();
	}
}
