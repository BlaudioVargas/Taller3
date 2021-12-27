package Taller3;

public class Local {
	private String ciudad;
	private boolean registro;
	private Clientes clientes;

	public Local(String ciudad, boolean registro) {
		this.ciudad=ciudad;
		this.registro=registro;
		this.clientes = new Clientes();
	}
	
	public Clientes getClientes(){return this.clientes;}
	
	public String getCiudad() {return this.ciudad;}
	
	public boolean getRegistro() {return this.registro;}
}
