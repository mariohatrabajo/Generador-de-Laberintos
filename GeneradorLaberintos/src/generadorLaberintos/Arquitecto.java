package generadorLaberintos;

import java.util.ArrayList;
import java.util.Random;

public class Arquitecto extends Entidad{
//Atributos
	private Pila posiciones = new Pila();
	public boolean terminado = false;
	public Random rd = new Random();
	
//Metodos
	//Constructores
	public Arquitecto(int y, int x, Laberinto l) {
		super(x, y, l);
		this.l.getCelda(y, x).visitar();
		posiciones.apilar(y, x);
		this.l.getCelda(y, x).setMuros(true);
	}
	
	public Arquitecto(Laberinto l) {
		Random rand = new Random();
		this.y = rand.nextInt(l.getAltura());
		this.x = 0;
		this.l = l;
	}
	
	/* Comprueba las celdas contiguas si no estan visitadas
	 * Elige una al azar
	 * Se mueve a ella
	 * Añade las nuevas coordenadas a la pila de posiciones
	 * Indica que la nueva celda esta visitada
	 * Si no hay celdas libres contiguas llama al metodo backtracking*/
	public void mover() {
		//Definicion de vbles
		ArrayList<Celda> libres = new ArrayList<Celda>();
		Celda anterior = l.getCelda(y, x);
		
		// Se referencian las celdas de alrededor
		Celda cNorte = new Celda();
		cNorte.visitar();
		Celda cSur = new Celda();
		cSur.visitar();
		Celda cOeste = new Celda();
		cOeste.visitar();
		Celda cEste = new Celda();
		cEste.visitar();
		if(y > 0) {
			cNorte = l.getCelda(y-1, x);
		}
		if(y < l.getMapa().length-1) {
			cSur = l.getCelda(y+1, x);
		}
		if(x > 0) {
			cOeste = l.getCelda(y, x-1);
		}
		if(x < l.getMapa()[0].length-1) {
			cEste = l.getCelda(y, x+1);
		}
		
		//Se comprueba si estan libres
		if(!cNorte.isVisitada()) {
			for(int i = 0; i < l.getVerticalidad(); i++) {
				libres.add(cNorte);
			}
		}
		if(!cSur.isVisitada()) {
			for(int i = 0; i < l.getVerticalidad(); i++) {
				libres.add(cSur);
			}
		}
		if(!cOeste.isVisitada()) {
			for(int i = 0; i < l.getHorizontalidad(); i++) {
				libres.add(cOeste);
			}
		}
		if(!cEste.isVisitada()) {
			for(int i = 0; i < l.getHorizontalidad(); i++) {
				libres.add(cEste);
			}
		}
		
		// Si no hay celdas contiguas libres, llama al metodo backtracking
		if(libres.isEmpty()) {
			backtracking();
		}else {
			// Se mueve
			Celda siguiente = libres.get(rd.nextInt(libres.size()));
			
			this.y = siguiente.y;
			this.x = siguiente.x;
			
			siguiente.visitar();
			
			//System.out.println("El arquitecto se ha movido a la posicion {" + y + ", " + x + "}");
			
			posiciones.apilar(y, x);
			
			//Se ponen todos los muros
			siguiente.setMuros(true);
			
			//Se quitan los muros que sobren
			if(siguiente.getY() > anterior.getY()) {// Se ha movido hacia abajo
				anterior.setSur(false);
				siguiente.setNorte(false);
			}else if(siguiente.getY() < anterior.getY()) {// Se ha movido hacia arriba
				anterior.setNorte(false);
				siguiente.setSur(false);
			}else if(siguiente.getX() > anterior.getX()) {// Se ha movido hacia la derecha
				anterior.setEste(false);
				siguiente.setOeste(false);
			}else if(siguiente.getX() < anterior.getX()) {// Se ha movido hacia la izquierda
				anterior.setOeste(false);
				siguiente.setEste(false);
			}
		}
	}
	
	//Retrocede a la posicion anterior
	private void backtracking() {
		if(!posiciones.estaVacia()) {
			int[] coords = posiciones.retirar();
			y = coords[0];
			x = coords[1];
			
			//System.err.println("El arquitecto ha vuelto a la posicion {" + y + ", " + x + "}");
		}else {
			System.out.println("Laberinto generado completamente");
			terminado = true;
		}
	}
}
