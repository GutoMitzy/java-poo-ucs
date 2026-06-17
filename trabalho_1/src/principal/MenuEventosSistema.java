package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import model.Evento;

public class MenuEventosSistema {

    private static Scanner sc = new Scanner(System.in);
    private Sistema sistema;
    private MenuEvento menuEvento;
    private static int proximoCodigoEvento = 0;
    private SimpleDateFormat fmtData = new SimpleDateFormat("dd/MM/yyyy");

    public MenuEventosSistema() {}

    public MenuEventosSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  EVENTOS");
            System.out.println("-----------------------------------------");
            System.out.println("  1. Cadastrar Evento");
            System.out.println("  2. Excluir Evento");
            System.out.println("  3. Acessar Evento");
            System.out.println("  4. Listar Eventos");
            System.out.println("  5. Buscar por Codigo");
            System.out.println("  6. Buscar por Nome");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    cadastrarEvento();
                    break;
                case 2:
                    excluirEvento();
                    break;
                case 3:
                    acessarEvento();
                    break;
                case 4:
                    sistema.listarEventos();
                    break;
                case 5:
                    sistema.buscarEventoPorCodigo();
                    break;
                case 6:
                    sistema.buscarEventoPorNome();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    return;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    public void cadastrarEvento() {
        System.out.println();
        System.out.println("-- Cadastrar Evento --");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome nao pode ser vazio.");
            return;
        }

        String descricao = Leitura.lerLinhaComPrompt(sc, "Descricao: ");
        String tipo = Leitura.lerLinhaComPrompt(sc, "Tipo: ");

        Date dataInicio;
        try {
            String d1 = Leitura.lerLinhaComPrompt(sc, "Data de inicio (dd/MM/yyyy): ").trim();
            dataInicio = fmtData.parse(d1);
        } catch (ParseException e) {
            System.out.println("Erro: data de inicio invalida. Use o formato dd/MM/yyyy.");
            return;
        }

        Date dataFim;
        try {
            String d2 = Leitura.lerLinhaComPrompt(sc, "Data de fim (dd/MM/yyyy): ").trim();
            dataFim = fmtData.parse(d2);
        } catch (ParseException e) {
            System.out.println("Erro: data de fim invalida. Use o formato dd/MM/yyyy.");
            return;
        }

        if (dataFim.before(dataInicio)) {
            System.out.println("Erro: data de fim nao pode ser anterior a data de inicio.");
            return;
        }

        int codigo = proximoCodigoEvento++;
        Evento novoEvento = new Evento(codigo, nome.trim(), descricao, tipo, dataInicio, dataFim);
        sistema.getEventos().add(novoEvento);
        sistema.registrarOperacao("Evento criado: [" + codigo + "] " + nome);
        sistema.autoSalvar();
        System.out.println("Evento '" + nome + "' criado com codigo " + codigo + ".");
    }

    private void excluirEvento() {
        System.out.println();
        System.out.println("-- Excluir Evento --");

        if (sistema.getEventos().isEmpty()) {
            System.out.println("Nenhum evento cadastrado para excluir.");
            return;
        }

        sistema.listarEventos();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do evento a excluir: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Evento evento = sistema.buscarEvento(codigo);
        if (evento == null) {
            System.out.println("Evento [" + codigo + "] nao encontrado.");
            return;
        }

        sistema.getEventos().remove(evento);
        sistema.registrarOperacao("Evento excluido: [" + codigo + "] " + evento.getNome());
        sistema.autoSalvar();
        System.out.println("Evento '" + evento.getNome() + "' excluido com sucesso!");
    }

    private void acessarEvento() {
        if (sistema.getEventos().isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        sistema.listarEventos();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do evento: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Evento evento = sistema.buscarEvento(codigo);
        if (evento == null) {
            System.out.println("Evento [" + codigo + "] nao encontrado.");
            return;
        }

        this.menuEvento = new MenuEvento(evento, sistema);
        menuEvento.mostra();
    }
}
