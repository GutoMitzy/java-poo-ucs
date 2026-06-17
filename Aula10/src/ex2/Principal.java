package ex2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal{
	public static void main(String[] args) {
		List<Integer> numeros = new ArrayList<Integer>();
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Quantos números deseja informar? ");
        int quantidade = scanner.nextInt();
        
        for (int i = 0; i < quantidade; i++) {
            System.out.print("Número " + (i + 1) + ": ");
            numeros.add(scanner.nextInt());
        }
        
        int maior = Collections.max(numeros);
        int menor = Collections.min(numeros);
        
        System.out.println("Maior número: " + maior);
        System.out.println("Menor número: " + menor);
	}
}
