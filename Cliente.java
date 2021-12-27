package Taller3;

public class Cliente {
	private String rut;
	private String nombre;
	private String apellido;
	private int saldo;
	private Local ciuadad;
	private Entregas entregas;
	
	public Cliente(String rut, String nombre, String apellido, int saldo, Local ciudad) {
		this.rut=rut;
		this.nombre=nombre;
		this.apellido=apellido;
		this.saldo=saldo;
		this.ciuadad=ciudad;
		this.entregas=new Entregas();
	}
	
	public String getRut() {return this.rut;}
	public String getNombre() {return this.nombre;}
	public String getApellido() {return this.apellido;}
	public int getSaldo() {return this.saldo;}
	public Local getCiuadad() {return this.ciuadad;}
	public Entregas getEntregas() {return this.entregas;}
}
