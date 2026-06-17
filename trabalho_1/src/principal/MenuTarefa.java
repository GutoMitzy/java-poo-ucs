package principal;

import java.util.Scanner;

import exceptions.EntradaInvalidaException;
import exceptions.OperacaoInvalidaException;
import exceptions.OrdemTarefaException;
import exceptions.RecursoInvalidoException;
import model.Colaborador;
import model.Evento;
import model.Recurso;
import model.Tarefa;
import model.UsoRecurso;

public class MenuTarefa {

    private static Scanner sc = new Scanner(System.in);
    private Evento evento;
    private Tarefa tarefa;
    private Sistema sistema;

    public MenuTarefa(Tarefa tarefa, Evento evento, Sistema sistema) {
        this.tarefa = tarefa;
        this.evento = evento;
        this.sistema = sistema;
    }

    public void mostra() {
        int opcao;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("   TAREFA [" + tarefa.getCodigo() + "]: " + tarefa.getDescricao());
            System.out.println("   Status : " + tarefa.getStatus());
            System.out.println("   Inicio : " + (tarefa.getEt().getDataInicio() != null
                    ? sistema.formatarData(tarefa.getEt().getDataInicio()) : "Nao Informado"));
            System.out.println("   Fim    : " + (tarefa.getEt().getDataFim() != null
                    ? sistema.formatarData(tarefa.getEt().getDataFim()) : "Nao Informado"));

            if (!tarefa.getEt().getColaboradores().isEmpty()) {
                System.out.println("   Colaboradores:");
                for (Colaborador c : tarefa.getEt().getColaboradores()) {
                    System.out.printf("     - %-18s  Cargo: %-14s%n",
                            c.getNome(), c.getCargo());
                }
            }

            if (!tarefa.getEt().getRecursos().isEmpty()) {
                System.out.println("   Recursos:");
                for (UsoRecurso ur : tarefa.getEt().getRecursos()) {
                    System.out.printf("     - %-18s  Qtd: %-4d  Custo: R$ %.2f%n",
                            ur.getRecurso().getNome(), ur.getQtdUtilizada(), ur.calcularCusto());
                }
                System.out.printf("   Custo total: R$ %.2f%n", tarefa.getEt().calcularCustoTotal());
            }

            System.out.println("-----------------------------------------");
            System.out.println("  1. Atualizar Informacoes");
            System.out.println("  2. Adicionar Dependencia");
            System.out.println("  3. Remover Dependencia");
            System.out.println("  4. Adicionar Colaborador");
            System.out.println("  5. Remover Colaborador");
            System.out.println("  6. Adicionar Recurso");
            System.out.println("  7. Remover Recurso");
            System.out.println("  8. Concluir Tarefa");
            System.out.println("  0. Voltar");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            try {
                switch (opcao) {
                    case 1:
                        atualizarTarefa();
                        break;
                    case 2:
                        adicionarDependencia();
                        break;
                    case 3:
                        removerDependencia();
                        break;
                    case 4:
                        adicionarColaborador();
                        break;
                    case 5:
                        removerColaborador();
                        break;
                    case 6:
                        adicionarRecurso();
                        break;
                    case 7:
                        removerRecurso();
                        break;
                    case 8:
                        concluirTarefa();
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (RecursoInvalidoException e) {
                System.out.println("Erro de recurso: " + e.getMessage());
            } catch (OperacaoInvalidaException e) {
                System.out.println("Operacao invalida: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void atualizarTarefa() {
        System.out.println();
        System.out.println("-- Atualizar Tarefa (deixe em branco para manter) --");

        String descricao = Leitura.lerLinhaComPrompt(sc, "Descricao: ");
        if (!descricao.trim().isEmpty()) {
            tarefa.setDescricao(descricao.trim());
        }

        System.out.println("Status validos: planejada | em_execucao | concluida");
        String status = Leitura.lerLinhaComPrompt(sc, "Status: ").trim();
        if (!status.isEmpty()) {
            if (status.equalsIgnoreCase("planejada")
                    || status.equalsIgnoreCase("em_execucao")
                    || status.equalsIgnoreCase("concluida")) {
                tarefa.setStatus(status.toLowerCase());
            } else {
                System.out.println("Status invalido. Mantido o status atual.");
            }
        }

        sistema.registrarOperacao("Tarefa atualizada: [" + tarefa.getCodigo() + "] " + tarefa.getDescricao());
        sistema.autoSalvar();
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private void adicionarDependencia() throws OperacaoInvalidaException {
        if (evento.getTarefas().size() <= 1) {
            throw new OperacaoInvalidaException("Nao ha outras tarefas no evento para adicionar como dependencia.");
        }

        evento.listarTarefas();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo da tarefa dependencia: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        if (codigo == tarefa.getCodigo()) {
            System.out.println("Uma tarefa nao pode depender de si mesma.");
            return;
        }

        Tarefa dep = evento.buscarTarefa(codigo);
        if (dep == null) {
            System.out.println("Tarefa [" + codigo + "] nao encontrada.");
            return;
        }

        if (tarefa.getDep().getOrdem().contains(codigo)) {
            System.out.println("Essa dependencia ja foi adicionada.");
            return;
        }

        tarefa.adicionarDependencia(codigo);
        sistema.registrarOperacao("Dependencia adicionada a tarefa [" + tarefa.getCodigo() + "]: depende de [" + codigo + "]");
        sistema.autoSalvar();
        System.out.println("Dependencia adicionada com sucesso!");
    }

    private void removerDependencia() throws OperacaoInvalidaException {
        if (tarefa.getDep().getOrdem().isEmpty()) {
            throw new OperacaoInvalidaException("Esta tarefa nao possui dependencias.");
        }

        evento.listarDependencias(tarefa);

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo da dependencia a remover: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        if (!tarefa.getDep().getOrdem().contains(codigo)) {
            System.out.println("Dependencia [" + codigo + "] nao encontrada nesta tarefa.");
            return;
        }

        tarefa.removerDependencia(codigo);
        sistema.registrarOperacao("Dependencia [" + codigo + "] removida da tarefa [" + tarefa.getCodigo() + "]");
        sistema.autoSalvar();
        System.out.println("Dependencia removida com sucesso!");
    }

    private void adicionarColaborador() throws OperacaoInvalidaException {
        if (sistema.getColaboradores().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhum colaborador cadastrado no sistema.");
        }

        sistema.listarColaboradores();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Colaborador colaborador = sistema.buscarColaborador(codigo);
        if (colaborador == null) {
            System.out.println("Colaborador [" + codigo + "] nao encontrado.");
            return;
        }

        tarefa.getEt().adicionarColaborador(colaborador);
        evento.adicionarColaborador(colaborador);
        sistema.registrarOperacao("Colaborador [" + colaborador.getCodigo() + "] " + colaborador.getNome()
                + " adicionado a tarefa [" + tarefa.getCodigo() + "]");
        sistema.autoSalvar();
        System.out.println("Colaborador adicionado com sucesso!");
    }

    private void removerColaborador() throws OperacaoInvalidaException {
        if (tarefa.getEt().getColaboradores().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhum colaborador associado a esta tarefa.");
        }

        System.out.println();
        System.out.println("-- Colaboradores desta Tarefa --");
        System.out.printf("  %-4s  %-18s  %-14s%n", "Cod", "Nome", "Cargo");
        System.out.println("  -----------------------------------------------");
        for (Colaborador c : tarefa.getEt().getColaboradores()) {
            System.out.printf("  %-4d  %-18s  %-14s%n", c.getCodigo(), c.getNome(), c.getCargo());
        }

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador a remover: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Colaborador colaborador = sistema.buscarColaborador(codigo);
        if (colaborador == null) {
            System.out.println("Colaborador [" + codigo + "] nao encontrado.");
            return;
        }

        tarefa.getEt().removerColaborador(colaborador);
        sistema.registrarOperacao("Colaborador [" + colaborador.getCodigo() + "] removido da tarefa [" + tarefa.getCodigo() + "]");
        sistema.autoSalvar();
        System.out.println("Colaborador removido com sucesso!");
    }

    private void adicionarRecurso() throws RecursoInvalidoException, OperacaoInvalidaException {
        if (sistema.getRecursos().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhum recurso cadastrado no sistema.");
        }

        sistema.listarRecursos();

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Recurso recurso = sistema.buscarRecurso(codigo);
        if (recurso == null) {
            System.out.println("Recurso [" + codigo + "] nao encontrado.");
            return;
        }

        if (recurso.getEstoque() <= 0) {
            throw new RecursoInvalidoException("Recurso '" + recurso.getNome() + "' sem estoque disponivel.");
        }

        System.out.println("Estoque disponivel: " + recurso.getEstoque());

        int quantidade;
        try {
            quantidade = Leitura.lerIntComPrompt(sc, "Quantidade a utilizar: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Quantidade deve ser maior que zero.");
            return;
        }

        tarefa.getEt().adicionarRecurso(recurso, quantidade);
        evento.adicionarRecurso(recurso);
        sistema.registrarOperacao("Recurso [" + recurso.getCodigo() + "] " + recurso.getNome()
                + " (qtd: " + quantidade + ") adicionado a tarefa [" + tarefa.getCodigo() + "]");
        sistema.autoSalvar();
        System.out.println("Recurso adicionado. Estoque restante: " + recurso.getEstoque());
    }

    private void removerRecurso() throws RecursoInvalidoException, OperacaoInvalidaException {
        if (tarefa.getEt().getRecursos().isEmpty()) {
            throw new OperacaoInvalidaException("Nenhum recurso associado a esta tarefa.");
        }

        System.out.println();
        System.out.println("-- Recursos desta Tarefa --");
        System.out.printf("  %-4s  %-18s  %-6s  %-12s%n", "Cod", "Nome", "Qtd", "Custo Total");
        System.out.println("  -----------------------------------------------");
        for (UsoRecurso ur : tarefa.getEt().getRecursos()) {
            System.out.printf("  %-4d  %-18s  %-6d  R$ %.2f%n",
                    ur.getRecurso().getCodigo(), ur.getRecurso().getNome(),
                    ur.getQtdUtilizada(), ur.calcularCusto());
        }

        int codigo;
        try {
            codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso a remover: ");
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Recurso recurso = sistema.buscarRecurso(codigo);
        if (recurso == null) {
            System.out.println("Recurso [" + codigo + "] nao encontrado.");
            return;
        }

        tarefa.getEt().removerRecurso(recurso);
        sistema.registrarOperacao("Recurso [" + recurso.getCodigo() + "] removido da tarefa [" + tarefa.getCodigo() + "]");
        sistema.autoSalvar();
        System.out.println("Recurso removido. Estoque restante: " + recurso.getEstoque());
    }

    private void concluirTarefa() {
        try {
            tarefa.concluirTarefa(evento.getTarefas());
            sistema.registrarOperacao("Tarefa concluida: [" + tarefa.getCodigo() + "] " + tarefa.getDescricao());
            sistema.autoSalvar();
        } catch (OrdemTarefaException e) {
            System.out.println("Nao foi possivel concluir: " + e.getMessage());
        }
    }
}
