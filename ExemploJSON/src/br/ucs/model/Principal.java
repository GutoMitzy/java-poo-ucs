package br.ucs.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Principal {

	public static void main(String[] args) {

		Principal main = new Principal();

		String nomeArquivo = "carro.json";

		Carro c = new Carro("Fusca");

		c.adicionaCarga(200);
		c.adicionaPassageiro(new Pessoa("Mickey", 30));
		c.adicionaPassageiro(new Pessoa("Donald", 20));
		c.adicionaPassageiro(new Pessoa("Margarida", 20));
		c.adicionaPassageiro(new Pessoa("Pateta", 50));

		main.gravaCarro(c, nomeArquivo);

		Carro outro = main.leCarro(nomeArquivo);

		System.out.println(outro);

	}

	public void gravaCarro(Carro carro, String nomeArquivo) {

		Gson gson = new Gson();
		try (FileWriter writer = new FileWriter(nomeArquivo)) {
			gson.toJson(carro, writer);
			System.out.println("Objeto escrito em " + nomeArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Carro leCarro(String nomeArquivo) {
		
		Carro carro = null;
		
		Gson gson = new Gson();
        try (FileReader reader = new FileReader(nomeArquivo)) {
            carro = gson.fromJson(reader, Carro.class);
            System.out.println("Objeto lido do JSON!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return carro;
	}

}
