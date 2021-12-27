package Taller3;

import java.util.LinkedList;


public class Clientes {
	private LinkedList<Cliente> item; 
	
	public Clientes() {
		this.item = new LinkedList<Cliente>();
	}
	
	public boolean addItem(Cliente item) {
		this.item.add(item);
		return true;
	}
	
	public int getTotal() {return this.item.size();}
	
	public Cliente getItem(int i) {
		if((i< this.item.size())&i>=0) {
			return this.item.get(i);
		}
		return null;
	}
}
