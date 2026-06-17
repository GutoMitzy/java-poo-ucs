package bancoException.exceptions;

public class DepositoZeroException extends Exception{
	private static final String MESSAGE = "Valor ZERO não é aceito como depósito!";

	public DepositoZeroException() {
		super(MESSAGE);
	}

	public DepositoZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(MESSAGE + " " + message, cause, enableSuppression, writableStackTrace);
	}

	public DepositoZeroException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public DepositoZeroException(String message) {
		super(MESSAGE + " " + message);
	}

	public DepositoZeroException(Throwable cause) {
		super(MESSAGE, cause);
	}
	
	
}
