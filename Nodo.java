package Taller3;


class Nodo{
	private Entrega item;
	private Nodo next;
	private Nodo previus;
	public Nodo(Entrega item) {
		this.item=item;
		this.next=null;
		this.previus=null;
	}
	public void setNext(Nodo next) {this.next=next;}
	public void setPrevius(Nodo previus) {this.previus=previus;}
	
	public Entrega getItem() {return this.item;}
	public Nodo getNext() {return this.next;}
	public Nodo getPrevius() {return this.previus;}
	
}