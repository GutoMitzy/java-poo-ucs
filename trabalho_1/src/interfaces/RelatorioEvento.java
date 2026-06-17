package interfaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Evento;
import model.ExecucaoTarefa;
import model.Tarefa;
import model.UsoRecurso;

public class RelatorioEvento implements Relatorio {

    private List<Evento> eventos;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public RelatorioEvento(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public void gerarResumo() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("           RESUMO GERAL DE EVENTOS");
        System.out.println("---------------------------------------------------------");

        for (Evento evento : eventos) {
            int totalColaboradores = evento.getColaboradores() != null ? evento.getColaboradores().size() : 0;
            int totalRecursos = evento.getRecursos() != null ? evento.getRecursos().size() : 0;
            int totalTarefas = evento.getTarefas() != null ? evento.getTarefas().size() : 0;

            long concluidas = 0;
            long emExecucao = 0;
            long planejadas = 0;
            float gastoTotal = 0;

            if (evento.getTarefas() != null) {
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
                        gastoTotal += et.calcularCustoTotal();
                    }
                }
            }

            String progresso = totalTarefas > 0
                ? String.format("%d/%d concluidas", concluidas, totalTarefas)
                : "Sem tarefas";

            System.out.println("|");
            System.out.println("   Evento  : " + evento.getNome() + " [cod. " + evento.getCodigo() + "]");
            System.out.println("   Tipo    : " + evento.getTipo());
            System.out.println("   Periodo : " + formatarData(evento.getDataInicio()) + " ate " + formatarData(evento.getDataFim()));
            System.out.println("   Colaboradores  : " + totalColaboradores);
            System.out.println("   Recursos       : " + totalRecursos);
            System.out.println("   Tarefas        : " + totalTarefas
                + "  (concluidas: " + concluidas
                + " | em execucao: " + emExecucao
                + " | planejadas: " + planejadas + ")");
            System.out.println("   Progresso      : " + progresso);
            System.out.printf("   Gasto total    : R$ %.2f%n", gastoTotal);
            System.out.println("|");
            System.out.println("---------------------------------------------------------");
        }
    }

    @Override
    public void listarExecucoesTempo(Date inicio, Date fim) {
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("         EVENTOS NO PERIODO: " + formatarData(inicio) + " ate " + formatarData(fim));
        System.out.println("---------------------------------------------------------");

        Date inicioN = normalizarData(inicio);
        Date fimN = normalizarData(fim);
        boolean encontrou = false;

        for (Evento evento : eventos) {
            Date eInicio = normalizarData(evento.getDataInicio());
            Date eFim = normalizarData(evento.getDataFim());

            if (eInicio == null || eFim == null) {
                continue;
            }

            boolean noPeriodo = !eInicio.after(fimN) && !eFim.before(inicioN);
            if (!noPeriodo) {
                continue;
            }

            encontrou = true;

            int totalTarefas = evento.getTarefas() != null ? evento.getTarefas().size() : 0;
            long concluidas = 0;
            float gastoTotal = 0;

            if (evento.getTarefas() != null) {
                for (Tarefa t : evento.getTarefas()) {
                    if ("concluida".equalsIgnoreCase(t.getStatus())) {
                        concluidas++;
                    }
                    ExecucaoTarefa et = t.getEt();
                    if (et != null) {
                        gastoTotal += et.calcularCustoTotal();
                    }
                }
            }

            System.out.println("|");
            System.out.println("   Evento  : " + evento.getNome() + " [cod. " + evento.getCodigo() + "]");
            System.out.println("   Periodo : " + formatarData(evento.getDataInicio()) + " ate " + formatarData(evento.getDataFim()));
            System.out.println("   Tipo    : " + evento.getTipo());
            System.out.println("   " + evento.getAndamento());
            System.out.println("   Colaboradores : " + (evento.getColaboradores() != null ? evento.getColaboradores().size() : 0));
            System.out.printf("   Gasto total   : R$ %.2f%n", gastoTotal);
            System.out.println("|");
            System.out.println("---------------------------------------------------------");
        }

        if (!encontrou) {
            System.out.println("   Nenhum evento encontrado no periodo informado.");
            System.out.println("---------------------------------------------------------");
        }
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
