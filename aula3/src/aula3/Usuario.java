package aula3;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private Reserva reservas[];
	private int ultimaReserva;
	private Emprestimo emprestimos[];
	private int ultimoEmprestimo;
	
	public Usuario() {
		this.reservas = new Reserva[Reserva.MAX_RESERVAS];
		this.emprestimos = new Emprestimo[Emprestimo.MAX_EMPRESTIMOS];
		this.ultimaReserva = 0;
		this.ultimoEmprestimo = 0;
	}
	
	public Usuario(int id, String nome, String email) {
		this(); //Chama o construtor acima primeiro
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void addReserva(Reserva reserva) {
		if(this.ultimaReserva < Reserva.MAX_RESERVAS ) {
			reserva.setUsuario(this);
			this.reservas[this.ultimaReserva++] = reserva;			
		} else {
			System.out.println("Máximo de Reservas alcançado para esse Usuário!");
		}
	}
	
	public void addEmprestimo(Emprestimo emprestimo) {
		if(this.ultimoEmprestimo < Emprestimo.MAX_EMPRESTIMOS) {
			emprestimo.setUsuario(this);
			this.emprestimos[this.ultimoEmprestimo++] = emprestimo;
		} else {
			System.out.println("Máximo de Empréstimo alcançado para esse usuário");
		}
	}
	
	public int countReserva() {
		return this.ultimaReserva;
	}
	
	public int countEmprestimo() {
		return this.ultimoEmprestimo;
	}
	
	public Reserva getReserva(int index) {
		if(index >= 0 && index < this.ultimaReserva) {
			return this.reservas[index]; 
		} else {
			return null;
		}
	}
	
	public Emprestimo getEmprestimo(int index) {
		if(index >= 0 && index < this.ultimoEmprestimo) {
			return this.emprestimos[index];
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("usuário :" + this.nome);
		sb.append("\n");
		sb.append("Reservas : ");
		sb.append("\n");
		
		for(int i=0; i<this.countReserva(); i++) {
			sb.append(this.getReserva(i).getLivro() + "\n");
		}
		
		sb.append("Empréstimos : ");
		sb.append("\n");
		
		for(int i=0; i<this.countEmprestimo(); i++) {
			sb.append(this.getEmprestimo(i).getLivro() + "\n");
		}
		
		return sb.toString();
	}
}
