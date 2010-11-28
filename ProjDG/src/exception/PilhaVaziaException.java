/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exception;

/**
 *
 * @author Davi
 */
public class PilhaVaziaException extends Exception{

    public PilhaVaziaException() {
        super("Não há dados na fila.");
    }

}
