package principal;

import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import model.Recurso;

public class MenuRecursosSistema {

    private static Scanner sc = new Scanner(System.in);
    private Sistema sistema;

    public MenuRecursosSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RECURSOS DO SISTEMA");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Cadastrar Recurso");
            System.out.println("  2. Listar Recursos");
            System.out.println("  3. Excluir Recurso");
            System.out.println("  4. Atualizar Recurso");
            System.out.println("  5. Buscar por Codigo");
            System.out.println("  6. Buscar por Nome");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    cadastrarRecurso();
                    break;
                case 2:
                    sistema.listarRecursos();
                    break;
                case 3:
                    excluirRecurso();
                    break;
                case 4:
                    atualizarRecurso();
                    break;
                case 5:
                    sistema.buscarRecursoSistemaCodigo();
                    break;
                case 6:
                    sistema.buscarRecursoSistemaNome();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void cadastrarRecurso() {
        System.out.println();
        System.out.println("-- Cadastrar Recurso --");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome nao pode ser vazio.");
            return;
        }

        String tipo = Leitura.lerLinhaComPrompt(sc, "Tipo (equipamento | item_consumo): ");
        if (tipo.trim().isEmpty()) {
            System.out.println("Tipo nao pode ser vazio.");
            return;
        }

        float preco;
        try {
            preco = Leitura.lerFloatComPrompt(sc, "Custo unitario: R$ ");
            if (preco < 0) {
                System.out.println("Custo nao pode ser negativo.");
                return;
            }
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        int estoque;
        try {
            estoque = Leitura.lerIntComPrompt(sc, "Estoque disponivel: ");
            if (estoque < 0) {
                System.out.println("Estoque nao pode ser negativo.");
                return;
            }
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Recurso recurso = new Recurso(nome.trim(), tipo.trim(), preco, estoque);
        sistema.getRecursos().add(recurso);
        sistema.registrarOperacao("Recurso cadastrado: [" + recurso.getCodigo() + "] " + recurso.getNome());
        sistema.autoSalvar();
        System.out.println("Recurso '" + recurso.getNome() + "' cadastrado com sucesso!");
    }

    private void excluirRecurso() {
        System.out.println();
        System.out.println("-- Excluir Recurso --");

        if (sistema.getRecursos().isEmpty()) {
            System.out.println("Nenhum recurso cadastrado para excluir.");
            return;
        }

        sistema.listarRecursos();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso a excluir: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Recurso recurso = sistema.buscarRecurso(codigo);
        if (recurso == null) {
            System.out.println("Recurso [" + codigo + "] nao encontrado.");
            return;
        }

        sistema.getRecursos().remove(recurso);
        sistema.registrarOperacao("Recurso excluido: [" + codigo + "] " + recurso.getNome());
        sistema.autoSalvar();
        System.out.println("Recurso '" + recurso.getNome() + "' excluido com sucesso!");
    }

    private void atualizarRecurso() {
        System.out.println();
        System.out.println("-- Atualizar Recurso --");

        if (sistema.getRecursos().isEmpty()) {
            System.out.println("Nenhum recurso cadastrado para atualizar.");
            return;
        }

        sistema.listarRecursos();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso a atualizar: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Recurso recurso = sistema.buscarRecurso(codigo);
        if (recurso == null) {
            System.out.println("Recurso [" + codigo + "] nao encontrado.");
            return;
        }

        System.out.println("Dados atuais: " + recurso.getNome() + " | " + recurso.getTipo()
                + " | R$ " + String.format("%.2f", recurso.getCustoUnitario())
                + " | Estoque: " + recurso.getEstoque());
        System.out.println("(deixe em branco para manter o atual)");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (!nome.trim().isEmpty()) {
            recurso.setNome(nome.trim());
        }

        String tipo = Leitura.lerLinhaComPrompt(sc, "Tipo (equipamento | item_consumo): ");
        if (!tipo.trim().isEmpty()) {
            recurso.setTipo(tipo.trim());
        }

        String custoStr = Leitura.lerLinhaComPrompt(sc, "Custo unitario: R$ ");
        if (!custoStr.trim().isEmpty()) {
            try {
                float custo = Float.parseFloat(custoStr.replace(",", "."));
                if (custo < 0) {
                    System.out.println("Custo nao pode ser negativo, mantido o valor atual.");
                } else {
                    recurso.setCustoUnitario(custo);
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, custo mantido.");
            }
        }

        String estoqueStr = Leitura.lerLinhaComPrompt(sc, "Estoque: ");
        if (!estoqueStr.trim().isEmpty()) {
            try {
                int novoEstoque = Integer.parseInt(estoqueStr.trim());
                if (novoEstoque < 0) {
                    System.out.println("Estoque nao pode ser negativo, mantido o valor atual.");
                } else {
                    recurso.setEstoque(novoEstoque);
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, estoque mantido.");
            }
        }

        sistema.registrarOperacao("Recurso atualizado: [" + recurso.getCodigo() + "] " + recurso.getNome());
        sistema.autoSalvar();
        System.out.println("Recurso atualizado com sucesso!");
    }
}
