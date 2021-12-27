package Taller3;

interface SistemaIMPL {
	public boolean addLocalizacion(Local item);
	public boolean addCliente(Cliente user);
	public boolean addEntrega(String rut1, String rut2, Entrega item );
	
	public Cliente searchCliente(String rut);
	
	public Locales getLocalizacion();
	public Clientes getCliente();
	public Entregas getEntrega();

}
