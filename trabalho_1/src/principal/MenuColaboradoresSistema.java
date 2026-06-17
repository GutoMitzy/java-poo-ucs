package principal;

import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import exceptions.EntidadeNaoEncontradaException;
import model.Colaborador;

public class MenuColaboradoresSistema {

    private static Scanner sc = new Scanner(System.in);
    private Sistema sistema;

    public MenuColaboradoresSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  COLABORADORES");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Cadastrar Colaborador");
            System.out.println("  2. Excluir Colaborador");
            System.out.println("  3. Atualizar Colaborador");
            System.out.println("  4. Listar Colaboradores");
            System.out.println("  5. Buscar por Codigo");
            System.out.println("  6. Buscar por Nome");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    cadastrarColaborador();
                    break;
                case 2:
                    excluirColaborador();
                    break;
                case 3:
                    atualizarColaborador();
                    break;
                case 4:
                    sistema.listarColaboradores();
                    break;
                case 5:
                    sistema.buscarColaboradorSistemaCodigo();
                    break;
                case 6:
                    sistema.buscarColaboradorSistemaNome();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void cadastrarColaborador() {
        System.out.println();
        System.out.println("-- Cadastrar Colaborador --");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome nao pode ser vazio.");
            return;
        }

        String cpf = Leitura.lerLinhaComPrompt(sc, "CPF: ");
        String telefone = Leitura.lerLinhaComPrompt(sc, "Telefone: ");
        String email = Leitura.lerLinhaComPrompt(sc, "Email: ");
        String cargo = Leitura.lerLinhaComPrompt(sc, "Cargo: ");

        float remuneracao;
        try {
            remuneracao = Leitura.lerFloatComPrompt(sc, "Remuneracao: R$ ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Colaborador colaborador = new Colaborador(nome.trim(), cpf, telefone, email, cargo, remuneracao);
        sistema.getColaboradores().add(colaborador);
        sistema.registrarOperacao("Colaborador cadastrado: [" + colaborador.getCodigo() + "] " + colaborador.getNome());
        sistema.autoSalvar();
        System.out.println("Colaborador '" + colaborador.getNome() + "' cadastrado com sucesso!");
    }

    private void excluirColaborador() {
        System.out.println();
        System.out.println("-- Excluir Colaborador --");

        if (sistema.getColaboradores().isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado para excluir.");
            return;
        }

        sistema.listarColaboradores();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador a excluir: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Colaborador colaborador = sistema.buscarColaborador(codigo);
        if (colaborador == null) {
            System.out.println("Colaborador [" + codigo + "] nao encontrado.");
            return;
        }

        sistema.getColaboradores().remove(colaborador);
        sistema.registrarOperacao("Colaborador excluido: [" + codigo + "] " + colaborador.getNome());
        sistema.autoSalvar();
        System.out.println("Colaborador '" + colaborador.getNome() + "' excluido com sucesso!");
    }

    private void atualizarColaborador() {
        System.out.println();
        System.out.println("-- Atualizar Colaborador --");

        if (sistema.getColaboradores().isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado para atualizar.");
            return;
        }

        sistema.listarColaboradores();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador a atualizar: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Colaborador c = sistema.buscarColaborador(codigo);
        if (c == null) {
            System.out.println("Colaborador [" + codigo + "] nao encontrado.");
            return;
        }

        System.out.println("Dados atuais: " + c.getNome() + " | " + c.getCargo() + " | " + c.getEmail());
        System.out.println("(deixe em branco para manter o atual)");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (!nome.trim().isEmpty()) {
            c.setNome(nome.trim());
        }

        String cpf = Leitura.lerLinhaComPrompt(sc, "CPF: ");
        if (!cpf.trim().isEmpty()) {
            c.setCpf(cpf.trim());
        }

        String telefone = Leitura.lerLinhaComPrompt(sc, "Telefone: ");
        if (!telefone.trim().isEmpty()) {
            c.setTelefone(telefone.trim());
        }

        String email = Leitura.lerLinhaComPrompt(sc, "Email: ");
        if (!email.trim().isEmpty()) {
            c.setEmail(email.trim());
        }

        String cargo = Leitura.lerLinhaComPrompt(sc, "Cargo: ");
        if (!cargo.trim().isEmpty()) {
            c.setCargo(cargo.trim());
        }

        String remunStr = Leitura.lerLinhaComPrompt(sc, "Remuneracao: R$ ");
        if (!remunStr.trim().isEmpty()) {
            try {
                c.setRemuneracao(Float.parseFloat(remunStr.replace(",", ".")));
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, remuneracao mantida.");
            }
        }

        sistema.registrarOperacao("Colaborador atualizado: [" + c.getCodigo() + "] " + c.getNome());
        sistema.autoSalvar();
        System.out.println("Colaborador atualizado com sucesso!");
    }
}
