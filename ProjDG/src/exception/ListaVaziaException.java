/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exception;

/**
 *
 * @author Davi Sande
 */
public class ListaVaziaException extends Exception{

    public ListaVaziaException() {
        super("N�o h� dados na lista.");
    }

}
