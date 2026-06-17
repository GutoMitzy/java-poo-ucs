package ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) {
		List<Double> numeros = new ArrayList<Double>();
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Quantos números deseja informar? ");
        int quantidade = sc.nextInt();
        
		for(int i=0; i<quantidade;i++) {
			System.out.print("Número " + (i + 1) + ": ");
			numeros.add(sc.nextDouble());
		}
		
		double soma=0;
        for (double numero : numeros) {
            soma += numero;
        }

        double media = soma / numeros.size();
		
		System.out.println("Média dos valores: " + media);
	}
}
