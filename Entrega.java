package Taller3;

public abstract class Entrega {
	private String code;
	private Cliente remitente;
	private Cliente receptor;
	private int peso;
	
	public Entrega(String code, int peso) {
		this.code=code;
		this.peso=peso;
	}
	
	public boolean setRemitente(Cliente user) {
		/*boolean validez = user.getCiuadad().getRegistro();
		if(validez) {
			this.remitente=user;
		}
		return validez;
		*/
		this.remitente=user;
		return true;
	}
	
	public boolean setReceptor(Cliente user) {
		/*boolean validez = user.getCiuadad().getRegistro();
		if(validez) {
			this.receptor=user;
		}
		return validez;
		*/
		this.receptor=user;
		return true;
	}
	
	public String getCode() {return this.code;}
	public Cliente getRemitente() {return this.remitente;}
	public Cliente getReceptor() {return this.receptor;}
	public int getPeso() {return this.peso;}
	
	abstract public String getType();
	abstract public int getValor();
	abstract public String getEspecificaciones() ;
}
