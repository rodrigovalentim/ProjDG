package exception;

public class PilhaVaziaException extends Exception{

    public PilhaVaziaException() {
        super("Não há dados na fila.");
    }

}
