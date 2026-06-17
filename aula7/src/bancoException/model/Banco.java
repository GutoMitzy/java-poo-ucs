package bancoException.model;

import bancoException.exceptions.DepositoNegativoException;
import bancoException.exceptions.DepositoZeroException;
import bancoException.exceptions.SaldoInsuficienteException;

public class Banco {

	public static void main(String[] args) {
		Conta c = new Conta(1000);
		
		System.out.println(c.getSaldo());
		
		try {
			c.depositar(0);		
			System.out.println(c.getSaldo());
			
		} catch(DepositoNegativoException | DepositoZeroException d) {
			System.out.println(d.getMessage());
		}
		
		try {
			c.sacar(2000);
			System.out.println(c.getSaldo());
		} catch(SaldoInsuficienteException s) {
			System.out.println(s.getMessage());
		}
	}

}
