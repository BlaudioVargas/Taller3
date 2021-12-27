package Taller3;

import java.util.ArrayList;

public class Locales {
	private ArrayList<Local> item;
	
	public Locales() {
		this.item = new ArrayList<Local>();
	}
	
	public boolean addItem(Local item) {
		this.item.add(item);
		return true;
	}
	
	public int getTotal() {return this.item.size();}
	
	public Local getItem(int i) {
		if((i< this.item.size())&i>=0) {
			return this.item.get(i);
		}
		return null;
	}
}
