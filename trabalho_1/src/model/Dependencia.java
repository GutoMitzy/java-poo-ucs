package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Dependencia implements Serializable{
	private List<Integer> ordem;
	
	private static final long serialVersionUID = 1L;

	public Dependencia() {
		this.ordem = new LinkedList<Integer>();
	}

	public List<Integer> getOrdem() {
		return this.ordem;
	}

	public void addDependencia(int indice) {
		ordem.add(indice);
	}

	public void removeDependencia(Integer indice) {
		ordem.remove(indice);
	}
}
