package interfaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Evento;
import model.ExecucaoTarefa;
import model.Recurso;
import model.Tarefa;
import model.UsoRecurso;

public class RelatorioRecursos implements Relatorio {

    private List<Evento> eventos;
    private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public RelatorioRecursos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public void gerarResumo() {
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("           RESUMO DE USO DE RECURSOS");
        System.out.println("---------------------------------------------------------");

        if (eventos.isEmpty()) {
            System.out.println("   Nenhum evento cadastrado.");
            System.out.println("---------------------------------------------------------");
            return;
        }

        Map<String, int[]> consolidado = new LinkedHashMap<>();
        Map<String, float[]> custos = new LinkedHashMap<>();
        int totalUnidades = 0;
        float custoGeral = 0;

        for (Evento evento : eventos) {
            if (evento.getTarefas() == null) {
                continue;
            }

            for (Tarefa t : evento.getTarefas()) {
                ExecucaoTarefa et = t.getEt();
                if (et == null) {
                    continue;
                }

                for (UsoRecurso ur : et.getRecursos()) {
                    Recurso r = ur.getRecurso();
                    String chave = r.getNome() + " [" + r.getTipo() + "]";

                    if (!consolidado.containsKey(chave)) {
                        consolidado.put(chave, new int[]{0});
                        custos.put(chave, new float[]{0f});
                    }

                    consolidado.get(chave)[0] += ur.getQtdUtilizada();
                    custos.get(chave)[0] += ur.calcularCusto();
                    totalUnidades += ur.getQtdUtilizada();
                    custoGeral += ur.calcularCusto();
                }
            }
        }

        if (consolidado.isEmpty()) {
            System.out.println("   Nenhum recurso utilizado em nenhum evento.");
            System.out.println("---------------------------------------------------------");
            return;
        }

        System.out.printf("   %-30s %8s %14s%n", "Recurso", "Qtd Total", "Custo Total");
        System.out.println("   -------------------------------------------------------");

        for (Map.Entry<String, int[]> entry : consolidado.entrySet()) {
            String nome = entry.getKey();
            int qtd = entry.getValue()[0];
            float custo = custos.get(nome)[0];
            System.out.printf("   %-30s %8d   R$ %8.2f%n", nome, qtd, custo);
        }

        System.out.println("   -------------------------------------------------------");
        System.out.printf("   %-30s %8d   R$ %8.2f%n", "TOTAL GERAL", totalUnidades, custoGeral);
        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void listarExecucoesTempo(Date inicio, Date fim) {
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("     RECURSOS UTILIZADOS NO PERIODO");
        System.out.println("      " + formatarData(inicio) + " ate " + formatarData(fim));
        System.out.println("---------------------------------------------------------");

        Date inicioN = normalizarData(inicio);
        Date fimN = normalizarData(fim);

        Map<String, int[]> consolidado = new LinkedHashMap<>();
        Map<String, float[]> custos = new LinkedHashMap<>();
        int totalUnidades = 0;
        float custoTotal = 0;
        boolean encontrou = false;

        for (Evento evento : eventos) {
            Date eInicio = normalizarData(evento.getDataInicio());
            Date eFim = normalizarData(evento.getDataFim());

            if (eInicio == null || eFim == null) {
                continue;
            }

            boolean eventoNoPeriodo = !eInicio.after(fimN) && !eFim.before(inicioN);
            if (!eventoNoPeriodo) {
                continue;
            }

            if (evento.getTarefas() == null) {
                continue;
            }

            for (Tarefa t : evento.getTarefas()) {
                ExecucaoTarefa et = t.getEt();
                if (et == null || et.getDataInicio() == null) {
                    continue;
                }

                Date tInicio = normalizarData(et.getDataInicio());
                Date tFim = et.getDataFim() != null ? normalizarData(et.getDataFim()) : normalizarData(new Date());

                boolean tarefaNoPeriodo = !tInicio.after(fimN) && !tFim.before(inicioN);
                if (!tarefaNoPeriodo) {
                    continue;
                }

                for (UsoRecurso ur : et.getRecursos()) {
                    encontrou = true;
                    Recurso r = ur.getRecurso();
                    String chave = r.getNome() + " [" + r.getTipo() + "]";

                    if (!consolidado.containsKey(chave)) {
                        consolidado.put(chave, new int[]{0});
                        custos.put(chave, new float[]{0f});
                    }

                    consolidado.get(chave)[0] += ur.getQtdUtilizada();
                    custos.get(chave)[0] += ur.calcularCusto();
                    totalUnidades += ur.getQtdUtilizada();
                    custoTotal += ur.calcularCusto();
                }
            }
        }

        if (!encontrou) {
            System.out.println("   Nenhum recurso utilizado no periodo informado.");
            System.out.println("---------------------------------------------------------");
            return;
        }

        System.out.printf("   %-30s %8s %14s%n", "Recurso", "Qtd", "Custo");
        System.out.println("   -------------------------------------------------------");

        for (Map.Entry<String, int[]> entry : consolidado.entrySet()) {
            String nome = entry.getKey();
            int qtd = entry.getValue()[0];
            float custo = custos.get(nome)[0];
            System.out.printf("   %-30s %8d   R$ %8.2f%n", nome, qtd, custo);
        }

        System.out.println("   -------------------------------------------------------");
        System.out.printf("   %-30s %8d   R$ %8.2f%n", "TOTAL DO PERIODO", totalUnidades, custoTotal);
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
