package ex3;
import java.util.Scanner;
import java.util.Arrays;

public class Init {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] vetor = new int[10];
		int n, ind=0;
		
		do {
			System.out.println("Digite o valor:");
			n = sc.nextInt();
			vetor[ind++] = n;
		} while (n != 0);
				
		System.out.println(Arrays.toString(vetor));
		
		System.out.println("Digite um multiplicador:");
		int mult = sc.nextInt();
		
		do {
			vetor[ind++] *= mult;
		} while (vetor[ind] != 0);
		
		System.out.println(Arrays.toString(vetor));
	}
}
