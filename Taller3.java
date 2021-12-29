package Taller3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

/**

 *@author BLAUDIO VARGAS

 */

public class Taller3 {

	public static void main(String [] args) throws IOException, ParseException {
		SistemaIMPL system = new Sistema();
		
		if(cargarLocales(system)) {
			/*//Se prueba la validez de carga de locales
			Locales locales = system.getLocalizacion();
			int total = locales.getTotal();
			for (int i=0; i<total; i++) {
				System.out.println(locales.getItem(i).getCiudad());
			}*/
			if(cargarCliente(system)) {
				/*//Se prueba la validez de carga de clientes
				Clientes clientes = system.getCliente();
				total = clientes.getTotal();
				for (int i=0; i<total; i++) {
					System.out.println(clientes.getItem(i).getNombre()+" "+clientes.getItem(i).getApellido()+" "+clientes.getItem(i).getRut()+" "+clientes.getItem(i).getSaldo()+" "+clientes.getItem(i).getCiuadad().getCiudad()+" "+clientes.getItem(i).getCiuadad().getRegistro());
				}*/
				if(cargaEntregas(system)) {
					/*//Se prueba la validez de carga de entregas
					Entregas entregas = system.getEntrega();
					total = entregas.getTotal();
					for (int i=0; i<total; i++) {
						System.out.println(entregas.getElemto(i).getItem().getCode()+" "+entregas.getElemto(i).getItem().getReceptor().getRut()+" "+entregas.getElemto(i).getItem().getRemitente().getRut()+" "+entregas.getElemto(i).getItem().getType()+" "+entregas.getElemto(i).getItem().getEspecificaciones());
					}*/
					boolean exito=true;
					while(exito) {
						exito=inicioSecion(system);
					}
					guardarClientes(system);
					guardarEntregas(system);
				}
			}
			
		}
	}
	
	private static boolean inicioSecion(SistemaIMPL system) throws ParseException {//Manage the start of the secion @return false to finalize the operation
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("INICIO DE SECION");
		System.out.println("Indique el Rut del usuario(formato RUT x.xxx.xxx-x/Sale con 0)");
		String seleccion = sc.nextLine();
		if(seleccion.equals("0") ) {
			return false;
		}
		else if (seleccion.equals("Admin")) {
			System.out.println("Indique la contraseña");
			String password = sc.nextLine();
			if(password.equals("choripan123")) {
				boolean exito=true;
				while(exito) {
					exito=inicioSecionADMIN(system);
				}
			}
			else {
				System.out.println("PASSWORD INVALIDO");
			}
		}
		else {
			Cliente user = system.searchCliente(seleccion);
			if(user!=null) {
				if(user.getCiuadad().getRegistro()) {
					boolean exito=true;
					while(exito) {
						exito=inicioSecionCLIENTE(system, user);
					}
				}
				else {
					System.out.println("El usuario no esta validado para el uso de la plataforma(No existe sucursal para antenderlo)");
				}
			}
			else {
				System.out.println("USUARIO INVALIDO");
				System.out.println("DESEA CREAR NUEVO USUARIO(Y/N)");
				String respuesta = sc.nextLine();
				if(respuesta.equals("y")||respuesta.equals("Y")) {
					nuevoUsuario(system, codificarRut(seleccion));
				}
			}
		}
		return true;
	}
	
	private static boolean inicioSecionADMIN(SistemaIMPL system) {//Manage secion of the Admin @return false to finalize the operation
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("INICIO DE SECION DE: ADMIN");
		System.out.println("1)Entregas por tipo");
		System.out.println("2)Entregas por localización");
		System.out.println("3)Entregas por cliente");
		System.out.println("4)Registro de ganancias");
		System.out.println("0)Salir");
		String seleccion = sc.nextLine();
		if(seleccion.equals("0") ) {
			return false;
		}else if(seleccion.equals("1") ) {
			System.out.println("ENTREGAS POR TIPO");
			entregasXtipo(system,"D");
			entregasXtipo(system,"E");
			entregasXtipo(system,"V");
		}else if(seleccion.equals("2") ) {
			entregasXlocalizacion(system);
		}else if(seleccion.equals("3") ) {
			entregasXcliente(system);
		}else if(seleccion.equals("4") ) {
			registroGanancias(system);
		}
		return true;
	}

