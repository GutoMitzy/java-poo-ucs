package model;
import java.io.Serializable;

public class Colaborador extends Pessoa implements Serializable{
	private String cargo;
	private float remuneracao;
	
	private static final long serialVersionUID = 1L;

	public Colaborador() {}

	public Colaborador(String nome, String cpf, String telefone, String email, String cargo, float remuneracao) {
		super(nome, cpf, telefone, email);
		this.cargo = cargo;
		this.remuneracao = remuneracao;
	}

	public String getCargo() { 
		return cargo; 
	}
	
	public void setCargo(String cargo) { 
		this.cargo = cargo; 
	}
	
	public float getRemuneracao() { 
		return remuneracao; 
	}
	
	public void setRemuneracao(float remuneracao) { 
		this.remuneracao = remuneracao; 
	}

	@Override
	public String toString() {
		return "Colaborador [codigo=" + getCodigo() + ", nome=" + getNome() + ", cargo=" + cargo + "]";
	}
}
