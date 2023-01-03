package generadorLaberintos;

public class ExcepcionMayorDe0 extends Exception{
	
	public ExcepcionMayorDe0() {
		super("El valor debe ser mayor de 0.");
	}
	
	public static void comprobar(int n) throws ExcepcionMayorDe0{
		if(n <= 0) {
			throw new ExcepcionMayorDe0();
		}
	}
}
