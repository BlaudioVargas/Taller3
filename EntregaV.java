package Taller3;

public class EntregaV extends Entrega{
	private String material;
	
	public EntregaV(String code, int peso, String material) {
		super(code,peso);
		this.material=material;
	}

	public String getType() {
		return "V";
	}

	public int getValor() {
		int precio=0;
		if(material.equals("Cuero")) {
			precio=200;
		}
		else if(material.equals("Cuero")) {
			precio=150;
		}
		else if(material.equals("Cuero")) {
			precio=100;
		}
		int valor = (int) ((this.getPeso()*0.001)*precio*50);
		return valor;
	}

	public String getEspecificaciones() {
		return this.material;
	}

}
