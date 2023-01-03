package generadorLaberintos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Enemigo extends Entidad implements Movil {
//Atributos
	public static final char c = '\u263A';
	private Brujula bru;
	private Celda[][] mapa;
	private ArrayList<Character> camino = new ArrayList<Character>();

//Metodos
	public Enemigo(Laberinto l, boolean brujula) {
		super(l);
		if (brujula) {
			bru = new Brujula(this);
			bru.lanzar();
		} else {
			mapa = new Celda[l.getMapa().length][l.getMapa()[0].length];
			System.arraycopy(l.getMapa(), 0, mapa, 0, l.getMapa().length);
			reiniciarCeldas();
		}
	}

	public Enemigo(int x, int y, Laberinto l, boolean brujula) {
		super(x, y, l);
		if (brujula) {
			bru = new Brujula(this);
			bru.lanzar();
		} else {
			mapa = new Celda[l.getMapa().length][l.getMapa()[0].length];
			System.arraycopy(l.getMapa(), 0, mapa, 0, l.getMapa().length);
			reiniciarCeldas();
		}
	}

	private void reiniciarCeldas() {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				mapa[i][j].setVisitada(false);
			}
		}
	}

	public void mover() {
		Random rd = new Random();
		Scanner teclado = new Scanner(System.in);

		if (bru != null) {
			switch (bru.getCamino().get(0)) {
			case 'N':
				moverNorte();
				break;
			case 'E':
				moverEste();
				break;
			case 'S':
				moverSur();
				break;
			case 'O':
				moverOeste();
				break;
			}

			bru.getCamino().remove(0);
		} else {
			this.mapa[y][x].visitar();

			if (!(this.mapa[y][x].tieneSur() || this.mapa[y + 1][x].isVisitada())) {
				moverSur();
				camino.add('S');

			} else if (!(this.mapa[y][x].tieneOeste() || this.mapa[y][x - 1].isVisitada())) {
				moverOeste();
				camino.add('O');

			} else if (!(this.mapa[y][x].tieneNorte() || this.mapa[y - 1][x].isVisitada())) {
				moverNorte();
				camino.add('N');

			} else if (!(this.mapa[y][x].tieneEste() || this.mapa[y][x + 1].isVisitada())) {
				moverEste();
				camino.add('E');

			} else {
				backtracking();
			}
			this.mapa[y][x].visitar();
		}
	}

	private void backtracking() {
		switch (camino.get(camino.size() - 1)) {
		case 'N':
			moverSur();
			camino.remove(camino.size() - 1);
			break;
		case 'E':
			moverOeste();
			camino.remove(camino.size() - 1);
			break;
		case 'S':
			moverNorte();
			camino.remove(camino.size() - 1);
			break;
		case 'O':
			moverEste();
			camino.remove(camino.size() - 1);
			break;
		}
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
