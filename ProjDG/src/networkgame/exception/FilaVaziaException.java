package networkgame.exception;

public class FilaVaziaException extends Exception{
	
	public FilaVaziaException(){
		super("Nao ha dados da fila.");
	}
}