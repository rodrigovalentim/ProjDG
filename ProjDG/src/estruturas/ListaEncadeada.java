package estruturas;


import exception.DadoNaoExisteException;
import exception.ListaVaziaException;
import exception.PosicaoInvalidaException;
import model.Pedra;

public class ListaEncadeada {
    private Node primeiro;
    private Node ultimo;
    private Node atual;
    private int qtdDados;

    public ListaEncadeada() {
        qtdDados = 0;
    }

    //Método para inclusão do objeto no início da fila
    public void adicionarNoInicio(Pedra dado) {
        if (qtdDados == 0) {
            Node node = new Node(dado);
            node.setProximo(null);
            primeiro = node;
            ultimo = node;
            qtdDados++;
        }
        else {
        	Node node = new Node(dado);
            node.setProximo(primeiro);
            primeiro = node;
            qtdDados++;
        }
    }

    //Método para inclusão do objeto no fim da fila
    public void adicionar(Pedra dado) {
        if (qtdDados == 0) {
            Node node = new Node(dado);
            node.setProximo(null);
            primeiro = node;
            ultimo = node;
            qtdDados++;
        } else {
            Node node = new Node(dado);
            node.setProximo(null);
            ultimo.setProximo(node);
            ultimo = node;
            qtdDados++;
        }
    }

    //Método para inclusão do objeto em dada posição
    public void adicionar(int posicao, Pedra dado) throws PosicaoInvalidaException {
        if (posicaoPermitida(posicao)) {
            atual = primeiro;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.getProximo();
            }
            if (atual == primeiro) {
                adicionarNoInicio(dado);
            } else if (atual == ultimo) {
                adicionar(dado);
            } else {
                Node node = new Node(dado);
                node.setProximo(atual.getProximo());
                atual.setProximo(node);
                atual = node;
                qtdDados++;
            }
        } else {
            throw new PosicaoInvalidaException();
        }
    }

    //Método que localiza determinado objeto
    public Pedra buscar(Pedra dado) throws DadoNaoExisteException{
        if (existe(dado)) {
            atual = primeiro;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
                if (atual.getDados().equals(dado)) {
                    return atual.getDados();
                }
            }
        }
        else{
            throw new DadoNaoExisteException();
        }
        return null;
    }

    //Método que localiza o objeto pela posição
    public Pedra buscar(int posicao) throws ListaVaziaException{
        if (posicaoPermitida(posicao)) {
            atual = primeiro;
            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }
            return atual.getDados();
        }
        else{
            throw new ListaVaziaException();
        }
    }

    //Método que remove o objeto do inicio da fila
    public void removerDoInicio() throws ListaVaziaException {
        if (qtdDados != 0) {
            if(qtdDados == 1){
                primeiro.setProximo(null);
                qtdDados--;
            }
            else{
                primeiro = primeiro.getProximo();
                qtdDados--;
            }
        } else {
            throw new ListaVaziaException();
        }
    }

    //Método que remove o objeto do fim da fila
    public void remover() throws ListaVaziaException {
        if (qtdDados != 0) {
            if(qtdDados == 1){
                ultimo.setProximo(null);
                qtdDados--;
            }
            else{
                ultimo.setProximo(null);
                qtdDados--;
            }
        } else {
            throw new ListaVaziaException();
        }
    }

    //Método que remove determinado objeto
    public void remover(Pedra dado) throws ListaVaziaException {
        atual = primeiro;
        int cont = 0;
        while (atual.getProximo() != null) {
            atual = atual.getProximo();
            cont++;
            if (atual.getDados().equals(dado)) {
            	atual = primeiro;
            	for(int x = 0; x < cont - 1; x++){
            		atual = atual.getProximo();
            	}
                if (atual == primeiro) {
                    removerDoInicio();
                } else if (atual == ultimo) {
                    remover();
                } else {                	
                	atual.setProximo(atual.getProximo().getProximo());
                    atual.getProximo().setProximo(null);
                    qtdDados--;
                }
            }
        }

    }

    //Método que remove o bjeto pela posição
    public void remover(int posicao) throws ListaVaziaException {
        if (posicaoPermitida(posicao)){
            atual = primeiro;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.getProximo();
            }
            if (atual == primeiro) {
                removerDoInicio();
            } else if (atual == ultimo) {
                remover();
            } else {
                atual.setProximo(atual.getProximo().getProximo());
                atual.getProximo().setProximo(null);
                qtdDados--;
            }
        }

    }

    //Método que verifica se determinado objeto está na lista
    public boolean existe(Pedra dado) {
        atual = primeiro;
        while (atual.getProximo() != null) {
            atual = atual.getProximo();
            if (atual.getDados().equals(dado)) {
                return true;
            }
        }
        return false;
    }

    //Método que informa o tamanho da lista
    public int tamanho() {
        return qtdDados;
    }

    //Método que verifica se a posição passada existe da lista
    public boolean posicaoPermitida(int posicao) {
        if (posicao < 0 || posicao >= qtdDados) {
            return false;
        }
        return true;
    }
}
