package model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import exceptions.ExecucaoSemResponsavelException;
import exceptions.HorarioInconsistenteException;
import exceptions.RecursoInvalidoException;

public class ExecucaoTarefa implements Serializable {
	private Date dataInicio;
	private Date dataFim;

	private List<Colaborador> colaboradores;
	private List<UsoRecurso> recursos;

	private static final long serialVersionUID = 1L;

	public ExecucaoTarefa() {
		this.colaboradores = new LinkedList<>();
		this.recursos = new LinkedList<>();
	}

	public ExecucaoTarefa(Date dataInicio, Date dataFim) throws HorarioInconsistenteException {
		this();
		validarHorarios(dataInicio, dataFim);
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	private void validarHorarios(Date inicio, Date fim) throws HorarioInconsistenteException {
		if (inicio != null && fim != null && fim.before(inicio)) {
			throw new HorarioInconsistenteException("Data de fim não pode ser anterior à data de início.");
		}
	}

	public void iniciarExecucao() {
		if (this.dataInicio == null) this.dataInicio = new Date();
	}

	public void finalizarExecucao() {
		if (this.dataFim == null) this.dataFim = new Date();
	}

	public Date getDataInicio() { return dataInicio; }

	public void setDataInicio(Date dataInicio) throws HorarioInconsistenteException {
		validarHorarios(dataInicio, this.dataFim);
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() { return dataFim; }

	public void setDataFim(Date dataFim) throws HorarioInconsistenteException {
		validarHorarios(this.dataInicio, dataFim);
		this.dataFim = dataFim;
	}

	public List<Colaborador> getColaboradores() { return colaboradores; }
	public List<UsoRecurso> getRecursos() { return recursos; }

	public void adicionarColaborador(Colaborador c) {
		if (c == null) throw new IllegalArgumentException("Colaborador não pode ser nulo.");
		colaboradores.add(c);
	}

	public void removerColaborador(Colaborador c) {
		if (c == null) throw new IllegalArgumentException("Colaborador não pode ser nulo.");
		colaboradores.remove(c);
	}

	public void adicionarRecurso(Recurso r, int quantidade) throws RecursoInvalidoException {
		if (r == null) throw new RecursoInvalidoException("Recurso não pode ser nulo.");
		for (UsoRecurso ur : recursos) {
			if (ur.getRecurso().getCodigo() == r.getCodigo()) {
				ur.setQtdUtilizada(quantidade);
				return;
			}
		}
		recursos.add(new UsoRecurso(r, quantidade));
	}

	public void removerRecurso(Recurso r) throws RecursoInvalidoException {
		if (r == null) throw new RecursoInvalidoException("Recurso não pode ser nulo.");
		recursos.removeIf(ur -> {
			if (ur.getRecurso().getCodigo() == r.getCodigo()) {
				ur.devolverAoEstoque();
				return true;
			}
			return false;
		});
	}

	public void terminaTarefa() throws ExecucaoSemResponsavelException, HorarioInconsistenteException {
		if (colaboradores.isEmpty()) {
			throw new ExecucaoSemResponsavelException("Não é possível concluir uma tarefa sem responsável registrado.");
		}
		if (dataFim != null) {
			System.out.println("Impossível terminar uma tarefa já finalizada!");
			return;
		}
		Date agora = new Date();
		if (dataInicio != null && agora.before(dataInicio)) {
			throw new HorarioInconsistenteException("Data de finalização não pode ser anterior à data de início.");
		}
		this.dataFim = agora;
		System.out.println("Tarefa finalizada com sucesso!");
	}

	public float calcularCustoTotal() {
		float total = 0;
		for (UsoRecurso ur : recursos) total += ur.calcularCusto();
		return total;
	}

	@Override
	public String toString() {
		return "ExecucaoTarefa [dataInicio=" + dataInicio + ", dataFim=" + dataFim
				+ ", colaboradores=" + colaboradores + ", recursos=" + recursos + "]";
	}
}
