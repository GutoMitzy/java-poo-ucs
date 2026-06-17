package ex1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class GerenciaArquivo {
	LinkedList<Livro> listaLivros;
	
	public static void main(String[] args) {
		GerenciaArquivo arq = new GerenciaArquivo();
		
		arq.lerArquivo("livros.txt");
		arq.mostrarLivros();
	}
	
	public GerenciaArquivo() {
		this.listaLivros= new LinkedList<Livro>();
	}
	
	public void lerArquivo(String arquivo) {
		try {
			File f = new File(arquivo);
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr); 
			String linha;
			
			while((linha = br.readLine()) != null) {
				String[] dados = linha.split(";");
				listaLivros.add(new Livro(dados[0], dados[1], dados[2], dados[3]));
			}
			
			br.close();
			isr.close();
			fis.close();
		} catch(IOException ioe) {
			ioe.getStackTrace();
		}
	}
	
	public void criarArquivo(String arquivo) {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		
		Livro l1 = new Livro("Harry Potter e a Pedra Filosofal", "J.K. Howling", "1997", "Rocco");
		Livro l2 = new Livro("Harry Potter e o Prisioneir de Azkaban", "J.K. Howling", "1997", "Rocco");
		Livro l3 = new Livro("Harry Potter e a Câmara Secreta", "J.K. Howling", "1997", "Rocco");
		Livro l4 = new Livro("Harry Potter e o Enigma do Príncipe", "J.K. Howling", "1997", "Rocco");
		Livro l5 = new Livro("Harry Potter e o Cálice de Fogo", "J.K. Howling", "1997", "Rocco");
		
		try {
			File f = new File(arquivo);
			FileOutputStream fo = new FileOutputStream(f);
			fo.write();
			
		} catch (IOException ioe){
			ioe.getStackTrace();
		}
	}
	
	public void mostrarLivros() {
		for(Livro l : listaLivros) {
			System.out.println(l);
		}
	}
}
