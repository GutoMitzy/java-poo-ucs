package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import exceptions.EntradaInvalidaException;

public class Evento implements Serializable {

    private int codigo;
    private String nome;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private String tipo;

    private List<Tarefa> tarefas;
    private List<Colaborador> colaboradores;
    private List<Recurso> recursos;

    private transient Scanner sc;
    private transient SimpleDateFormat fmt;
    private static final long serialVersionUID = 1L;

    public Evento() {
        this.tarefas = new LinkedList<>();
        this.colaboradores = new LinkedList<>();
        this.recursos = new LinkedList<>();
    }

    public Evento(int codigo, String nome, String descricao, String tipo, Date dataInicio, Date dataFim) {
        this();
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    private String formatarData(Date data) {
        if (data == null) {
            return "Nao informada";
        }
        if (fmt == null) {
            fmt = new SimpleDateFormat("dd/MM/yyyy");
        }
        return fmt.format(data);
    }

    private Scanner getScanner() {
        if (sc == null) {
            sc = new Scanner(System.in);
        }
        return sc;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void adicionarTarefa(Tarefa t) {
        t.setCodigo(tarefas.size());
        tarefas.add(t);
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void removerTarefa(int codigo) {
        Tarefa tarefa = buscarTarefa(codigo);
        if (tarefa != null) {
            try {
                for (UsoRecurso ur : tarefa.getEt().getRecursos()) {
                    ur.devolverAoEstoque();
                }
            } catch (Exception e) {
                System.out.println("Aviso: erro ao devolver recursos ao estoque: " + e.getMessage());
            }
        }
        tarefas.removeIf(t -> t.getCodigo() == codigo);
        for (int i = 0; i < tarefas.size(); i++) {
            tarefas.get(i).setCodigo(i);
        }
    }

    public List<Colaborador> getColaboradores() {
        if (colaboradores == null) {
            colaboradores = new LinkedList<>();
        }
        return colaboradores;
    }

    public void adicionarColaborador(Colaborador c) {
        if (c != null && !getColaboradores().contains(c)) {
            getColaboradores().add(c);
        }
    }

    public void removerColaborador(int codigo) {
        if (colaboradores != null) {
            colaboradores.removeIf(c -> c.getCodigo() == codigo);
        }
    }

    public Colaborador buscarColaboradorPorNome(String nome) {
        for (Colaborador c : getColaboradores()) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    public List<Recurso> getRecursos() {
        if (recursos == null) {
            recursos = new LinkedList<>();
        }
        return recursos;
    }

    public void adicionarRecurso(Recurso r) {
        if (r != null && !getRecursos().contains(r)) {
            getRecursos().add(r);
        }
    }

    public void removerRecurso(int codigo) {
        if (recursos != null) {
            recursos.removeIf(r -> r.getCodigo() == codigo);
        }
    }

    public void removerRecurso(Recurso recurso) {
        if (recursos != null) {
            recursos.remove(recurso);
        }
    }

    public Recurso buscarRecursoPorCodigo(int codigo) {
        for (Recurso r : getRecursos()) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public String getAndamento() {
        int total = getTarefas().size();
        long concluidas = getTarefas().stream().filter(t -> "concluida".equalsIgnoreCase(t.getStatus())).count();
        long emExecucao = getTarefas().stream().filter(t -> "em_execucao".equalsIgnoreCase(t.getStatus())).count();
        long planejadas = getTarefas().stream().filter(t -> "planejada".equalsIgnoreCase(t.getStatus())).count();
        return String.format("Andamento: %d/%d concluidas | %d em execucao | %d planejadas", concluidas, total, emExecucao, planejadas);
    }

    public void listarTarefas() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  TAREFAS DO EVENTO: " + nome);
        System.out.println("-----------------------------------------");
        if (tarefas.isEmpty()) {
            System.out.println("  Nenhuma tarefa cadastrada.");
            return;
        }
        System.out.printf("  %-4s  %-28s  %-12s  %-12s  %-12s%n", "Cod", "Descricao", "Status", "Inicio", "Fim");
        System.out.println("  ---------------------------------------------------------------");
        for (Tarefa t : tarefas) {
            System.out.printf("  %-4d  %-28s  %-12s  %-12s  %-12s%n",
                t.getCodigo(),
                t.getDescricao(),
                t.getStatus(),
                formatarData(t.getEt().getDataInicio()),
                formatarData(t.getEt().getDataFim()));
        }
        System.out.println("  Total: " + tarefas.size() + " tarefa(s)");
    }

    public void buscarTarefaEventoCodigo() {
        System.out.print("Codigo da tarefa: ");
        int codigo;
        try {
            codigo = principal.Leitura.lerInt(getScanner());
        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Tarefa tarefa = buscarTarefa(codigo);
        if (tarefa == null) {
            System.out.println("Tarefa [" + codigo + "] nao encontrada.");
            return;
        }

        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  TAREFA ENCONTRADA");
        System.out.println("-----------------------------------------");
        System.out.println("  Codigo    : " + tarefa.getCodigo());
        System.out.println("  Descricao : " + tarefa.getDescricao());
        System.out.println("  Status    : " + tarefa.getStatus());
        System.out.println("  Inicio    : " + formatarData(tarefa.getEt().getDataInicio()));
        System.out.println("  Fim       : " + formatarData(tarefa.getEt().getDataFim()));

        if (!tarefa.getEt().getColaboradores().isEmpty()) {
            System.out.println("  Colaboradores: " + tarefa.getEt().getColaboradores().size());
        }

        if (!tarefa.getEt().getRecursos().isEmpty()) {
            System.out.println("  Recursos:");
            for (UsoRecurso ur : tarefa.getEt().getRecursos()) {
                System.out.printf("    - %-18s  Qtd: %-4d  Custo: R$ %.2f%n",
                    ur.getRecurso().getNome(), ur.getQtdUtilizada(), ur.calcularCusto());
            }
            System.out.printf("  Custo total: R$ %.2f%n", tarefa.getEt().calcularCustoTotal());
        }
    }

    public void buscarTarefaEventoNome() {
        System.out.print("Descricao da tarefa: ");
        String nome = getScanner().nextLine();

        LinkedList<Tarefa> resultado = buscarTarefa(nome);
        if (resultado.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com esse nome.");
            return;
        }

        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  TAREFAS ENCONTRADAS");
        System.out.println("-----------------------------------------");

        for (Tarefa t : resultado) {
            System.out.println("  Codigo    : " + t.getCodigo());
            System.out.println("  Descricao : " + t.getDescricao());
            System.out.println("  Status    : " + t.getStatus());
            System.out.println("  Inicio    : " + formatarData(t.getEt().getDataInicio()));
            System.out.println("  Fim       : " + formatarData(t.getEt().getDataFim()));

            if (!t.getEt().getColaboradores().isEmpty()) {
                System.out.println("  Colaboradores: " + t.getEt().getColaboradores().size());
            }

            if (!t.getEt().getRecursos().isEmpty()) {
                System.out.println("  Recursos:");
                for (UsoRecurso ur : t.getEt().getRecursos()) {
                    System.out.printf("    - %-18s  Qtd: %-4d  Custo: R$ %.2f%n",
                        ur.getRecurso().getNome(), ur.getQtdUtilizada(), ur.calcularCusto());
                }
                System.out.printf("  Custo total: R$ %.2f%n", t.getEt().calcularCustoTotal());
            }

            System.out.println("  -----------------------------------------------");
        }
    }

    public Tarefa buscarTarefa(int codigo) {
        for (Tarefa t : tarefas) {
            if (t.getCodigo() == codigo) {
                return t;
            }
        }
        return null;
    }

    public LinkedList<Tarefa> buscarTarefa(String nome) {
        LinkedList<Tarefa> resultado = new LinkedList<>();
        String filtro = nome.toLowerCase();
        for (Tarefa t : tarefas) {
            if (t.getDescricao().toLowerCase().contains(filtro)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public void listarDependencias(Tarefa tarefa) {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  DEPENDENCIAS DA TAREFA [" + tarefa.getCodigo() + "]");
        System.out.println("-----------------------------------------");
        if (tarefa.getDep().getOrdem().isEmpty()) {
            System.out.println("  Nenhuma dependencia cadastrada.");
            return;
        }
        System.out.printf("  %-4s  %-28s  %-12s%n", "Cod", "Descricao", "Status");
        System.out.println("  -----------------------------------------------");
        for (int n : tarefa.getDep().getOrdem()) {
            for (Tarefa t : tarefas) {
                if (t.getCodigo() == n) {
                    System.out.printf("  %-4d  %-28s  %-12s%n", t.getCodigo(), t.getDescricao(), t.getStatus());
                }
            }
        }
    }

    @Override
    public String toString() {
        if (fmt == null) {
            fmt = new SimpleDateFormat("dd/MM/yyyy");
        }
        return "INFORMACOES GERAIS\nnome: " + nome + "\ncodigo: " + codigo
                + "\ndescricao: " + descricao
                + "\ndataInicio: " + formatarData(dataInicio)
                + "\ndataFim: " + formatarData(dataFim)
                + "\ntipo: " + tipo
                + "\n" + getAndamento()
                + "\nTAREFAS ASSOCIADAS:\n-----> " + tarefas;
    }
}
