package generadorLaberintos;

public class ExcepcionModos extends Exception{
	
	public ExcepcionModos() {
		super("El modo debe ser 1, 2, 3 o 4.");
	}
	
	public static void comprobarModoDibujo(int modo) throws ExcepcionModos{
		if(modo < 1 || modo > 3) {
			throw new ExcepcionModos();
		}
	}
	
	public static void comprobarModoJuego(int modo) throws ExcepcionModos{
		if(modo < 1 || modo > 4) {
			throw new ExcepcionModos();
		}
	}
}
