package generadorLaberintos;

public abstract class Entidad {
	
	protected int x;
	protected int y;
	protected Laberinto l;
	
	public Entidad() {}
	
	public Entidad(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Entidad(Laberinto l) {
		this.l = l;
	}
	
	public Entidad(int x, int y, Laberinto l) {
		this.x = x;
		this.y = y;
		this.l = l;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Laberinto getLaberinto() {
		return l;
	}
}
