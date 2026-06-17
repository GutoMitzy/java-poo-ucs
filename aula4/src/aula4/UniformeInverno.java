package aula4;

public class UniformeInverno extends Uniforme{
	private int gorro;

	public int getGorro() {
		return gorro;
	}

	public void setGorro(int gorro) {
		this.gorro = gorro;
	}
	
	public void defineCor(int gorro) {
		this.gorro = gorro;
	}
	
	public void defineCor(int gorro, int meias) {
		super.setMeia(meias);
		this.gorro = gorro;
	}
	
	public void print(String text) {
		System.out.println(text);
	}

	@Override
	public String toString() {
		return "UniformeInverno [gorro=" + gorro + ", Uniforme=" + super.toString() + "]";
	}
}
