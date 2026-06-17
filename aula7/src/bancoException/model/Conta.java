package bancoException.model;
import bancoException.exceptions.DepositoNegativoException;
import bancoException.exceptions.DepositoZeroException;
import bancoException.exceptions.SaldoInsuficienteException;

public class Conta {
	private float saldo;
	
	public Conta() {}
	
	public Conta(float saldo) {
		this.saldo = saldo;
	}
	
	public float getSaldo() {
		return this.saldo;
	}
	
	public void depositar(float valor) throws DepositoNegativoException, DepositoZeroException {
		if(valor < 0) {
			throw new DepositoNegativoException();
		}
		if(valor==0) {
			throw new DepositoZeroException();
		}
		this.saldo += valor;
	}
	
	public void sacar(float valor) throws SaldoInsuficienteException{
		if(valor>saldo) {
			throw new SaldoInsuficienteException("Você só tem " + this.saldo + " na conta.");
		}
		this.saldo -= valor;
	}
}
