package Taller3;

public class EntregaE extends Entrega{
	private int largo;
	private int ancho;
	private int profundidad;
	
	public EntregaE(String code, int peso, int largo, int ancho, int profundidad) {
		super(code,peso);
		this.largo=largo;
		this.ancho=ancho;
		this.profundidad=profundidad;
	}

	public String getType() {
		return "E";
	}

	public int getValor() {
		int valor = (int) ((this.getPeso()*0.001)*(largo*ancho*profundidad)*50);
		return valor;
	}

	public String getEspecificaciones() {
		return this.largo+"/"+this.ancho+"/"+this.profundidad;
	}

}
