package aula3;
import java.util.Date;

public class Reserva {
	private Date data;
	
	private Livro livro;
	private Usuario usuario;
	
	public static final int MAX_RESERVAS = 100;
	
	public Reserva() {}

	public Reserva(Date data, Livro livro, Usuario usuario) {
		this.data = data;
		this.livro = livro;
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