	private static void entregasXtipo(SistemaIMPL system, String tipo) {
		System.out.println("Entregas del tipo :"+tipo );
		Entregas entregas = system.getEntrega();
		int total = entregas.getTotal();
		for (int i=0; i<total; i++) {
			if(entregas.getElemto(i).getItem().getType().equals(tipo)) {
				imprimirEntregas(entregas.getElemto(i).getItem());
			}
		}
	}

	private static void entregasXlocalizacion(SistemaIMPL system) {
		System.out.println("ENTREGAS POR LOCALIZACION");
		Locales locales = system.getLocalizacion();
		int total = locales.getTotal();
		for (int i=0; i<total; i++) {
			String sucursal =locales.getItem(i).getCiudad();
			Entregas entregas = system.getEntrega();
			int totalE = entregas.getTotal();
			int emisor=0;
			int receptor=0;
			for (int j=0; j<totalE; j++) {
				String sucursalB = entregas.getElemto(j).getItem().getRemitente().getCiuadad().getCiudad();
				String sucursalC = entregas.getElemto(j).getItem().getReceptor().getCiuadad().getCiudad();
				if(sucursal.equals(sucursalB)) {
					emisor+=1;
				}
				if(sucursal.equals(sucursalC)) {
					receptor+=1;
				}
				
			}
			System.out.println(sucursal+" realizo "+emisor+" envíos y recibió "+receptor+" envíos");
		}
	}

	private static void entregasXcliente(SistemaIMPL system) {
		System.out.println("ENTREGAS POR CLIENTE");
		Clientes clientes = system.getCliente();
		int total = clientes.getTotal();
		for (int i=0; i<total; i++) {
			verEntregas(clientes.getItem(i));
		}
	}

	private static void registroGanancias(SistemaIMPL system) {//Shows the earnings for each susucras and their totalm
		System.out.println("REGISTRO GANANCIAS");
		int valanceTotal=0;
		Locales locales = system.getLocalizacion();
		int total = locales.getTotal();
		for (int i=0; i<total; i++) {
			String sucursal =locales.getItem(i).getCiudad();
			Entregas entregas = system.getEntrega();
			int totalE = entregas.getTotal();
			int valance=0;
			for (int j=0; j<totalE; j++) {
				String sucursalB = entregas.getElemto(j).getItem().getReceptor().getCiuadad().getCiudad();
				if(sucursal.equals(sucursalB)) {
					valance+=entregas.getElemto(j).getItem().getValor();
				}
				
			}
			valanceTotal+=valance;
			System.out.println("Sucursal :"+sucursal+", ganancias por entrega: "+valance+" clp");
		}
		System.out.println("Valance total por todas las entregas es de : "+valanceTotal+" clp");
	}

	private static boolean inicioSecionCLIENTE(SistemaIMPL system, Cliente user) {//Manage secion of the user @return false to finalize the operation
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("INICIO DE SECION DE: "+user.getRut());
		System.out.println("1)Realizar una entrega");
		System.out.println("2)Recargar saldo");
		System.out.println("3)Ver tus entregas");
		System.out.println("0)Salir");
		String seleccion = sc.nextLine();
		if(seleccion.equals("0") ) {
			return false;
		}else if(seleccion.equals("1") ) {
			realizarEntrega(system, user);
		}else if(seleccion.equals("2") ) {
			recargarSaldo(user);
		}else if(seleccion.equals("3") ) {
			System.out.println("VER TUS ENTREGAS");
			verEntregas(user);
		}
		return true;
	}

