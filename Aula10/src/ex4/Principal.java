package ex4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        List<Integer> numeros = new ArrayList<>();

        System.out.print("Quantos números deseja informar? ");
        int quantidade = scanner.nextInt();

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Digite o número " + (i + 1) + ": ");
            numeros.add(scanner.nextInt());
        }

        Collections.sort(numeros, Comparator.reverseOrder());

        System.out.println("\nLista em ordem decrescente:");
        System.out.println(numeros);

        scanner.close();
	}
}
