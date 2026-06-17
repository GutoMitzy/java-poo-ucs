package principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import model.Colaborador;
import model.Evento;
import model.Recurso;

public class MenuEvento {

    private static Scanner sc = new Scanner(System.in);
    private Evento evento;
    private Sistema sistema;
    private MenuTarefaEvento menuTarefaEvento;
    private SimpleDateFormat fmtData = new SimpleDateFormat("dd/MM/yyyy");

    public MenuEvento(Evento evento, Sistema sistema) {
        this.evento = evento;
        this.sistema = sistema;
        this.menuTarefaEvento = new MenuTarefaEvento(evento, sistema);
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("   EVENTO: [" + evento.getCodigo() + "] " + evento.getNome());
            System.out.println("   Tipo  : " + evento.getTipo());
            System.out.println("   " + evento.getAndamento());
            System.out.println("   " + sistema.formatarData(evento.getDataInicio())
                    + "  ->  " + sistema.formatarData(evento.getDataFim()));
            System.out.println("-----------------------------------------");
            System.out.println("  1. Atualizar Informacoes");
            System.out.println("  2. Gerenciar Tarefas");
            System.out.println("  3. Listar Colaboradores do Evento");
            System.out.println("  4. Buscar Colaborador por Codigo");
            System.out.println("  5. Buscar Colaborador por Nome");
            System.out.println("  6. Listar Recursos do Evento");
            System.out.println("  7. Buscar Recurso por Codigo");
            System.out.println("  8. Buscar Recurso por Nome");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    atualizarEvento();
                    break;
                case 2:
                    menuTarefaEvento.mostra();
                    break;
                case 3:
                    listarColaboradorEvento();
                    break;
                case 4:
                    buscarColaboradorEventoCodigo();
                    break;
                case 5:
                    buscarColaboradorEventoNome();
                    break;
                case 6:
                    listarRecursoEvento();
                    break;
                case 7:
                    buscarRecursoEventoCodigo();
                    break;
                case 8:
                    buscarRecursoEventoNome();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void atualizarEvento() {
        System.out.println();
        System.out.println("-- Atualizar Evento (deixe em branco para manter) --");

        String nome = Leitura.lerLinhaComPrompt(sc, "Nome: ");
        if (!nome.trim().isEmpty()) {
            evento.setNome(nome.trim());
        }

        String descricao = Leitura.lerLinhaComPrompt(sc, "Descricao: ");
        if (!descricao.trim().isEmpty()) {
            evento.setDescricao(descricao.trim());
        }

        String tipo = Leitura.lerLinhaComPrompt(sc, "Tipo: ");
        if (!tipo.trim().isEmpty()) {
            evento.setTipo(tipo.trim());
        }

        String d1str = Leitura.lerLinhaComPrompt(sc, "Data de inicio (dd/MM/yyyy): ").trim();
        Date dataInicio = null;
        if (!d1str.isEmpty()) {
            try {
                dataInicio = fmtData.parse(d1str);
            } catch (ParseException e) {
                System.out.println("Data de inicio invalida, mantida a atual.");
            }
        }

        String d2str = Leitura.lerLinhaComPrompt(sc, "Data de fim (dd/MM/yyyy): ").trim();
        Date dataFim = null;
        if (!d2str.isEmpty()) {
            try {
                dataFim = fmtData.parse(d2str);
            } catch (ParseException e) {
                System.out.println("Data de fim invalida, mantida a atual.");
            }
        }

        if (dataInicio != null && dataFim != null && dataFim.before(dataInicio)) {
            System.out.println("Erro: data de fim nao pode ser anterior a data de inicio. Datas nao alteradas.");
        } else {
            if (dataInicio != null) {
                evento.setDataInicio(dataInicio);
            }
            if (dataFim != null) {
                evento.setDataFim(dataFim);
            }
        }

        sistema.registrarOperacao("Evento atualizado: [" + evento.getCodigo() + "] " + evento.getNome());
        sistema.autoSalvar();
        System.out.println("Evento atualizado com sucesso!");
    }

    private void listarColaboradorEvento() {
        List<Colaborador> colaboradores = evento.getColaboradores();
        System.out.println();
        System.out.println("-- Colaboradores do Evento: " + evento.getNome() + " --");
        if (colaboradores.isEmpty()) {
            System.out.println("  Nenhum colaborador associado a este evento.");
            return;
        }
        System.out.printf("  %-4s  %-18s  %-14s  %-22s%n", "Cod", "Nome", "Cargo", "Email");
        System.out.println("  ---------------------------------------------------------------");
        for (Colaborador c : colaboradores) {
            System.out.printf("  %-4d  %-18s  %-14s  %-22s%n",
                    c.getCodigo(), c.getNome(), c.getCargo(), c.getEmail());
        }
        System.out.println("  Total: " + colaboradores.size() + " colaborador(es)");
    }

    private void listarRecursoEvento() {
        List<Recurso> recursos = evento.getRecursos();
        System.out.println();
        System.out.println("-- Recursos do Evento: " + evento.getNome() + " --");
        if (recursos.isEmpty()) {
            System.out.println("  Nenhum recurso associado a este evento.");
            return;
        }
        System.out.printf("  %-4s  %-18s  %-14s  %-10s  %-7s%n", "Cod", "Nome", "Tipo", "Custo Unit.", "Estoque");
        System.out.println("  ---------------------------------------------------------------");
        for (Recurso r : recursos) {
            System.out.printf("  %-4d  %-18s  %-14s  R$ %-7.2f  %-7d%n",
                    r.getCodigo(), r.getNome(), r.getTipo(), r.getCustoUnitario(), r.getEstoque());
        }
        System.out.println("  Total: " + recursos.size() + " recurso(s)");
    }

    private void buscarColaboradorEventoCodigo() {
        try {
            int codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador: ");
            Colaborador c = buscarColaborador(codigo);
            if (c == null) {
                System.out.println("Colaborador [" + codigo + "] nao encontrado neste evento.");
                return;
            }
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  COLABORADOR ENCONTRADO");
            System.out.println("-----------------------------------------");
            System.out.println("  Codigo      : " + c.getCodigo());
            System.out.println("  Nome        : " + c.getNome());
            System.out.println("  CPF         : " + c.getCpf());
            System.out.println("  Telefone    : " + c.getTelefone());
            System.out.println("  Email       : " + c.getEmail());
            System.out.println("  Cargo       : " + c.getCargo());
            System.out.printf("  Remuneracao : R$ %.2f%n", c.getRemuneracao());
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarColaboradorEventoNome() {
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome do colaborador: ");
        LinkedList<Colaborador> encontrados = buscarColaborador(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum colaborador encontrado com esse nome.");
            return;
        }
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  COLABORADORES ENCONTRADOS");
        System.out.println("-----------------------------------------");
        for (Colaborador c : encontrados) {
            System.out.println("  Codigo      : " + c.getCodigo());
            System.out.println("  Nome        : " + c.getNome());
            System.out.println("  CPF         : " + c.getCpf());
            System.out.println("  Telefone    : " + c.getTelefone());
            System.out.println("  Email       : " + c.getEmail());
            System.out.println("  Cargo       : " + c.getCargo());
            System.out.printf("  Remuneracao : R$ %.2f%n", c.getRemuneracao());
            System.out.println("  -----------------------------------------------");
        }
    }

    private Colaborador buscarColaborador(int codigo) {
        for (Colaborador c : evento.getColaboradores()) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    private LinkedList<Colaborador> buscarColaborador(String nome) {
        LinkedList<Colaborador> result = new LinkedList<>();
        String filtro = nome.toLowerCase();
        for (Colaborador c : evento.getColaboradores()) {
            if (c.getNome().toLowerCase().contains(filtro)) {
                result.add(c);
            }
        }
        return result;
    }

    private void buscarRecursoEventoCodigo() {
        try {
            int codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso: ");
            Recurso r = buscarRecurso(codigo);
            if (r == null) {
                System.out.println("Recurso [" + codigo + "] nao encontrado neste evento.");
                return;
            }
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  RECURSO ENCONTRADO");
            System.out.println("-----------------------------------------");
            System.out.println("  Codigo      : " + r.getCodigo());
            System.out.println("  Nome        : " + r.getNome());
            System.out.println("  Tipo        : " + r.getTipo());
            System.out.printf("  Custo Unit. : R$ %.2f%n", r.getCustoUnitario());
            System.out.println("  Estoque     : " + r.getEstoque());
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarRecursoEventoNome() {
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome do recurso: ");
        LinkedList<Recurso> encontrados = buscarRecurso(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum recurso encontrado com esse nome.");
            return;
        }
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  RECURSOS ENCONTRADOS");
        System.out.println("-----------------------------------------");
        for (Recurso r : encontrados) {
            System.out.println("  Codigo      : " + r.getCodigo());
            System.out.println("  Nome        : " + r.getNome());
            System.out.println("  Tipo        : " + r.getTipo());
            System.out.printf("  Custo Unit. : R$ %.2f%n", r.getCustoUnitario());
            System.out.println("  Estoque     : " + r.getEstoque());
            System.out.println("  -----------------------------------------------");
        }
    }

    private Recurso buscarRecurso(int codigo) {
        for (Recurso r : evento.getRecursos()) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    private LinkedList<Recurso> buscarRecurso(String nome) {
        LinkedList<Recurso> result = new LinkedList<>();
        String filtro = nome.toLowerCase();
        for (Recurso r : evento.getRecursos()) {
            if (r.getNome().toLowerCase().contains(filtro)) {
                result.add(r);
            }
        }
        return result;
    }
}
