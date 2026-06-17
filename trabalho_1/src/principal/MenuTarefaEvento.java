package principal;

import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import exceptions.OperacaoInvalidaException;
import model.Evento;
import model.Tarefa;

public class MenuTarefaEvento {

    private static Scanner sc = new Scanner(System.in);
    private Evento evento;
    private Sistema sistema;
    private MenuTarefa menuTarefa;

    public MenuTarefaEvento(Evento evento, Sistema sistema) {
        this.evento = evento;
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("   TAREFAS: " + evento.getNome());
            System.out.println("   " + evento.getAndamento());
            System.out.println("-----------------------------------------");
            System.out.println("  1. Cadastrar Tarefa");
            System.out.println("  2. Excluir Tarefa");
            System.out.println("  3. Acessar Tarefa");
            System.out.println("  4. Listar Tarefas");
            System.out.println("  5. Buscar por Codigo");
            System.out.println("  6. Buscar por Nome");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            try {
                switch (opcao) {
                    case 1:
                        cadastrarTarefa();
                        break;
                    case 2:
                        excluirTarefa();
                        break;
                    case 3:
                        acessarTarefa();
                        break;
                    case 4:
                        evento.listarTarefas();
                        break;
                    case 5:
                        evento.buscarTarefaEventoCodigo();
                        break;
                    case 6:
                        evento.buscarTarefaEventoNome();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (OperacaoInvalidaException e) {
                System.out.println("Operacao invalida: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void cadastrarTarefa() throws OperacaoInvalidaException {
        System.out.println();
        System.out.println("-- Cadastrar Tarefa --");

        String descricao = Leitura.lerLinhaComPrompt(sc, "Descricao: ");
        if (descricao.trim().isEmpty()) {
            throw new OperacaoInvalidaException("Descricao nao pode ser vazia.");
        }

        System.out.println("Status: planejada | em_execucao | concluida");
        String status = Leitura.lerLinhaComPrompt(sc, "Status: ").toLowerCase().trim();

        if (!status.equals("planejada") && !status.equals("em_execucao") && !status.equals("concluida")) {
            System.out.println("Status invalido. Usando 'planejada' como padrao.");
            status = "planejada";
        }

        Tarefa tarefa = new Tarefa(descricao.trim(), status);
        evento.adicionarTarefa(tarefa);
        sistema.registrarOperacao("Tarefa cadastrada no evento [" + evento.getCodigo() + "]: [" + tarefa.getCodigo() + "] " + tarefa.getDescricao());
        sistema.autoSalvar();
        System.out.println("Tarefa cadastrada! Codigo: " + tarefa.getCodigo());
    }

    private void excluirTarefa() throws OperacaoInvalidaException {
        System.out.println();
        System.out.println("-- Excluir Tarefa --");

        if (evento.getTarefas().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhuma tarefa cadastrada para excluir.");
        }

        evento.listarTarefas();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo da tarefa a excluir: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Tarefa tarefa = evento.buscarTarefa(codigo);
        if (tarefa == null) {
            System.out.println("Tarefa [" + codigo + "] nao encontrada.");
            return;
        }

        evento.removerTarefa(codigo);
        sistema.registrarOperacao("Tarefa excluida do evento [" + evento.getCodigo() + "]: [" + codigo + "] " + tarefa.getDescricao());
        sistema.autoSalvar();
        System.out.println("Tarefa excluida com sucesso! Recursos devolvidos ao estoque.");
    }

    private void acessarTarefa() throws OperacaoInvalidaException {
        System.out.println();
        System.out.println("-- Acessar Tarefa --");

        if (evento.getTarefas().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhuma tarefa cadastrada.");
        }

        evento.listarTarefas();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo da tarefa: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Tarefa tarefa = evento.buscarTarefa(codigo);
        if (tarefa == null) {
            System.out.println("Tarefa [" + codigo + "] nao encontrada.");
            return;
        }

        menuTarefa = new MenuTarefa(tarefa, evento, sistema);
        menuTarefa.mostra();
    }
}
