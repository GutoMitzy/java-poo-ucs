package aula2;

public class Time {
	private String nome;
	private String uniforme;
	
	public Time() {}
	
	public Time(String nome, String uniforme) {
		this.nome = nome;
		this.uniforme = uniforme;
	}
	
	public void setNome(String nome) {
		if (nome.isBlank() || nome.isEmpty() || nome == null) {
			System.out.println("Nome em branco/vazio não aceito!");
			return;
		}
		
		this.nome = nome;
	}
	
	public void setUniforme(String uniforme) {
		this.uniforme = uniforme;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getUniforme() {
		return this.uniforme;
	}
	
}
