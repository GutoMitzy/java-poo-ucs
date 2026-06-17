package principal;

import java.util.Scanner;

import exceptions.EntradaInvalidaException;

public class Leitura {

    private Leitura() {}

    public static int lerInt(Scanner sc) throws EntradaInvalidaException {
        String linha = sc.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Valor informado nao e um numero inteiro valido: '" + linha + "'");
        }
    }

    public static float lerFloat(Scanner sc) throws EntradaInvalidaException {
        String linha = sc.nextLine().trim();
        try {
            return Float.parseFloat(linha.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Valor informado nao e um numero valido: '" + linha + "'");
        }
    }

    public static int lerIntComPrompt(Scanner sc, String prompt) throws EntradaInvalidaException {
        System.out.print(prompt);
        return lerInt(sc);
    }

    public static float lerFloatComPrompt(Scanner sc, String prompt) throws EntradaInvalidaException {
        System.out.print(prompt);
        return lerFloat(sc);
    }

    public static String lerLinhaComPrompt(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static int lerOpcaoMenu(Scanner sc) {
        System.out.print("Opcao: ");
        try {
            return lerInt(sc);
        } catch (EntradaInvalidaException e) {
            return -1;
        }
    }
}
