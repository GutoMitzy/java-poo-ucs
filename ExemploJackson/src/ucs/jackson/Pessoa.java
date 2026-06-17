package ucs.jackson;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Pessoa {
    private String nome;
    private Pessoa amigo;
    private List<Pessoa> amigos;

    public Pessoa() { // Obrigatório para Jackson na desserialização
    	this.amigos = new ArrayList<Pessoa>();
    }

    public Pessoa(String nome) {
    	this();
        this.nome = nome;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pessoa getAmigo() {
		return amigo;
	}

	public void setAmigo(Pessoa amigo) {
		this.amigo = amigo;
	}
	
	public List<Pessoa> getAmigos() {
		return this.amigos;
	}
	
	public void addAmigo(Pessoa amigo) {
		this.amigos.add(amigo);
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
    
    
}