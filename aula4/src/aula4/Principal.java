package aula4;

public class Principal {
	public static void main(String[] args) {
		Uniforme u1 = new Uniforme();
		
		u1.defineCor(3);
		u1.defineCor(3.6f);
		
		Camiseta c = new Camiseta(5, "Grená");
		u1.defineCor(c);
		
		c.setCor("Vermelho");
		c.setNrUniforme(4);
		
		System.out.println(c);
		System.out.println(u1);
		
		UniformeInverno ui = new UniformeInverno();
		
		ui.defineCor(7);
		
		ui.print();
		System.out.println(ui);
	}
}
