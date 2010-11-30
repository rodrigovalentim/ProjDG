package exception;

public class DadoNaoExisteException extends Exception{

    public DadoNaoExisteException() {
        super("Dado não existente na lista.");
    }

}
