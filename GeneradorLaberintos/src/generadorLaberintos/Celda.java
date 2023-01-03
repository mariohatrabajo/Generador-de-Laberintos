package generadorLaberintos;

//Generador de laberintos

public class Celda {
//Atributos
	private boolean visitada = false;
	//Muros
	private boolean norte = false;
	private boolean este = false;
	private boolean sur = false;
	private boolean oeste = false;
	
	public int x;
	public int y;
	
//Metodos
	public Celda() {}

	public Celda(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public Celda(boolean norte, boolean este, boolean sur, boolean oeste) {
		this.norte = norte;
		this.este = este;
		this.sur = sur;
		this.oeste = oeste;
	}
	
	
	//Getters y Setters
	//Visitada
	public boolean isVisitada() {
		return visitada;
	}
	public void setVisitada(boolean visitada) {
		this.visitada = visitada;
	}
	public void visitar() {
		this.visitada = true;
	}
	
	//Y
	public int getY() {
		return y;
	}
	
	//X
	public int getX() {
		return x;
	}
	
	//Norte
	public boolean tieneNorte() {
		return norte;
	}
	public void setNorte(boolean norte) {
		this.norte = norte;
	}
	
	//Este
	public boolean tieneEste() {
		return este;
	}
	public void setEste(boolean este) {
		this.este = este;
	}
	
	//Sur
	public boolean tieneSur() {
		return sur;
	}
	public void setSur(boolean sur) {
		this.sur = sur;
	}
	
	//Oeste
	public boolean tieneOeste() {
		return oeste;
	}
	public void setOeste(boolean oeste) {
		this.oeste = oeste;
	}
	
	public void setMuros(boolean norte, boolean este, boolean sur, boolean oeste) {
		this.norte = norte;
		this.este = este;
		this.sur = sur;
		this.oeste = oeste;
	}
	public void setMuros(boolean todos) {
		this.norte = todos;
		this.este = todos;
		this.sur = todos;
		this.oeste = todos;
	}
}
