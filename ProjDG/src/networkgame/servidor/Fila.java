package networkgame.servidor;

import exception.FilaVaziaException;

public class Fila {
	private Object[] vetor;
    private int qtdDados;
     
    //Construtor que inicia os atributos
    public Fila() {
		vetor = new Object[50];
		qtdDados = 0;
	}

    //Metodo para inserir os dados na fila (redimensiona duplicando o tamanho da fila)
	public void inserir(Object dado){
		if(qtdDados < vetor.length){
            vetor[qtdDados] = dado;
            qtdDados++;
        }
        else{
            if(qtdDados % 50 == 0){
                Object[] temp = vetor;
                vetor = new Object[qtdDados * 2];
                for(int x = 0; x < temp.length; x++){
                    vetor[x] = temp[x];
                }
            }
            vetor[qtdDados] = dado;
            qtdDados++;
        }    	
    }
    
	//Metodo para remover os dados da fila
    public Object remover() throws FilaVaziaException{
    	if(vazia()){
    		throw new FilaVaziaException();
    	}
    	else{
    		Object dadoExcluir = vetor[0];
    		for(int x = 0; x < vetor.length - 1; x++){
    			vetor[x] = vetor[x + 1];
    		}
    		qtdDados--;
    		return dadoExcluir;
    	}    	
    }
    
    //Metodo que verifica se a fila esta vazia
    public boolean vazia(){
    	if(qtdDados == 0){
            return true;
        }
        return false;
    }

}
