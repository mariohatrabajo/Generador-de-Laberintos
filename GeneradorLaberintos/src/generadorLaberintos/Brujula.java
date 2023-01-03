package generadorLaberintos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Brujula extends Entidad{
//Atributos
	private ArrayList<Character> camino = new ArrayList<Character>();
	private Enemigo ene;
	private Celda[][] mapa;
	
//Metodos
	public Brujula(Enemigo ene) {
		super(ene.getX(), ene.getY());
		this.ene = ene;
		
		this.l = ene.getLaberinto();
		
		mapa = new Celda[l.getMapa().length][l.getMapa()[0].length];
		System.arraycopy(l.getMapa(), 0, mapa, 0, l.getMapa().length);
		reiniciarCeldas();
	}
	
	private void reiniciarCeldas() {
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {
				mapa[i][j].setVisitada(false);
			}
		}
	}
	
	public void lanzar() {
		mapa[y][x].visitar();
		
		while(x != 0 || y != l.getAltura()-1) {
			
			if(!(mapa[y][x].tieneSur() || mapa[y+1][x].isVisitada())) {
				moverSur();
				camino.add('S');
				//System.out.print("La brujula se ha movido al sur");
				
			}else if(!(mapa[y][x].tieneOeste() || mapa[y][x-1].isVisitada())) {
				moverOeste();
				camino.add('O');
				//System.out.print("La brujula se ha movido al oeste");
				
			}else if(!(mapa[y][x].tieneNorte() || mapa[y-1][x].isVisitada())) {
				moverNorte();
				camino.add('N');
				//System.out.print("La brujula se ha movido al norte");
				
			}else if(!(mapa[y][x].tieneEste() || mapa[y][x+1].isVisitada())) {
				moverEste();
				camino.add('E');
				//System.out.print("La brujula se ha movido al este");
				
			}else {
				backtracking();
				//System.out.print("La brujula ha vuelto");
			}
			//System.out.println("{" + x + ", " + y + "}");
			mapa[y][x].visitar();
		}
		
	}
	
	private void backtracking() {
		switch(camino.get(camino.size()-1)) {
		case 'N':
				moverSur();
				camino.remove(camino.size()-1);
			break;
		case 'E':
				moverOeste();
				camino.remove(camino.size()-1);
			break;
		case 'S':
				moverNorte();
				camino.remove(camino.size()-1);
			break;
		case 'O':
			moverEste();
			camino.remove(camino.size()-1);
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

	public ArrayList<Character> getCamino() {
		return camino;
	}
}
