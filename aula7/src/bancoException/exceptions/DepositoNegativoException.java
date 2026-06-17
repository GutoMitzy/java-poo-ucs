package bancoException.exceptions;

public class DepositoNegativoException extends Exception{
	private static final String MESSAGE = "Valor negativo não é aceito como depósito!";

	public DepositoNegativoException() {
		super(MESSAGE);
	}

	public DepositoNegativoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(MESSAGE + " " + message, cause, enableSuppression, writableStackTrace);
	}

	public DepositoNegativoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public DepositoNegativoException(String message) {
		super(MESSAGE + " " + message);
	}

	public DepositoNegativoException(Throwable cause) {
		super(MESSAGE, cause);
	}
	
	
}
