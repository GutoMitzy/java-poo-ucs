package aula3;
import java.util.Date;

public class Emprestimo {
	private Date dataInicio;
	private Date dataFim;
	private float multa;
	private boolean pago;
	
	private Livro livro;
	private Usuario usuario;
	
	public static final int MAX_EMPRESTIMOS = 100;
	
	public Emprestimo() {}
	
	public Emprestimo(Date dataInicio, Date dataFim, float multa, boolean pago, Livro livro, Usuario usuario) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.multa = multa;
		this.pago = pago;
		this.livro = livro;
		this.usuario = usuario;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public float getMulta() {
		return multa;
	}
	
	public void setMulta(float multa) {
		this.multa = multa;
	}
	
	public boolean isPago() {
		return pago;
	}
	
	public void setPago(boolean pago) {
		this.pago = pago;
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
