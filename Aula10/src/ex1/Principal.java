package ex1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Principal {
	public static void main(String[] args) {
		Map<String, String> pessoas = new HashMap<>();
		
		pessoas.put("1", "Augusto");
		pessoas.put("2", "Mariana");
		pessoas.put("3", "Felipe");
		pessoas.put("4", "Camila");
		pessoas.put("5", "Ricardo");
		pessoas.put("6", "Patrícia");
		pessoas.put("7", "Eduardo");
		pessoas.put("8", "Larissa");
		pessoas.put("9", "Vinícius");
		pessoas.put("10", "Juliana");
		pessoas.put("11", "André");
		pessoas.put("12", "Beatriz");
		pessoas.put("13", "Gustavo");
		pessoas.put("14", "Fernanda");
		pessoas.put("15", "Lucas");
		pessoas.put("16", "Aline");
		pessoas.put("17", "Thiago");
		pessoas.put("18", "Renata");
		pessoas.put("19", "Bruno");
		pessoas.put("20", "Natália");
		
		Set<String> chaves = pessoas.keySet();
		
		for(String chave : chaves) {
			System.out.print("[" + chave + "] ");
		}
	}
}
