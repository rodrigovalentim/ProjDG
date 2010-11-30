package exception;

public class ListaVaziaException extends Exception{

    public ListaVaziaException() {
        super("Não há dados na lista.");
    }

}
