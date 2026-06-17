package model;

import java.io.Serializable;
import java.util.List;

import exceptions.OrdemTarefaException;

public class Tarefa implements Serializable {

    private static int proximoCodigoTarefa = 0;
    private int codigo;
    private String descricao;
    private String status;

    private ExecucaoTarefa et;
    private Dependencia dep;

    private static final long serialVersionUID = 1L;

    public Tarefa() {
        this.codigo = proximoCodigoTarefa++;
        this.dep = new Dependencia();
        this.et = new ExecucaoTarefa();
    }

    public Tarefa(String descricao, String status) {
        this();
        this.descricao = descricao;
        this.status = status;
        if ("em_execucao".equalsIgnoreCase(status)) {
            this.et.iniciarExecucao();
        } else if ("concluida".equalsIgnoreCase(status)) {
            this.et.iniciarExecucao();
            this.et.finalizarExecucao();
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if ("em_execucao".equalsIgnoreCase(status) && !"em_execucao".equalsIgnoreCase(this.status)) {
            this.et.iniciarExecucao();
        } else if ("concluida".equalsIgnoreCase(status) && !"concluida".equalsIgnoreCase(this.status)) {
            if (this.et.getDataInicio() == null) {
                this.et.iniciarExecucao();
            }
            this.et.finalizarExecucao();
        }
        this.status = status;
    }

    public ExecucaoTarefa getEt() {
        return et;
    }

    public void setEt(ExecucaoTarefa et) {
        this.et = et;
    }

    public Dependencia getDep() {
        return dep;
    }

    public void verificarDependencias(List<Tarefa> todasTarefas) throws OrdemTarefaException {
        for (int depCodigo : dep.getOrdem()) {
            boolean encontrada = false;
            for (Tarefa t : todasTarefas) {
                if (t.getCodigo() == depCodigo) {
                    encontrada = true;
                    if (!"concluida".equalsIgnoreCase(t.getStatus())) {
                        throw new OrdemTarefaException(
                            "A tarefa [" + this.codigo + "] depende da tarefa ["
                            + depCodigo + "] que ainda nao foi concluida (status: "
                            + t.getStatus() + ")."
                        );
                    }
                    break;
                }
            }
            if (!encontrada) {
                throw new OrdemTarefaException(
                    "Dependencia com codigo [" + depCodigo + "] nao encontrada nas tarefas do evento."
                );
            }
        }
    }

    public void concluirTarefa(List<Tarefa> todasTarefas) throws OrdemTarefaException {
        verificarDependencias(todasTarefas);
        this.setStatus("concluida");
        System.out.println("Tarefa [" + this.codigo + "] concluida com sucesso!");
    }

    public void adicionarDependencia(int indice) {
        this.dep.addDependencia(indice);
    }

    public void removerDependencia(int indice) {
        this.dep.removeDependencia(indice);
    }

    @Override
    public String toString() {
        return "Tarefa [codigo=" + codigo + ", descricao=" + descricao + ", status=" + status + "]";
    }
}
