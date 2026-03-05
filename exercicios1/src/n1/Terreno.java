package n1;

public class Terreno {
	private float largura;
	private float comprimento;
	
	public void setLargura(float largura) {
		this.largura = largura;
	}
	
	public void setComprimento(float comprimento) {
		this.comprimento = comprimento;
	}
	
	public float calculaArea() {
		return this.comprimento * this.largura;
	}
}
