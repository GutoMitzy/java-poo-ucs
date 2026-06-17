package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import exceptions.EntidadeNaoEncontradaException;
import interfaces.RelatorioEvento;
import interfaces.RelatorioRecursos;
import interfaces.RelatorioTarefas;
import model.Evento;

public class MenuRelatorios {

    private static Scanner sc = new Scanner(System.in);
    private Sistema sistema;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public MenuRelatorios(Sistema sistema) {
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RELATORIOS");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Relatorio de Eventos");
            System.out.println("  2. Relatorio de Recursos");
            System.out.println("  3. Relatorio de Tarefas");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    relatorioEvento();
                    break;
                case 2:
                    relatorioRecursos();
                    break;
                case 3:
                    relatorioTarefas();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    return;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void relatorioEvento() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RELATORIO DE EVENTOS");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Resumo Geral de Todos os Eventos");
            System.out.println("  2. Eventos por Periodo");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            RelatorioEvento relatorio = new RelatorioEvento(sistema.getEventos());

            switch (opcao) {
                case 1:
                    relatorio.gerarResumo();
                    break;
                case 2:
                    listarPorPeriodo(relatorio);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void relatorioRecursos() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RELATORIO DE RECURSOS");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Resumo Geral de Uso de Recursos");
            System.out.println("  2. Uso de Recursos por Periodo");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            RelatorioRecursos relatorio = new RelatorioRecursos(sistema.getEventos());

            switch (opcao) {
                case 1:
                    relatorio.gerarResumo();
                    break;
                case 2:
                    listarPorPeriodo(relatorio);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void relatorioTarefas() {
        if (sistema.getEventos().isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        sistema.listarEventos();

        Evento evento;
        try {
            evento = sistema.buscarEventoPorCodigoComFeedback();
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
            return;
        }

        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RELATORIO DE TAREFAS");
            System.out.println("   Evento: " + evento.getNome());
            System.out.println("-----------------------------------------");
            System.out.println("  1. Resumo de Tarefas do Evento");
            System.out.println("  2. Tarefas Executadas por Periodo");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            RelatorioTarefas relatorio = new RelatorioTarefas(evento);

            switch (opcao) {
                case 1:
                    relatorio.gerarResumo();
                    break;
                case 2:
                    listarPorPeriodo(relatorio);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void listarPorPeriodo(interfaces.Relatorio relatorio) {
        Date inicio = lerData("Data de inicio (dd/MM/yyyy): ");
        if (inicio == null) {
            return;
        }
        Date fim = lerData("Data de fim (dd/MM/yyyy): ");
        if (fim == null) {
            return;
        }
        if (fim.before(inicio)) {
            System.out.println("Erro: data de fim nao pode ser anterior a data de inicio.");
            return;
        }
        relatorio.listarExecucoesTempo(inicio, fim);
    }

    private Date lerData(String prompt) {
        String entrada = Leitura.lerLinhaComPrompt(sc, prompt).trim();
        try {
            return fmt.parse(entrada);
        } catch (ParseException e) {
            System.out.println("Formato de data invalido. Use dd/MM/yyyy.");
            return null;
        }
    }
}
