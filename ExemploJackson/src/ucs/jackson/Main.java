package ucs.jackson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	
	private ObjectMapper mapper;
	
	public Main() {
		this.mapper = new ObjectMapper();
	}
	
	public static void main(String[] args) throws Exception {
    	
    	Main m = new Main();
    	
        // Criar objetos com referência circular
        Pessoa joao = new Pessoa("João");
        Pessoa maria = new Pessoa("Maria");

        joao.setAmigo(maria);
        maria.setAmigo(joao);
        
        Pessoa donald = new Pessoa("Donald");
        Pessoa huguinho = new Pessoa("Huguinho");
        Pessoa zezinho = new Pessoa("Zézinho");
        Pessoa luizinho = new Pessoa("Luizinho");
        
        huguinho.addAmigo(donald);
        huguinho.addAmigo(luizinho);
                
        donald.addAmigo(huguinho);
        donald.addAmigo(zezinho);
        donald.addAmigo(luizinho);

        // Serializar para JSON
        String json = m.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(joao);
        System.out.println("JSON serializado:");
        System.out.println(json);

        // Desserializar de volta para objetos
        Pessoa desserializado = m.mapper.readValue(json, Pessoa.class);
        System.out.println("\nObjeto desserializado:");
        System.out.println("Nome: " + desserializado.getNome());
        System.out.println("Amigo: " + desserializado.getAmigo().getNome());
        System.out.println("Amigo do amigo: " + desserializado.getAmigo().getAmigo().getNome());  // Deve ser João novamente
   
        // Serializar para JSON
        String jsonDonald = m.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(donald);
        System.out.println("JSON serializado:");
        System.out.println(jsonDonald);
        
     // Desserializar de volta para objetos
        Pessoa donaldDesserializado = m.mapper.readValue(jsonDonald, Pessoa.class);
        System.out.println("\nObjeto desserializado:");
        System.out.println(donaldDesserializado);
        System.out.println("Amigos:");
        for(Pessoa p : donaldDesserializado.getAmigos()) {
        	System.out.println(p.getNome());
        	if(p.getAmigos()!= null && !p.getAmigos().isEmpty()) {
        		System.out.println("\tAmigos:");
        		for(Pessoa p1 : p.getAmigos()) {
        			System.out.println("\t" + p1.getNome());
        		}
        	}
        }
        
        boolean ok = true;
        String nomeArquivo = "donald.json";
        
        try {
        	m.gravaJSONPessoa(nomeArquivo, jsonDonald);
        	m.gravaJSONPessoa("joao.json", json);
        } catch (ErroGravacaoException e) {
        	System.out.println(e.getMessage());
        	ok = false;
        }
        
        if(ok) {
        	Pessoa p = m.leJSONPessoa(nomeArquivo);
        	System.out.println("Pessoa lida do arquivo " + nomeArquivo);
        	System.out.println(p);
        }
    }
    
    public Pessoa leJSONPessoa(String nomeArquivo) throws ErroLeituraException {
    	
    	String json = null;
    	Pessoa p = null;
    	try {
			json = this.leArquivo(nomeArquivo);
			p = this.mapper.readValue(json, Pessoa.class);
		} catch (IOException e) {
			throw new ErroLeituraException(e);
		}
    	return p;
    }
    
    public void gravaJSONPessoa(String nomeArquivo, String json) throws ErroGravacaoException {
    	try {
    		this.gravaArquivo(nomeArquivo, json);
    	} catch (IOException e) {
    		throw new ErroGravacaoException(e);
    	}
    }
    
    private String leArquivo(String nomeArquivo) throws IOException {
    	StringBuilder sb = new StringBuilder();
    	FileInputStream fis = new FileInputStream(nomeArquivo);
    	InputStreamReader isr = new InputStreamReader(fis);
    	BufferedReader br = new BufferedReader(isr);
    	String linha;
    	while((linha = br.readLine()) != null) {
    		sb.append(linha);
    		sb.append("\n");
    	}
    	fis.close();
    	return sb.toString();
    }
    
    private void gravaArquivo(String nomeArquivo, String texto) throws IOException {
    	FileWriter fw = new FileWriter(nomeArquivo);
    	fw.write(texto);
    	fw.close();
    }
}