package Taller3;

public class Sistema implements SistemaIMPL {
	private Locales locales;
	private Clientes clientes;
	private Entregas entregas;
	
	public Sistema() {
		this.locales = new Locales();
		this.clientes = new Clientes();
		this.entregas= new Entregas();
	}

	public boolean addLocalizacion(Local item) {
		this.locales.addItem(item);
		return true;
	}

	public boolean addCliente(Cliente user) {
		this.clientes.addItem(user);
		return true;
	}

	public boolean addEntrega(String rut1, String rut2, Entrega item) {
		Cliente userA = searchCliente(rut1);
		Cliente userB = searchCliente(rut2);
		if(userA!= null && userB!=null) {
			if(userA.getCiuadad().getRegistro() && userB.getCiuadad().getRegistro()) {
				userA.getEntregas().addItem(item);
				userB.getEntregas().addItem(item);
				item.setRemitente(userA);
				item.setReceptor(userB);
				this.entregas.addItem(item);
				return true;
			}
		}
		return false;
	}

	public Cliente searchCliente(String rut) {
		int total = this.clientes.getTotal();
		for(int i=0;i<total;i++) {
			if(this.clientes.getItem(i).getRut().equals(rut)) {
				return this.clientes.getItem(i);
			}
		}
		return null;
	}

	public Locales getLocalizacion() {
		return this.locales;
	}

	public Clientes getCliente() {
		return this.clientes;
	}

	public Entregas getEntrega() {
		return this.entregas;
	}

}
