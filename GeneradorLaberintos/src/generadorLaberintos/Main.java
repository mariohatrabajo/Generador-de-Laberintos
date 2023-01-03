package generadorLaberintos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		boolean fallo;
		int altura = 1, anchura = 1, verticalidad = 1, horizontalidad = 1;
		int modo = 3;
		boolean otraPartida;
		
		do {
			System.out.println("\u29C9Generador de laberintos horizontales\u29C9");
	
			// El usuario indica el tamaño del laberinto
			do {
				try {
					System.out.print("\tAltura: ");
					altura = teclado.nextInt();
					ExcepcionMayorDe0.comprobar(altura);
					
					System.out.print("\tAnchura: ");
					anchura = teclado.nextInt();
					ExcepcionMayorDe0.comprobar(anchura);
					
					System.out.print("\tVerticalidad(normal 1): ");
					verticalidad = teclado.nextInt();
					ExcepcionMayorDe0.comprobar(verticalidad);
					
					System.out.print("\tHorizontalidad(normal 1): ");
					horizontalidad = teclado.nextInt();
					ExcepcionMayorDe0.comprobar(horizontalidad);
					
					fallo = false;
	
				} catch (InputMismatchException e) {
					System.err.println("Las dimensiones deben ser números.");
					teclado.next();
					fallo = true;
					
				}catch(ExcepcionMayorDe0 e) {
					System.err.println("Las dimensiones deben ser mayores de 0.");
					fallo = true;
				}
			} while (fallo);
	
			// Se crea el laberinto
			Laberinto l = new Laberinto(altura, anchura, verticalidad, horizontalidad);
	
			System.out.println("\n¿Deseas guardar el laberinto en un fichero?(s/n)");
			boolean guardar = teclado.next().charAt(0) == 's';
	
			System.out.println("\n¿Deseas indicar la semilla del laberinto?(s/n)");
			if (teclado.next().charAt(0) == 's') {
				do {
					try {
						System.out.print("Semilla: ");
						l.getArq().rd.setSeed(teclado.nextLong());
						fallo = false;
						
					} catch (InputMismatchException e) {
						System.err.println("La semilla debe ser un número.");
						teclado.next();
						fallo = true;
					}
				} while (fallo);
	
			}
			
			do {
				try {
					System.out.println("\n\tModos:\n" 
									 + "1. Paso a paso\n" 
									 + "2. Automático\n" 
									 + "3. Ver terminado");
					modo = teclado.nextInt();
					
					ExcepcionModos.comprobarModoDibujo(modo);
					fallo = false;
					
				}catch(ExcepcionModos e) {
					System.err.println(e.getMessage());
					fallo = true;
				}catch(InputMismatchException e) {
					System.err.println("El modo debe ser un número");
					teclado.next();
					fallo = true;
				}
			}while(fallo);
	
			l.generarLaberinto(guardar, modo);
			
			
			//Juego
			System.out.println("\nPulsa enter para empezar el juego.");
			teclado.nextLine();
			teclado.nextLine();
			
			l.setJugador(new Jugador(l));
			
			l.jugarLaberinto();
			
			System.out.println("\n¿Otra partida?(s/n)");
			otraPartida = (teclado.next().charAt(0) == 's');
			System.out.println("\n\n\n\n");
			
		}while(otraPartida);
	}
}
