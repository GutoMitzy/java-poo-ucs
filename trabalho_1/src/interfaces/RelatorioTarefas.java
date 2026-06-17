package interfaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Evento;
import model.ExecucaoTarefa;
import model.Tarefa;
import model.UsoRecurso;

public class RelatorioTarefas implements Relatorio {

    private Evento evento;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public RelatorioTarefas(Evento evento) {
        this.evento = evento;
    }

    @Override
    public void gerarResumo() {
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("           RESUMO DE TAREFAS");
        System.out.println("            Evento: " + evento.getNome());
        System.out.println("---------------------------------------------------------");

        if (evento.getTarefas() == null || evento.getTarefas().isEmpty()) {
            System.out.println("   Nenhuma tarefa cadastrada neste evento.");
            System.out.println("---------------------------------------------------------");
            return;
        }

        int total = evento.getTarefas().size();
        long concluidas = 0;
        long emExecucao = 0;
        long planejadas = 0;
        float custoTotalEvento = 0;
        int totalRecursos = 0;
        int totalColaboradores = 0;

        for (Tarefa t : evento.getTarefas()) {
            String status = t.getStatus();

            if ("concluida".equalsIgnoreCase(status)) {
                concluidas++;
            } else if ("em_execucao".equalsIgnoreCase(status)) {
                emExecucao++;
            } else {
                planejadas++;
            }

            ExecucaoTarefa et = t.getEt();
            if (et != null) {
                totalRecursos += et.getRecursos().size();
                totalColaboradores += et.getColaboradores().size();
                custoTotalEvento += et.calcularCustoTotal();
            }
        }

        System.out.println("|");
        System.out.println("   Total de tarefas   : " + total);
        System.out.println("   Concluidas         : " + concluidas);
        System.out.println("   Em execucao        : " + emExecucao);
        System.out.println("   Planejadas         : " + planejadas);
        System.out.println("   Total responsaveis : " + totalColaboradores + " atribuicoes");
        System.out.println("   Total recursos     : " + totalRecursos + " usos de recursos");
        System.out.printf("   Custo total        : R$ %.2f%n", custoTotalEvento);
        System.out.println("|");
        System.out.println("   -- Detalhe por tarefa --");
        System.out.println("|");

        for (Tarefa t : evento.getTarefas()) {
            ExecucaoTarefa et = t.getEt();

            String inicio = (et != null && et.getDataInicio() != null)
                ? formatarData(et.getDataInicio())
                : "Nao iniciada";

            String fim = (et != null && et.getDataFim() != null)
                ? formatarData(et.getDataFim())
                : "Em andamento";

            int numResponsaveis = (et != null) ? et.getColaboradores().size() : 0;
            int numRecursos = (et != null) ? et.getRecursos().size() : 0;
            float custo = (et != null) ? et.calcularCustoTotal() : 0;

            System.out.println("   [" + t.getCodigo() + "] " + t.getDescricao());
            System.out.println("       Status       : " + t.getStatus());
            System.out.println("       Inicio       : " + inicio);
            System.out.println("       Fim          : " + fim);
            System.out.println("       Responsaveis : " + numResponsaveis);
            System.out.println("       Recursos     : " + numRecursos);
            System.out.printf("       Custo        : R$ %.2f%n", custo);
            System.out.println("|");
        }

        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void listarExecucoesTempo(Date inicio, Date fim) {
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("        TAREFAS EXECUTADAS NO PERIODO");
        System.out.println("         Evento : " + evento.getNome());
        System.out.println("         De     : " + formatarData(inicio) + " ate " + formatarData(fim));
        System.out.println("---------------------------------------------------------");

        if (evento.getTarefas() == null || evento.getTarefas().isEmpty()) {
            System.out.println("   Nenhuma tarefa cadastrada neste evento.");
            System.out.println("---------------------------------------------------------");
            return;
        }

        Date inicioN = normalizarData(inicio);
        Date fimN = normalizarData(fim);
        boolean encontrou = false;
        int contConcluidas = 0;
        int contExecucao = 0;
        float custoTotal = 0;

        for (Tarefa t : evento.getTarefas()) {
            ExecucaoTarefa et = t.getEt();
            if (et == null || et.getDataInicio() == null) {
                continue;
            }

            Date tInicio = normalizarData(et.getDataInicio());
            Date tFim = et.getDataFim() != null
                ? normalizarData(et.getDataFim())
                : normalizarData(new Date());

            boolean noPeriodo = !tInicio.after(fimN) && !tFim.before(inicioN);
            if (!noPeriodo) {
                continue;
            }

            encontrou = true;
            custoTotal += et.calcularCustoTotal();

            if ("concluida".equalsIgnoreCase(t.getStatus())) {
                contConcluidas++;
            } else {
                contExecucao++;
            }

            System.out.println("   [" + t.getCodigo() + "] " + t.getDescricao());
            System.out.println("       Status       : " + t.getStatus());
            System.out.println("       Inicio       : " + formatarData(et.getDataInicio()));
            System.out.println("       Fim          : " + (et.getDataFim() != null ? formatarData(et.getDataFim()) : "Em andamento"));
            System.out.println("       Responsaveis : " + et.getColaboradores().size());
            System.out.println("       Recursos     : " + et.getRecursos().size() + " tipos");
            System.out.printf("       Custo        : R$ %.2f%n", et.calcularCustoTotal());
            System.out.println("|");
        }

        if (!encontrou) {
            System.out.println("   Nenhuma tarefa executada no periodo informado.");
        } else {
            System.out.println("   -- Resumo do periodo --");
            System.out.println("   Concluidas  : " + contConcluidas);
            System.out.println("   Em execucao : " + contExecucao);
            System.out.printf("   Custo total : R$ %.2f%n", custoTotal);
        }

        System.out.println("---------------------------------------------------------");
    }

    private Date normalizarData(Date data) {
        if (data == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private String formatarData(Date data) {
        if (data == null) {
            return "Nao informada";
        }
        return fmt.format(data);
    }
}
