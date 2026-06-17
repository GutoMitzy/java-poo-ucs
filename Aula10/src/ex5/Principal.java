package ex5;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        Map<String, Carro> carros = new HashMap<>();

        int opcao;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Inserir carro");
            System.out.println("2 - Listar todos os carros");
            System.out.println("3 - Listar carros por cor");
            System.out.println("4 - Mostrar placa do carro mais velho");
            System.out.println("5 - Mostrar média de idade dos carros");
            System.out.println("6 - Atualizar carro por placa");
            System.out.println("7 - Remover carro por placa");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Ano de fabricação: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Cor: ");
                    String cor = scanner.nextLine();

                    Carro carro = new Carro(placa, nome, ano, cor);
                    carros.put(placa, carro);
                    System.out.println("Carro cadastrado!");
                    
                    break;
                case 2:
                    System.out.println("\nLista de carros:");
                    for (Carro c : carros.values()) {
                        System.out.println(c);
                    }
                    
                    break;
                case 3:
                    System.out.print("Informe a cor: ");
                    String corBusca = scanner.nextLine();

                    System.out.println("\nCarros encontrados:");
                    for (Carro c : carros.values()) {
                        if (c.getCor().equals(corBusca)) {
                            System.out.println(c);
                        }
                    }
                    
                    break;
                case 4:
                    Carro maisVelho = null;
                    for (Carro c : carros.values()) {
                        if (maisVelho == null ||c.getAnoFabricacao() < maisVelho.getAnoFabricacao()) {
                            maisVelho = c;
                        }
                    }

                    if (maisVelho != null) {
                        System.out.println("Placa do carro mais velho: " + maisVelho.getPlaca());
                    }

                    break;
                case 5:
                    if (carros.isEmpty()) {
                        System.out.println("Nenhum carro cadastrado.");
                        break;
                    }

                    int anoAtual = Year.now().getValue();
                    double soma = 0;

                    for (Carro c : carros.values()) {
                        soma += (anoAtual - c.getAnoFabricacao());
                    }

                    double media = soma / carros.size();
                    System.out.println("Média de idade: " + media + " anos");

                    break;
                case 6:
                    System.out.print("Informe a placa do carro: ");
                    String placaAtualizar = scanner.nextLine();

                    if (carros.containsKey(placaAtualizar)) {
                        Carro c = carros.get(placaAtualizar);

                        System.out.print("Novo nome: ");
                        c.setNome(scanner.nextLine());

                        System.out.print("Novo ano: ");
                        c.setAnoFabricacao(scanner.nextInt());
                        scanner.nextLine();

                        System.out.print("Nova cor: ");
                        c.setCor(scanner.nextLine());

                        System.out.println("Dados atualizados!");
                    } else {
                        System.out.println("Carro não encontrado.");
                    }

                    break;
                case 7:
                    System.out.print("Informe a placa do carro: ");
                    String placaRemover = scanner.nextLine();

                    if (carros.remove(placaRemover) != null) {
                        System.out.println("Carro removido!");
                    } else {
                        System.out.println("Carro não encontrado.");
                    }

                    break;
                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
        scanner.close();
    }
}

