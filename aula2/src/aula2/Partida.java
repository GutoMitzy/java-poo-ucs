package aula2;

public class Partida {
	private Time visitante;
	private Time mandante;
	
	private int golVisitante;
	private int golMandante;
	
	public Time getVisitante() {
		return visitante;
	}
	
	public void setVisitante(Time visitante) {
		if(visitante == this.mandante) {
			System.out.println("Não aceito!");
			return;
		}
		this.visitante = visitante;
	}
	
	public Time getMandante() {
		return mandante;
	}
	
	public void setMandante(Time mandante) {
		if(mandante == this.visitante) {
			System.out.println("Não aceito!");
			return;
		}
		this.mandante = mandante;
	}
	
	public void marcaGolVisitante() {
		this.golVisitante++;
	}
	
	public void marcaGolMandante() {
		this.golMandante++;
	}
	
	public String getResultado() {
		return this.golMandante + " X " + this.golVisitante;
	}
	
	public String getCampeao() {
		if(this.golMandante > this.golVisitante) {
			return this.mandante.getNome();
		} else {
			if(this.golVisitante > this.golMandante) {
				return this.visitante.getNome();
			} else {
				return "Empate!";
			}
		}
	}
}
