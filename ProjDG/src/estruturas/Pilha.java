package estruturas;

import exception.PilhaVaziaException;

public class Pilha {

    private Object[] vetor;
    private int qtdDados;

    //Construtor que inicia os atributos
    public Pilha() {
        vetor = new Object[50];
        qtdDados = 0;
    }

    //Método para empilhar os dados na pilha (redimensiona duplicando o tamanho da pilha)
    public void empilhar(Object dado) {
        if (qtdDados < vetor.length) {
            vetor[qtdDados] = dado;
            qtdDados++;
        } else {
            if (qtdDados % 50 == 0) {
                Object[] temp = vetor;
                vetor = new Object[qtdDados * 2];
                for (int x = 0; x < temp.length; x++) {
                    vetor[x] = temp[x];
                }
            }
            vetor[qtdDados] = dado;
            qtdDados++;
        }
    }

    //Método para desempilhar os dados da pilha
    public Object desempilhar() throws PilhaVaziaException {
        if (vazia()) {
            throw new PilhaVaziaException();
        } else {
            qtdDados--;
            return vetor[qtdDados];
        }

    }

    //Método que verifica se a pilha está vazia
    public boolean vazia() {
        if (qtdDados == 0) {
            return true;
        }
        return false;
    }
}
