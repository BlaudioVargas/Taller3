package Taller3;


public class Entregas {
	private Nodo first;
	
	public Entregas() {
		this.first=null;
	}
	
	public boolean addItem(Entrega item) {
		Nodo nuevo = new Nodo(item);
		if(first==null) {
			this.first=nuevo;
			nuevo.setNext(nuevo);
			nuevo.setPrevius(nuevo);
		}
		else {
			boolean primero = false;//Se verifica que no existe el codigo repitido
			Nodo indice = first.getNext();
			String codeA = item.getCode();
			String codeB = first.getItem().getCode();
			if(codeA.equals(codeB)) {
				return false;
			}
			while(!primero) {
				codeB = indice.getItem().getCode();
				if(codeA.equals(codeB)) {
					return false;
				}
				if(indice==first) {
					primero=true;
				}
				indice=indice.getNext();
			}
			nuevo.setNext(first);//Se agrega la nueva entrega al nodo
			nuevo.setPrevius(first.getPrevius());
			first.getPrevius().setNext(nuevo);
			first.setPrevius(nuevo);
		}
		return true;
	}
	
	public Nodo getElemto(int i) {
		boolean primero = false;
		Nodo indice = first.getNext();
		int j=1;
		if(first!=null) {
			if(i==0) {
				return first;
			}
			while(!primero) {
				if(i==j) {
					return indice;
				}
				if(indice==first) {
					primero=true;
				}
				j++;
				indice=indice.getNext();
			}
		}
		return null;
	}
}