	private static void realizarEntrega(SistemaIMPL system, Cliente user) {//Generate a new Entrega for the user and system
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("REALIZAR UNA ENTREGA");
		try {
			System.out.println("Indique el Tipo de entrega");
			String tipo = sc.nextLine();
			String typos="D/E/V";
			String[] tipos = typos.split("/");
			if(Arrays.asList(tipos).contains(tipo)) {
				System.out.println("Indique el Rut del destinatario");
				String rut = sc.nextLine();
				if(system.searchCliente(rut).getCiuadad().getRegistro()) {
					String codigo = generarCodigoUnico(system);
					//String tipo = partes[1];
					String remitente = user.getRut();
					String destinatario = rut;
					Entrega nuevo=null;
					System.out.println("Indique el peso en gr");
					int peso = sc.nextInt();
					@SuppressWarnings("resource")
					var sc2= new Scanner(System.in);
					if(tipo.equals("D")) {
						System.out.println("Indique el grosor en mm");
						int grosor = sc2.nextInt();
						nuevo = new EntregaD(codigo,peso, grosor);
					}
					else if(tipo.equals("E")){
						System.out.println("Indique el largo en cm");
						int largo = sc2.nextInt();
						System.out.println("Indique el ancho en cm");
						int ancho = sc2.nextInt();
						System.out.println("Indique el profundidad en cm");
						int profundidad = sc2.nextInt();
						nuevo = new EntregaE(codigo,peso, largo, ancho, profundidad);
					}
					else if(tipo.equals("V")){
						System.out.println("Indique el material");
						String material = sc2.nextLine();
						String materials="Cuero/Plástico/Tela";
						String[] materiales = materials.split("/");
						if(Arrays.asList(materiales).contains(material)) {
							nuevo = new EntregaV(codigo,peso, material);
						}
						
					}
					if(nuevo!=null) {
						system.addEntrega(remitente, destinatario, nuevo);
					}
					else {
						System.out.println("ERROR EN DATOS PARA EL ENVIO");
					}
				}
				else {
					System.out.println("ERROR NO EXISTE SEDE PARA EL ENVIO");
				}
			}
			else {
				System.out.println("ERROR TIPO NO RECONOCIDO");
			}
		}
		catch (Exception e) {
			System.out.println("ERROR DATOS DE ENTREGA INVALIDOS");
		}
	}

	private static String generarCodigoUnico(SistemaIMPL system) {//@return a unique code for Envio
		boolean exito = false;
		String codigo="";
		while(!exito) {
			int total= system.getEntrega().getTotal();
			exito=true;
			codigo = generarCodigo();
			for(int i=0;i<total;i++) {
				if(system.getEntrega().getElemto(i).getItem().getCode().equals(codigo)) {
					exito=false;
					break;
				}
			}
		}
		return codigo;
	}

	private static String generarCodigo() {//@return a code for Envio
		String codigo =""+(int)(Math.random() * ( 10 - 0 ))+""+(int)(Math.random() * ( 10 - 0 ))+""+(int)(Math.random() * ( 10 - 0 ))+""+(int)(Math.random() * ( 10 - 0 ))+""+(int)(Math.random() * ( 10 - 0 ))+""+(int)(Math.random() * ( 10 - 0 ));
		return codigo;
	}

	private static void recargarSaldo(Cliente user) {//Recharge user Saldo
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		System.out.println("RECARGAR SALDO");
		try {
			System.out.println("Indique la cantidad de dinero a aumentar");
			int saldo = sc.nextInt();
			user.addSaldo(saldo);
			System.out.println("SE AGREGO EL NUEVO SALDO CON EXITO");
		}
		catch (Exception e) {
			System.out.println("ERROR DATOS DE SALDO INVALIDOS");
		}
	}

	private static void verEntregas(Cliente user) {//Show a user's deliveries
		int total = user.getEntregas().getTotal();
		System.out.println("Entregas del usuario: "+user.getRut());
		for(int i=0; i<total;i++) {
			System.out.print((i+1)+") ");
			imprimirEntregas(user.getEntregas().getElemto(i).getItem());
		}
	}
	
	private static void imprimirEntregas(Entrega entrega) {//prints the data of the desired delivery on the screen
		System.out.println("Entrega:"+entrega.getCode());
		System.out.println("---Destinatario: "+entrega.getReceptor().getRut()+" "+entrega.getReceptor().getNombre()+" "+entrega.getReceptor().getApellido());
		System.out.println("---Remintente: "+entrega.getRemitente().getRut()+" "+entrega.getReceptor().getNombre()+" "+entrega.getReceptor().getApellido());
		System.out.println("---Especificaciones:");
		System.out.println("-----Tipo: "+entrega.getType());
		System.out.println("-----Peso: "+entrega.getPeso()+" gr");
		if(entrega.getType().equals("D")) {
			System.out.println("-----Grosor: "+entrega.getEspecificaciones()+" mm");
		}
		else if(entrega.getType().equals("E")) {
			String[] partes;
			partes = entrega.getEspecificaciones().split(",");
			System.out.println("-----Largo: "+partes[0]+" cm");
			System.out.println("-----Ancho: "+partes[1]+" cm");
			System.out.println("-----Profundidad: "+partes[2]+" cm");
		}
		else if(entrega.getType().equals("V")) {
			System.out.println("-----Material: "+entrega.getEspecificaciones());
		}
		else {
			System.out.println("ERROR AL RECONOCER EL ENVIO");
		}
		System.out.println("-----Valor: "+entrega.getValor()+" Clp");
			
	} 

