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
		String rutA = decodificarRut(rut);
		for(int i=0;i<total;i++) {
			String rutB = decodificarRut(this.clientes.getItem(i).getRut());
			if(rutA.equals(rutB)) {
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
	
	public static String decodificarRut(String rut) {//@return a simplified version of the rut
		String[] partes;
		partes = rut.split("");
		if(partes.length==12)
		{
			if(partes[11].equals("k"))
			{
				partes[11]="K";
			}
			return partes[0]+partes[1]+partes[3]+partes[4]+partes[5]+partes[7]+partes[8]+partes[9]+partes[11];
			
		}
		if(partes.length==11)
		{
			if(partes[10].equals("k"))
			{
				partes[10]="K";
			}
			return partes[0] + partes[2]+partes[3]+partes[4] + partes[6]+partes[7]+partes[8] +partes[10];
		}
		else if(partes.length==10)
		{
			if(partes[9].equals("k"))
			{
				partes[9]="K";
			}
			return partes[0]+partes[1]+partes[2]+partes[3]+partes[4]+partes[5]+partes[6]+partes[7]+partes[9];
		}
		else if(partes.length==9)
		{
			if(partes[8].equals("k"))
			{
				partes[8]="K";
			}
			return partes[0]+partes[1]+partes[2]+partes[3]+partes[4]+partes[5]+partes[6]+partes[7]+partes[8];
		}
		else if(partes.length==8)
		{
			if(partes[7].equals("k"))
			{
				partes[7]="K";
			}
			return partes[0]+partes[1]+partes[2]+partes[3]+partes[4]+partes[5]+partes[6]+partes[7];
		}
		else
		{
			return rut;
		}
	}

}
