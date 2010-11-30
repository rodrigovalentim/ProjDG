package estruturas;

import model.Pedra;


public class Node {
	private Pedra dados;
	private Node proximo;
	
	public Node(Pedra dados){
		this.dados = dados;
	}
	public Pedra getDados() {
		return dados;
	}
	public void setDados(Pedra dados) {
		this.dados = dados;
	}
	public Node getProximo() {
		return proximo;
	}
	public void setProximo(Node node) {
		this.proximo = node;
	}
	
	

}