	private static void nuevoUsuario(SistemaIMPL system, String rut) {//add a new user to the system
		@SuppressWarnings("resource")
		var sc= new Scanner(System.in);
		try {
			System.out.println("Indique el Nombre");
			String nombre = sc.nextLine();
			System.out.println("Indique el Apellido");
			String apellido = sc.nextLine();
			System.out.println("Indique la Ciudad");
			String ciudad = sc.nextLine();
			System.out.println("Indique el Saldo");
			int saldo = sc.nextInt();
			Local city = searchLocal(system, ciudad);
			if(city==null) {
				city= new Local(ciudad, false);
			}
			Cliente nuevo = new Cliente(rut, nombre, apellido, saldo, city);
			system.addCliente(nuevo);
			System.out.println("SE AGREGO EL NUEVO CLIENTE CON EXITO");
		}
		catch (Exception e) {
			System.out.println("ERROR DATOS DE USUARIOS INVALIDOS");
		}
	}
	
	public static String codificarRut(String rut) {//@return a standar version of the rut
		String[] partes;
		partes = rut.split("");
		if(partes.length==12)
		{
			if(partes[11].equals("k"))
			{
				partes[11]="K";
			}
			return partes[0]+"."+partes[1]+partes[3]+partes[4]+partes[5]+"."+partes[7]+partes[8]+partes[9]+"-"+partes[11];
			
		}
		else if(partes.length==11)
		{
			if(partes[10].equals("k"))
			{
				partes[10]="K";
			}
			return partes[0]+"."+partes[2]+partes[3]+partes[4]+"."+partes[6]+partes[7]+partes[8]+"-"+partes[10];
		}
		else if(partes.length==10)
		{
			if(partes[9].equals("k"))
			{
				partes[9]="K";
			}
			return partes[0]+partes[1]+"."+partes[2]+partes[3]+partes[4]+"."+partes[5]+partes[6]+partes[7]+"-"+partes[9];
		}
		else if(partes.length==9)
		{
			if(partes[8].equals("k"))
			{
				partes[8]="K";
			}
			return partes[0]+partes[1]+"."+partes[2]+partes[3]+partes[4]+"."+partes[5]+partes[6]+partes[7]+"-"+partes[8];
		}
		else if(partes.length==8)
		{
			if(partes[7].equals("k"))
			{
				partes[7]="K";
			}
			return partes[0]+"."+partes[1]+partes[2]+partes[3]+"."+partes[4]+partes[5]+partes[6]+"-"+partes[7];
		}
		else
		{
			return rut;
		}
	}

