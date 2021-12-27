package Taller3;

public class EntregaD  extends Entrega{
	private int grosor;
	
	public EntregaD(String code, int peso, int grosor) {
		super(code,peso);
		this.grosor=grosor;
	}
	
	public String getType() {
		return "D";
	}

	public int getValor() {
		int valor = (int) ((this.getPeso()*0.001)*this.grosor*100);
		return valor;
	}

	public String getEspecificaciones() {
		return ""+this.grosor;
	}

}
