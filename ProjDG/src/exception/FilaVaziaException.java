package exception;

public class FilaVaziaException extends Exception{
	
	public FilaVaziaException(){
		super("N�o h� dados da fila.");
	}
}