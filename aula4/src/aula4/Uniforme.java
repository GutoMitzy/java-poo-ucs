package aula4;

public class Uniforme {
	private int camiseta;
	private int calcao;
	private int meia;
	
	public Uniforme() {}
	
	public Uniforme(int camiseta, int calcao, int meia) {
		this.camiseta = camiseta;
		this.calcao = calcao;
		this.meia = meia;
	}

	public int getCamiseta() {
		return camiseta;
	}

	public void setCamiseta(int camiseta) {
		this.camiseta = camiseta;
	}

	public int getCalcao() {
		return calcao;
	}

	public void setCalcao(int calcao) {
		this.calcao = calcao;
	}

	public int getMeia() {
		return meia;
	}

	public void setMeia(int meia) {
		this.meia = meia;
	}
	
	public void defineCor(int camiseta) {
		this.camiseta = camiseta;
	}
	
	public void defineCor(float camiseta) {
		this.camiseta = (int)camiseta;
	}
	
	public void defineCor(String camiseta) {
		switch (camiseta) {
		case "AMARELO":
			this.camiseta = 1;
			break;
		case "VERDE":
			this.camiseta = 2;
			break;
		}

	}
	
	public void defineCor(int camiseta, int calcao) {
		this.camiseta = camiseta;
		this.calcao = calcao;
	}
	
	public void defineCor(Camiseta c) {
		this.camiseta = c.getNrUniforme();
	}
	
	public void print() {
		System.out.println("Uniforme");
	}

	@Override
	public String toString() {
		return "Uniforme [camiseta=" + camiseta + ", calcao=" + calcao + ", meia=" + meia + "]";
	}
	
	
	
	
}
