package generadorLaberintos;

import java.util.Scanner;

public class Jugador extends Entidad implements Movil {
//Atributos
	public static final char c = '\u263B';
	public char ultimoMov;

//Metodos
	public Jugador(Laberinto l) {
		super(0, 0, l);
	}

	public Jugador(int x, int y, Laberinto l) {
		super(x, y, l);
	}
	
	public void mover() {
		Scanner teclado = new Scanner(System.in);
		char mov;
		
		System.out.println("Usa WASD para moverte, puedes no poner nada para continuar en la misma dirección.");
		String aux = teclado.nextLine();
		if(aux.isEmpty()) {
			mov = ultimoMov;
		}else {
			mov = aux.charAt(0);
		}
		
		switch(Character.toUpperCase(mov)) {
		case 'W':
			if(!l.getMapa()[y][x].tieneNorte()) {
				moverNorte();
			}
			break;
		case 'D':
			if(!l.getMapa()[y][x].tieneEste()) {
				moverEste();
			}
			break;
		case 'S':
			if(!l.getMapa()[y][x].tieneSur()) {
				moverSur();
			}
			break;
		case 'A':
			if(!l.getMapa()[y][x].tieneOeste()) {
				moverOeste();
			}
			break;
		}
		ultimoMov = mov;
	}
	
	private void moverNorte() {
		y--;
	}

	private void moverEste() {
		x++;
	}

	private void moverSur() {
		y++;
	}

	private void moverOeste() {
		x--;
	}	
}
