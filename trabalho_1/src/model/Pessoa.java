package model;
import java.io.Serializable;

public class Pessoa implements Serializable{
	private static int ultimoCodigo=0;
	private final int codigo;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	
	private static final long serialVersionUID = 1L;

	public Pessoa() {
		this.codigo = ultimoCodigo++;
	}

	public Pessoa(String nome, String cpf, String telefone, String email) {
		this();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
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
	public String getCpf() { 
		return cpf; 
	}
	public void setCpf(String cpf) { 
		this.cpf = cpf; 
	}
	public String getTelefone() { 
		return telefone; 
	}
	public void setTelefone(String telefone) { 
		this.telefone = telefone; 
	}
	public String getEmail() { 
		return email; 
	}
	public void setEmail(String email) { 
		this.email = email; 
	}

	@Override
	public String toString() {
		return "Pessoa [codigo=" + codigo + ", nome=" + nome + "]";
	}
}
