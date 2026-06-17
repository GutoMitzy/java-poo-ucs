package bancoException.exceptions;

public class SaldoInsuficienteException extends Exception{
	private static final String MESSAGE = "Você não possui saldo para executar essa operação!";

	public SaldoInsuficienteException() {
		super(MESSAGE);
	}

	public SaldoInsuficienteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(MESSAGE + " " + message, cause, enableSuppression, writableStackTrace);
	}

	public SaldoInsuficienteException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public SaldoInsuficienteException(String message) {
		super(MESSAGE + " " + message);
	}

	public SaldoInsuficienteException(Throwable cause) {
		super(MESSAGE, cause);
	}
	
	
	
}
