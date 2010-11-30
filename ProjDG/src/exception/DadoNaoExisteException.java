/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exception;

/**
 *
 * @author Davi
 */
public class DadoNaoExisteException extends Exception{

    public DadoNaoExisteException() {
        super("Dado não existente na lista.");
    }

}
