package aula3;
import java.util.Date;

public class Principal {
	public static void main(String[] args) {
		Livro l1 = new Livro("Uma Breve História do Tempo", "Stephen Hawking");
		Livro l2 = new Livro("Core Java", "Cay Horstmann");
		Livro l3 = new Livro("C Completo e Total", "Herbert Childt");
		
		Usuario u1 = new Usuario(1, "Augusto", "a@a.com");
		
		Reserva r1 = new Reserva();
		r1.setData(new Date());
		r1.setLivro(l2);
		
		Emprestimo e1 = new Emprestimo();
		Emprestimo e2 = new Emprestimo();
		e1.setLivro(l1);
		e2.setLivro(l3);
		
		u1.addEmprestimo(e1);
		u1.addReserva(r1);
		u1.addEmprestimo(e2);
		
		System.out.println("Usuário : " + u1.getNome());
		for(int i=0; i<u1.countReserva(); i++) {
			System.out.println("Reservou livro : " + u1.getReserva(i).getLivro().getTitulo());
		}
		
		for(int i=0; i<u1.countEmprestimo(); i++) {
			System.out.println("Emprestou livro : " + u1.getEmprestimo(i).getLivro().getTitulo());
		}
		
		LivroEdicaoEspecial lev = new LivroEdicaoEspecial();
		
		lev.setCorDaCapa("Dourada");
		lev.setTitulo("Rangers: Ordem dos Arqueiros");
		lev.setAutor("Robert");
		System.out.println("'''''''''''''''''''''''''''''''");
		System.out.println(l1);
		System.out.println(u1);
		
	}
}
