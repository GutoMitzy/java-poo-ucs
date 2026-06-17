package model;

import java.io.Serializable;

import exceptions.RecursoInvalidoException;

public class UsoRecurso implements Serializable {
	private Recurso recurso;
	private int qtdUtilizada;

	private static final long serialVersionUID = 1L;

	public UsoRecurso() {}

	public UsoRecurso(Recurso recurso, int qtdUtilizada) throws RecursoInvalidoException {
		if (recurso == null) throw new RecursoInvalidoException("Recurso não pode ser nulo.");
		if (qtdUtilizada <= 0) throw new RecursoInvalidoException("Quantidade deve ser maior que zero. Informado: " + qtdUtilizada);
		if (qtdUtilizada > recurso.getEstoque()) throw new RecursoInvalidoException("Quantidade (" + qtdUtilizada + ") excede estoque (" + recurso.getEstoque() + ").");
		this.recurso = recurso;
		this.qtdUtilizada = qtdUtilizada;
		recurso.setEstoque(recurso.getEstoque() - qtdUtilizada);
	}

	public Recurso getRecurso() { return recurso; }
	public void setRecurso(Recurso recurso) { this.recurso = recurso; }
	public int getQtdUtilizada() { return qtdUtilizada; }

	public void setQtdUtilizada(int novaQtd) throws RecursoInvalidoException {
		if (novaQtd <= 0) throw new RecursoInvalidoException("Quantidade deve ser maior que zero. Informado: " + novaQtd);
		int diff = novaQtd - this.qtdUtilizada;
		if (diff > recurso.getEstoque()) throw new RecursoInvalidoException("Quantidade adicional (" + diff + ") excede estoque disponível (" + recurso.getEstoque() + ").");
		recurso.setEstoque(recurso.getEstoque() - diff);
		this.qtdUtilizada = novaQtd;
	}

	public void devolverAoEstoque() {
		recurso.setEstoque(recurso.getEstoque() + qtdUtilizada);
	}

	public float calcularCusto() {
		return recurso.getCustoUnitario() * qtdUtilizada;
	}

	@Override
	public String toString() {
		return "UsoRecurso [recurso=" + recurso.getNome() + ", qtdUtilizada=" + qtdUtilizada
				+ ", custoTotal=R$ " + String.format("%.2f", calcularCusto()) + "]";
	}
}
