package interfaces;

import java.util.Date;

public interface Relatorio {
    void gerarResumo();
    void listarExecucoesTempo(Date inicio, Date fim);
}
