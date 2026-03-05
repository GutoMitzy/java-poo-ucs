package aula2;

public class Principal {
	public static void main(String[] args) {
		Time t1, t2;
		
		t1 = new Time();
		t1.setNome("Juventude");
		t1.setUniforme("Verde/Branco");
		
		t2 = new Time("Caxias", "Grená");
	
		System.out.println(t1.getNome() + " - " + t1.getUniforme());
		System.out.println(t2.getNome() + " - " + t2.getUniforme());
		
		Partida p1 = new Partida();
		p1.setMandante(t2);
		p1.setVisitante(t2);
		
		p1.marcaGolMandante();
		p1.marcaGolMandante();
		p1.marcaGolVisitante();
		
		System.out.println(p1.getCampeao());
		System.out.println(p1.getResultado());
	}
}
