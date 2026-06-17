package principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import exceptions.EntidadeNaoEncontradaException;
import model.Colaborador;
import model.Empresa;
import model.Evento;
import model.Recurso;

public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String DIR_CONFIG = "config";
    private static final String ARQUIVO_DADOS = DIR_CONFIG + File.separator + "dados.ser";
    private static final int MAX_HISTORICO = 20;

    private static transient Scanner sc = new Scanner(System.in);

    private Empresa emp;
    private List<Evento> eventos;
    private List<String> historicoOperacoes;
    private List<Recurso> recursos;
    private List<Colaborador> colaboradores;

    private transient MenuEventosSistema menuEventosSistema;
    private transient MenuColaboradoresSistema menuColaboradoresSistema;
    private transient MenuRecursosSistema menuRecursosSistema;
    private transient MenuRelatorios menuRelatoriosSistema;
    private transient SimpleDateFormat fmtData;

    public static void main(String[] args) {
        Sistema sistema = carregarOuCriar();
        sistema.inicializarMenus();
        sistema.mostra();
    }

    private static Sistema carregarOuCriar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Sistema s = (Sistema) ois.readObject();
                System.out.println("Dados carregados com sucesso.");
                return s;
            } catch (Exception e) {
                System.out.println("Erro ao carregar dados: " + e.getMessage() + ". Iniciando novo sistema.");
            }
        }
        return new Sistema();
    }

    public Sistema() {
        this.eventos = new LinkedList<>();
        this.historicoOperacoes = new LinkedList<>();
        this.recursos = new LinkedList<>();
        this.colaboradores = new LinkedList<>();
    }

    private void inicializarMenus() {
        if (sc == null) {
            sc = new Scanner(System.in);
        }
        if (fmtData == null) {
            fmtData = new SimpleDateFormat("dd/MM/yyyy");
        }
        this.menuEventosSistema = new MenuEventosSistema(this);
        this.menuColaboradoresSistema = new MenuColaboradoresSistema(this);
        this.menuRecursosSistema = new MenuRecursosSistema(this);
        this.menuRelatoriosSistema = new MenuRelatorios(this);
    }

    public void mostra() {
        inicializarMenus();
        int opcao = 0;
        do {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  SISTEMA DE GESTAO DE EVENTOS");
            System.out.println("  Empresa: " + getEmpresa());
            System.out.println("-----------------------------------------");
            System.out.println("  1. Cadastrar Empresa");
            System.out.println("  2. Acessar Eventos");
            System.out.println("  3. Acessar Colaboradores");
            System.out.println("  4. Acessar Recursos");
            System.out.println("  5. Acessar Relatorios");
            System.out.println("  6. Ver Historico de Operacoes");
            System.out.println("  0. Sair");
            System.out.println("-----------------------------------------");

            opcao = Leitura.lerOpcaoMenu(sc);

            switch (opcao) {
                case 1:
                    cadastrarEmpresa();
                    break;
                case 2:
                    menuEventosSistema.mostra();
                    break;
                case 3:
                    menuColaboradoresSistema.mostra();
                    break;
                case 4:
                    menuRecursosSistema.mostra();
                    break;
                case 5:
                    menuRelatoriosSistema.mostra();
                    break;
                case 6:
                    verHistoricoOperacoes();
                    break;
                case 0:
                    registrarOperacao("Sistema encerrado.");
                    autoSalvar();
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    public void cadastrarEmpresa() {
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome da empresa: ");
        if (nome.trim().isEmpty()) {
            System.out.println("Nome nao pode ser vazio.");
            return;
        }
        this.emp = new Empresa(nome.trim());
        this.emp.setCodigo(1);
        registrarOperacao("Empresa cadastrada: " + nome);
        autoSalvar();
        System.out.println("Empresa '" + nome + "' cadastrada com sucesso!");
    }

    public String getEmpresa() {
        if (emp == null) {
            return "-nao conectado-";
        }
        return emp.getNome();
    }

    public void verHistoricoOperacoes() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  HISTORICO DE OPERACOES (ultimas " + MAX_HISTORICO + ")");
        System.out.println("-----------------------------------------");
        if (historicoOperacoes.isEmpty()) {
            System.out.println("  Nenhuma operacao registrada.");
            return;
        }
        for (int i = 0; i < historicoOperacoes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + historicoOperacoes.get(i));
        }
    }

    public void registrarOperacao(String descricao) {
        if (fmtData == null) {
            fmtData = new SimpleDateFormat("dd/MM/yyyy");
        }
        String registro = "[" + fmtData.format(new Date()) + "] " + descricao;
        historicoOperacoes.add(registro);
        while (historicoOperacoes.size() > MAX_HISTORICO) {
            historicoOperacoes.remove(0);
        }
    }

    public String formatarData(Date data) {
        if (data == null) {
            return "Nao informada";
        }
        if (fmtData == null) {
            fmtData = new SimpleDateFormat("dd/MM/yyyy");
        }
        return fmtData.format(data);
    }

    public void autoSalvar() {
        try {
            File dir = new File(DIR_CONFIG);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            System.out.println("Aviso: nao foi possivel salvar os dados. " + e.getMessage());
        }
    }

    public void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            System.out.println("Nenhum arquivo de dados encontrado.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Sistema s = (Sistema) ois.readObject();
            this.emp = s.emp;
            this.eventos = s.eventos;
            this.historicoOperacoes = s.historicoOperacoes;
            this.recursos = s.recursos;
            this.colaboradores = s.colaboradores;
            System.out.println("Dados carregados com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void listarEventos() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  EVENTOS");
        System.out.println("-----------------------------------------");
        if (eventos.isEmpty()) {
            System.out.println("  Nenhum evento cadastrado.");
            return;
        }
        System.out.printf("  %-4s  %-22s  %-12s  %-12s  %-10s%n", "Cod", "Nome", "Tipo", "Inicio", "Fim");
        System.out.println("  ---------------------------------------------------------------");
        for (Evento e : eventos) {
            System.out.printf("  %-4d  %-22s  %-12s  %-12s  %-10s%n",
                e.getCodigo(),
                e.getNome(),
                e.getTipo(),
                formatarData(e.getDataInicio()),
                formatarData(e.getDataFim()));
        }
        System.out.println("  Total: " + eventos.size() + " evento(s)");
    }

    public Evento buscarEvento(int codigo) {
        for (Evento e : eventos) {
            if (e.getCodigo() == codigo) {
                return e;
            }
        }
        return null;
    }

    public Evento buscarEventoPorCodigoComFeedback() throws EntidadeNaoEncontradaException {
        try {
            int codigo = Leitura.lerIntComPrompt(sc, "Codigo do evento: ");
            Evento e = buscarEvento(codigo);
            if (e == null) {
                throw new EntidadeNaoEncontradaException("Evento [" + codigo + "] nao encontrado.");
            }
            return e;
        } catch (exceptions.EntradaInvalidaException ex) {
            throw new EntidadeNaoEncontradaException("Codigo invalido: " + ex.getMessage());
        }
    }

    public Evento buscarEventoPorCodigo() {
        try {
            return buscarEventoPorCodigoComFeedback();
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public LinkedList<Evento> buscarEventoPorNome() {
        LinkedList<Evento> busca = new LinkedList<>();
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome do evento: ").toLowerCase();
        for (Evento e : eventos) {
            if (e.getNome().toLowerCase().contains(nome)) {
                busca.add(e);
            }
        }
        if (busca.isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
        } else {
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("  EVENTOS ENCONTRADOS");
            System.out.println("-----------------------------------------");
            for (Evento e : busca) {
                System.out.println("  Codigo    : " + e.getCodigo());
                System.out.println("  Nome      : " + e.getNome());
                System.out.println("  Descricao : " + e.getDescricao());
                System.out.println("  Tipo      : " + e.getTipo());
                System.out.println("  Inicio    : " + formatarData(e.getDataInicio()));
                System.out.println("  Fim       : " + formatarData(e.getDataFim()));
                System.out.println("  " + e.getAndamento());
                System.out.println("  -----------------------------------------------");
            }
        }
        return busca;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void listarRecursos() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  RECURSOS");
        System.out.println("-----------------------------------------");
        if (recursos.isEmpty()) {
            System.out.println("  Nenhum recurso cadastrado.");
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

    public Recurso buscarRecurso(int codigo) {
        for (Recurso r : recursos) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public LinkedList<Recurso> buscarRecurso(String nome) {
        LinkedList<Recurso> busca = new LinkedList<>();
        String filtro = nome.toLowerCase();
        for (Recurso r : recursos) {
            if (r.getNome().toLowerCase().contains(filtro)) {
                busca.add(r);
            }
        }
        return busca;
    }

    public void buscarRecursoSistemaCodigo() {
        try {
            int codigo = Leitura.lerIntComPrompt(sc, "Codigo do recurso: ");
            Recurso r = buscarRecurso(codigo);
            if (r == null) {
                System.out.println("Recurso [" + codigo + "] nao encontrado.");
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
        } catch (exceptions.EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarRecursoSistemaNome() {
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome do recurso: ");
        LinkedList<Recurso> resultado = buscarRecurso(nome);
        if (resultado.isEmpty()) {
            System.out.println("Nenhum recurso encontrado.");
            return;
        }
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  RECURSOS ENCONTRADOS");
        System.out.println("-----------------------------------------");
        for (Recurso r : resultado) {
            System.out.println("  Codigo      : " + r.getCodigo());
            System.out.println("  Nome        : " + r.getNome());
            System.out.println("  Tipo        : " + r.getTipo());
            System.out.printf("  Custo Unit. : R$ %.2f%n", r.getCustoUnitario());
            System.out.println("  Estoque     : " + r.getEstoque());
            System.out.println("  -----------------------------------------------");
        }
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void listarColaboradores() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  COLABORADORES");
        System.out.println("-----------------------------------------");
        if (colaboradores.isEmpty()) {
            System.out.println("  Nenhum colaborador cadastrado.");
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

    public Colaborador buscarColaborador(int codigo) {
        for (Colaborador c : colaboradores) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    public LinkedList<Colaborador> buscarColaborador(String nome) {
        LinkedList<Colaborador> busca = new LinkedList<>();
        String filtro = nome.toLowerCase();
        for (Colaborador c : colaboradores) {
            if (c.getNome().toLowerCase().contains(filtro)) {
                busca.add(c);
            }
        }
        return busca;
    }

    public void buscarColaboradorSistemaCodigo() {
        try {
            int codigo = Leitura.lerIntComPrompt(sc, "Codigo do colaborador: ");
            Colaborador c = buscarColaborador(codigo);
            if (c == null) {
                System.out.println("Colaborador [" + codigo + "] nao encontrado.");
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
        } catch (exceptions.EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarColaboradorSistemaNome() {
        String nome = Leitura.lerLinhaComPrompt(sc, "Nome do colaborador: ");
        LinkedList<Colaborador> resultado = buscarColaborador(nome);
        if (resultado.isEmpty()) {
            System.out.println("Nenhum colaborador encontrado.");
            return;
        }
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("  COLABORADORES ENCONTRADOS");
        System.out.println("-----------------------------------------");
        for (Colaborador c : resultado) {
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
}
