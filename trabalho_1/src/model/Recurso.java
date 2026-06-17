package model;

import java.io.Serializable;

public class Recurso implements Serializable {
	private static int proximoCodigoRecurso = 0;
	private int codigo;
	private String nome;
	private String tipo;
	private float custoUnitario;
	private int estoque;

	private static final long serialVersionUID = 1L;

	public Recurso() {}

	public Recurso(String nome, String tipo, float custoUnitario, int estoque) {
		this.codigo = proximoCodigoRecurso++;
		this.nome = nome;
		this.tipo = tipo;
		this.custoUnitario = custoUnitario;
		this.estoque = estoque;
	}

	public int getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getCustoUnitario() {
		return custoUnitario;
	}
	public void setCustoUnitario(float custoUnitario) {
		this.custoUnitario = custoUnitario;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	@Override
	public String toString() {
		return "Recurso [codigo=" + codigo + ", nome=" + nome + ", tipo=" + tipo
				+ ", custoUnitario=" + custoUnitario + ", estoque=" + estoque + "]";
	}
}
