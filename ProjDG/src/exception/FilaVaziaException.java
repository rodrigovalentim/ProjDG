package exception;

public class FilaVaziaException extends Exception{
	
	public FilaVaziaException(){
		super("Não há dados da fila.");
	}
}