	private static boolean cargarLocales(SistemaIMPL system) {//load localización.txt @return false in case of an error while loading
		try {
			File archivo = new File ("src/Taller3/localización.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			String linea;
			while((linea = reader.readLine())!=null){
				Local nuevo = new Local(linea, true);
				system.addLocalizacion(nuevo);
			}
			reader.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE LOCALES CORRUPTO");
			return false;
		}
	}
	
	private static boolean cargarCliente(SistemaIMPL system) {//load Cliente.txt @return false in case of an error while loading
		try {
			File archivo = new File ("src/Taller3/Cliente.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			String linea;
			String[] partes;
			while((linea = reader.readLine())!=null){
				partes = linea.split(",");
				String rut = partes[0];
				String nombre = partes[1];
				String apellido = partes[2];
				int saldo =Integer.parseInt(partes[3]);
				String ciudad = partes[4];
				Local city = searchLocal(system, ciudad);
				if(city==null) {
					city= new Local(ciudad, false);
				}
				Cliente nuevo = new Cliente(rut, nombre, apellido, saldo, city);
				system.addCliente(nuevo);
			}
			reader.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE CLIENTES CORRUPTO");
			return false;
		}
	}
	
	private static boolean cargaEntregas(SistemaIMPL system) {//load Entregas.txt @return false in case of an error while loading
		try {
			File archivo = new File ("src/Taller3/Entregas.txt"); 
			FileReader text = new FileReader (archivo); 
			BufferedReader reader = new BufferedReader(text); 
			String linea;
			String[] partes;
			while((linea = reader.readLine())!=null){
				partes = linea.split(",");
				String codigo = partes[0];
				String tipo = partes[1];
				String remitente = partes[2];
				String destinatario = partes[3];
				Entrega nuevo=null;
				if(tipo.equals("D")) {
					int peso =Integer.parseInt(partes[4]);
					int grosor =Integer.parseInt(partes[4]);
					nuevo = new EntregaD(codigo,peso, grosor);
				}
				else if(tipo.equals("E")){
					int peso =Integer.parseInt(partes[4]);
					int largo =Integer.parseInt(partes[4]);
					int ancho =Integer.parseInt(partes[4]);
					int profundidad =Integer.parseInt(partes[4]);
					nuevo = new EntregaE(codigo,peso, largo, ancho, profundidad);
				}
				else if(tipo.equals("V")){
					String material = partes[4];
					int peso =Integer.parseInt(partes[5]);
					nuevo = new EntregaV(codigo,peso, material);
				}
				if(nuevo!=null) {
					system.addEntrega(remitente, destinatario, nuevo);
				}
				
			}
			reader.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("ARCHIVO DE ENTREGAS CORRUPTO");
			return false;
		}
	}
	
	private static void guardarClientes(SistemaIMPL system) {//save clientes.txt
		try {
			File archivo = new File ("src/Taller3/Cliente.txt"); 
			FileWriter text = new FileWriter (archivo); 
			BufferedWriter wr = new BufferedWriter(text);

			Clientes clientes = system.getCliente();
			int total = clientes.getTotal();
			for (int i=0; i<total; i++) {
				String datos=clientes.getItem(i).getRut()+","+clientes.getItem(i).getNombre()+","+clientes.getItem(i).getApellido()+","+clientes.getItem(i).getSaldo()+","+clientes.getItem(i).getCiuadad().getCiudad();
				wr.write(datos+"\n");
			}
			wr.close();
		}
		catch (Exception e) {
			System.out.println("ARCHIVO CLIENTES NO GUARDADO");
		}
	}
	
	private static void guardarEntregas(SistemaIMPL system) {//save entregas.txt
		try {
			File archivo = new File ("src/Taller3/Entregas.txt"); 
			FileWriter text = new FileWriter (archivo); 
			BufferedWriter wr = new BufferedWriter(text);

			Entregas entregas = system.getEntrega();
			int total = entregas.getTotal();
			for (int i=0; i<total; i++) {
				String datos =entregas.getElemto(i).getItem().getCode()+","+entregas.getElemto(i).getItem().getType()+","+entregas.getElemto(i).getItem().getRemitente().getRut()+","+entregas.getElemto(i).getItem().getReceptor().getRut();
				if(entregas.getElemto(i).getItem().getType().equals("V")) {
					datos=datos+","+entregas.getElemto(i).getItem().getEspecificaciones()+","+entregas.getElemto(i).getItem().getPeso();
				}
				else {
					datos=datos+","+entregas.getElemto(i).getItem().getPeso()+","+entregas.getElemto(i).getItem().getEspecificaciones();
				}
				wr.write(datos+"\n");
			}
			wr.close();
		}
		catch (Exception e) {
			System.out.println("ARCHIVO ENTREGAS NO GUARDADO");
		}
	}

	private static Local searchLocal(SistemaIMPL system, String ciudad) {//Search for a specific city in the system
		Locales locales = system.getLocalizacion();
		int total = locales.getTotal();
		for (int i=0; i<total; i++) {
			if(ciudad.equals(locales.getItem(i).getCiudad()))
				return(locales.getItem(i));
		}
		return null;
	}
	
	
	
	
	
}
