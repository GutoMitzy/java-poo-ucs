package ex1;
import java.util.Scanner;

public class DemoTesteArray {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] v = new int[5];
		
		System.out.println("Digite o tamanho do vetor:");
		int tam = sc.nextInt();
		
		TesteArray ta = new TesteArray(tam);
		System.out.println(ta);
		
		ta.recebeArray(v);
		System.out.println(v[0]);
	}
}
