package model;

import java.io.Serializable;

public class Empresa implements Serializable	{
	private int codigo;
	private String nome;
	
	private static final long serialVersionUID = 1L;

	public Empresa() { 
		this.codigo = 0; 
	}

	public Empresa(String nome) {
		this.nome = nome;
		this.codigo = 0;
	}

	public String getNome() { 
		return nome; 
	}
	
	public void setNome(String nome) { 
		this.nome = nome; 
	}
	
	public int getCodigo() { 
		return codigo; 
	}
	
	public void setCodigo(int codigo) { 
		this.codigo = codigo; 
	}
}
