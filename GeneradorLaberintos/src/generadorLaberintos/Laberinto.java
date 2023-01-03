package generadorLaberintos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Laberinto {
//Atributos
	private Celda[][] mapa;
	private Arquitecto arq;
	private Jugador jug;
	private Entidad ene;
	private int altura;
	private int anchura;
	private int verticalidad = 1;
	private int horizontalidad = 1;
	private boolean jugando = false;
	
//Metodos
	public Laberinto() {
		generarLaberinto(false, 3);
	}

	public Laberinto(Celda[][] mapa) {
		this.mapa = mapa;
		altura = mapa.length;
		anchura = mapa[0].length;
		crearCeldas();
		arq = new Arquitecto(0, 0, this);
	}

	public Laberinto(int altura, int anchura) {
		this.mapa = new Celda[altura][anchura];
		this.altura = altura;
		this.anchura = anchura;
		crearCeldas();
		arq = new Arquitecto(0, 0, this);
	}

	public Laberinto(int altura, int anchura, int verticalidad, int horizontalidad) {
		this.mapa = new Celda[altura][anchura];
		this.altura = altura;
		this.anchura = anchura;
		this.verticalidad = verticalidad;
		this.horizontalidad = horizontalidad;
		crearCeldas();
		arq = new Arquitecto(0, 0, this);
	}
	
	private void crearCeldas() {
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < anchura; x++) {
				mapa[y][x] = new Celda(y, x);
			}
		}
	}

	// Dibuja el laberinto en la consola
	public void dibujarLaberinto() {

		char[][] dibujo = rellenarLaberinto();
		
		System.out.println("\n");
		
		for (char[] dib : dibujo) {
			for (char d : dib) {
				System.out.print(d);
			}
			System.out.println();
		}
	}

	// Guarda el laberinto en un fichero
	public void guardarLaberinto() throws FileNotFoundException {
		String nombreArchivo = "Laberintos/laberinto" + 1 + ".txt";
		File archivo = new File(nombreArchivo);
		
		for(int i = 2; archivo.exists(); i++) {
			nombreArchivo = "Laberintos/laberinto" + i + ".txt";
			archivo = new File(nombreArchivo);
		}

		PrintWriter salida = new PrintWriter(archivo);
		
		char[][] dibujo = rellenarLaberinto();

		for (char[] dib : dibujo) {
			for (char d : dib) {
				salida.print(d);
			}
			salida.println();
		}
		
		salida.close();
	}
	
	// Juega el laberinto
	public void jugarLaberinto() {
		Scanner teclado = new Scanner(System.in);
		boolean victoria = false;
		boolean derrota = false;
		long inicio;
		long lim = 0;
		int modo = 1;
		boolean fallo = false;
		String ganador = "";
		int dif = 5;
		
		jugando = true;
		
		//Se elige el modo de juego
		do {
			try {
				System.out.println("Modos de juego\n"
								 + "   1. Normal\n"
								 + "   2. Contrarreloj\n"
								 + "   3. Multijugador\n"
								 + "   4. Contra la máquina");
				modo = teclado.nextInt();
				
				ExcepcionModos.comprobarModoJuego(modo);
				fallo = false;
				
			}catch(ExcepcionModos e) {
				System.err.println(e.getMessage());
				fallo = true;
			}catch(InputMismatchException e) {
				System.err.println("El modo debe ser un número");
				teclado.next();
				fallo =true;
			}
		}while(fallo);
		
		//Enunciado
		System.out.println("¡Llega a la esquina inferior derecha para ganar!");
		
		//Se elige el tiempo del contrarreloj
		if(modo == 2) {
			dif = 5;
			
			do {
				try {
					System.out.println("\nElige la dificultad(1-10)");
					dif = teclado.nextInt();
					fallo = false;
					
					if(dif < 0 || dif > 10) {
						System.err.println("La dificultad debe estar entre 1 y 10.");
						fallo = true;
					}
					
				}catch(InputMismatchException e) {
					System.err.println("La dificultad no puede ser una letra.");
					teclado.next();
					fallo = true;
				}
			}while(fallo);
			
			lim = (int)((altura + anchura)*5000/dif);
			System.out.println("Llega antes de " + formatearTiempo(lim));
		}
		
		//Se crea al otro jugador
		if(modo == 3) {
			ene = new Jugador(anchura-1, 0, this);
		}else if(modo == 4) {
			
			do {
				try {
					System.out.println("Seleccione una dificultad:\n"
									+  "1. Facil\n"
									+  "2. Dificil");
					dif = teclado.nextInt();
		
					fallo = false;
				}catch(InputMismatchException e) {
					System.err.println("La dificultad debe ser un número");
					teclado.next();
					fallo = true;
				}
			}while(fallo);
			
			ene = new Enemigo(anchura-1, 0, this, (dif == 2));
		}
		
		//Juego
		inicio = System.currentTimeMillis();
		while(!victoria && !derrota && ganador == "") {
			dibujarLaberinto();
			
			if(modo == 3) {
				System.out.println("Jugador 1:");
			}
			jug.mover();
			
			if(modo == 3) {
				dibujarLaberinto();
				System.out.println("Jugador 2:");
				((Jugador) ene).mover();
				
				//Comprobación del ganador
				if(jug.getX() == anchura-1 && jug.getY() == altura-1) {
					ganador = "el Jugador 1";
				}
				
				if(ene.getX() == 0 && ene.getY() == altura-1) {
					if(ganador != "") {
						ganador = "Ambos";
					}else {
						ganador = "el Jugador 2";
					}
				}
			}else if(modo == 4) {
				((Enemigo) ene).mover();
				
				//Comprobación del ganador
				if(jug.getX() == anchura-1 && jug.getY() == altura-1) {
					ganador = "el Jugador";
				}
				
				if(ene.getX() == 0 && ene.getY() == altura-1) {
					if(ganador != "") {
						ganador = "Ambos";
					}else {
						ganador = "la máquina";
					}
				}
			}
			
			if(modo != 3 && modo != 4 && jug.getX() == anchura-1 && jug.getY() == altura-1) {
				victoria = true;
			}
			
			//Comprueba si ha pasado el tiempo limite
			if(modo == 2 && System.currentTimeMillis() - inicio > lim) {
				derrota = true;
			}
		}
		
		dibujarLaberinto();
		
		//Se calcula el tiempo que ha tardado en recorrer el laberinto
		long t = System.currentTimeMillis()-inicio;
		String tiempo = formatearTiempo(t);
		
		//Mensaje final
		if(victoria) {
			System.out.println("HAS GANADO");
		}else if(derrota) {
			System.err.println("\nHas perdido\n");
		}else if(modo == 3 || modo == 4) {
			System.out.println("El ganador es " + ganador);
		}
		System.out.println("Has tardado " + tiempo);
		if(modo == 2) {
			System.out.println("(Tenías que llegar en " + formatearTiempo(lim) + ")");
		}
	}
	
	private String formatearTiempo(long ms) {
		ms /= 1000;
		int m = (int)(ms/60);
		int s = (int)(ms%60);
		
		String tiempo = "";
		if(m < 10) {
			tiempo += 0;
		}
		tiempo += m + ":";
		if(s < 10) {
			tiempo += 0;
		}
		tiempo += s;
		
		return tiempo;
	}

	// Rellena el laberinto
	private char[][] rellenarLaberinto() {
		char[][] dibujo = new char[altura * 2 + 1][anchura * 2 * 2 + 1];

		// Se dibuja el contorno
		// Bordes
		for (int i = 0; i < dibujo.length; i++) {
			for (int j = 0; j < dibujo[0].length; j++) {

				if ((i == 0) || (i == dibujo.length - 1)) {
					dibujo[i][j] = '\u2501';// �?

				} else if ((j == 0) || (j == dibujo[0].length - 1)) {
					dibujo[i][j] = '\u2503';// ┃

				}else if(jug != null && (jug.getX()*4+2 == j && jug.getY()*2+1 == i)) { 
					dibujo[i][j] = Jugador.c;
					
				}else if(ene != null && (ene.getX()*4+2 == j && ene.getY()*2+1 == i)){
					dibujo[i][j] = Enemigo.c;
					
				}else if(anchura*4-2 == j && altura*2-1 == i && jugando){// Meta negra
					dibujo[i][j] = '\u25A0';
					
				}else if(ene != null && 2 == j && altura*2-1 == i && jugando){// Meta blanca
					dibujo[i][j] = '\u25A1';
					
				}else {
					dibujo[i][j] = ' ';
				}
			}
		}

		// Esquinas 		
		dibujo[0][0] = '\u250F'; // Arr-izq NO �?
		dibujo[dibujo.length - 1][0] = '\u2517'; // Ab-izq SO ┗
		dibujo[0][dibujo[0].length - 1] = '\u2513'; // Arr-der NE ┓
		dibujo[dibujo.length - 1][dibujo[0].length - 1] = '\u251B'; // Ab-der SE ┛

		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < anchura; x++) {
				int xdib = x * 2 * 2 + 2;
				int ydib = y * 2 + 1;

				if (mapa[y][x].tieneNorte()) {
					dibujo[ydib - 1][xdib] = '\u2501';// �?
					dibujo[ydib - 1][xdib - 1] = '\u2501';
					dibujo[ydib - 1][xdib + 1] = '\u2501';
				}
				if (mapa[y][x].tieneEste()) {
					dibujo[ydib][xdib + 2] = '\u2503';// ┃
				}
				if (mapa[y][x].tieneSur()) {
					dibujo[ydib + 1][xdib] = '\u2501';// �?
					dibujo[ydib + 1][xdib - 1] = '\u2501';
					dibujo[ydib + 1][xdib + 1] = '\u2501';
				}
				if (mapa[y][x].tieneOeste()) {
					dibujo[ydib][xdib - 2] = '\u2503';// ┃
				}

				/*
				 * Para cada celda dibuja todas las esquinas
				 */

				Celda actual = mapa[y][x];
				Celda cNorte = new Celda();
				Celda cEste = new Celda();
				Celda cSur = new Celda();
				Celda cOeste = new Celda();
				if (y > 0) {
					cNorte = mapa[y - 1][x];
				}
				if (x > 0) {
					cOeste = mapa[y][x - 1];
				}

				// NO
				// Tiene norte
				if (actual.tieneNorte()) {

					// Tiene oeste
					if (actual.tieneOeste()) {
						dibujo[ydib - 1][xdib - 2] = '\u250F';// �?

						if (cNorte.tieneOeste()) {
							dibujo[ydib - 1][xdib - 2] = '\u2523';// ┣
						} else if (cOeste.tieneNorte()) {
							dibujo[ydib - 1][xdib - 2] = '\u2533';// ┳
						}

						if (cOeste.tieneNorte() && cNorte.tieneOeste()) {

							dibujo[ydib - 1][xdib - 2] = '\u254B';// ╋
						}
					// No tiene oeste
					} else {
						dibujo[ydib - 1][xdib] = '\u2501';// �?
						dibujo[ydib - 1][xdib - 1] = '\u2501';
						dibujo[ydib - 1][xdib + 1] = '\u2501';
						dibujo[ydib - 1][xdib - 2] = '\u2501';
						
						if (cNorte.tieneOeste()) {
							dibujo[ydib - 1][xdib - 2] = '\u2517';// ┗
						}
						
						if(cNorte.tieneOeste() && cOeste.tieneNorte()) {
							dibujo[ydib - 1][xdib - 2] = '\u253B';// ┻
						}
					}
				// No tiene norte
				} else {
					// Tiene oeste
					if (actual.tieneOeste()) {
						dibujo[ydib - 1][xdib - 2] = '\u2503';// ┃

						if (cOeste.tieneNorte()) {
							dibujo[ydib - 1][xdib - 2] = '\u2513';// ┓
						} else if (!cNorte.tieneOeste()) {
							dibujo[ydib - 1][xdib - 2] = '\u257B';// ╻ (medio)
						}

						if (cOeste.tieneNorte() && cNorte.tieneOeste()) {
							dibujo[ydib - 1][xdib - 2] = '\u252B';// ┫
						}
					}
				}

				// NE
				// Tiene norte
				if (actual.tieneNorte()) {

					// Tiene este
					if (actual.tieneEste()) {
						dibujo[ydib - 1][xdib + 2] = '\u2513'; // ┓

						if (cNorte.tieneEste()) {
							dibujo[ydib - 1][xdib + 2] = '\u252B';// ┫
						} else if (cEste.tieneNorte()) {
							dibujo[ydib - 1][xdib + 2] = '\u2533';// ┳
						}

						if (cEste.tieneNorte() && cNorte.tieneEste()) {

							dibujo[ydib - 1][xdib + 2] = '\u254B';// ╋
						}
					// No tiene este
					} else {
						dibujo[ydib - 1][xdib] = '\u2501';// �?
						dibujo[ydib - 1][xdib - 1] = '\u2501';
						dibujo[ydib - 1][xdib + 1] = '\u2501';
						dibujo[ydib - 1][xdib + 2] = '\u2501';

						if (cNorte.tieneEste()) {
							dibujo[ydib - 1][xdib + 2] = '\u251B'; // ┛
						}
						
						if(cNorte.tieneEste() && cEste.tieneNorte()) {
							dibujo[ydib - 1][xdib + 2] = '\u253B';// ┻
						}
					}
					// No tiene norte
				} else {
					// Tiene este
					if (actual.tieneEste()) {
						dibujo[ydib - 1][xdib + 2] = '\u2503';// ┃

						if (cEste.tieneNorte()) {
							dibujo[ydib - 1][xdib + 2] = '\u250F'; // �?
						} else if (!cNorte.tieneEste()) {
							dibujo[ydib - 1][xdib + 2] = '\u257B';// ╻ (medio)
						}

						if (cEste.tieneNorte() && cNorte.tieneEste()) {
							dibujo[ydib - 1][xdib + 2] = '\u2523';// ┣
						}
					}
				}

				// SO
				// Tiene sur
				if (actual.tieneSur()) {

					// Tiene oeste
					if (actual.tieneOeste()) {
						dibujo[ydib + 1][xdib - 2] = '\u2517';// ┗

						if (cSur.tieneOeste()) {
							dibujo[ydib + 1][xdib - 2] = '\u2523';// ┣
						} else if (cOeste.tieneSur()) {
							dibujo[ydib + 1][xdib - 2] = '\u253B';// ┻
						}

						if (cOeste.tieneSur() && cSur.tieneOeste()) {

							dibujo[ydib + 1][xdib - 2] = '\u254B';// ╋
						}
					// No tiene oeste
					} else {
						dibujo[ydib + 1][xdib] = '\u2501';// �?
						dibujo[ydib + 1][xdib - 1] = '\u2501';
						dibujo[ydib + 1][xdib + 1] = '\u2501';
						dibujo[ydib + 1][xdib - 2] = '\u2501';

						if (cSur.tieneOeste()) {
							dibujo[ydib + 1][xdib - 2] = '\u250F'; // �?
						}
						
						if(cSur.tieneOeste() && cOeste.tieneSur()) {
							dibujo[ydib + 1][xdib - 2] = '\u253B';// ┻
						}
					}
				// No tiene sur
				} else {
					// Tiene oeste
					if (actual.tieneOeste()) {
						dibujo[ydib + 1][xdib - 2] = '\u2503';// ┃

						if (cOeste.tieneSur()) {
							dibujo[ydib + 1][xdib - 2] = '\u251B'; // ┛
						} else if (!cSur.tieneOeste()) {
							dibujo[ydib + 1][xdib - 2] = '\u2579';// ╹ (medio)
						}

						if (cOeste.tieneSur() && cSur.tieneOeste()) {
							dibujo[ydib + 1][xdib - 2] = '\u252B';// ┫
						}
					}
				}

				// SE
				// Tiene sur
				if (actual.tieneSur()) {

					// Tiene este
					if (actual.tieneEste()) {
						dibujo[ydib + 1][xdib + 2] = '\u251B'; // ┛

						if (cSur.tieneEste()) {
							dibujo[ydib + 1][xdib + 2] = '\u252B';// ┫
						} else if (cEste.tieneSur()) {
							dibujo[ydib + 1][xdib + 2] = '\u253B';// ┻
						}

						if (cEste.tieneSur() && cSur.tieneEste()) {

							dibujo[ydib + 1][xdib + 2] = '\u254B';// ╋
						}
						// No tiene este
					} else {
						dibujo[ydib + 1][xdib] = '\u2501';// �?
						dibujo[ydib + 1][xdib - 1] = '\u2501';
						dibujo[ydib + 1][xdib + 1] = '\u2501';
						dibujo[ydib + 1][xdib + 2] = '\u2501';

						if (cSur.tieneEste()) {
							dibujo[ydib + 1][xdib + 2] = '\u2513'; // ┓
						}
						
						if(cSur.tieneEste() && cEste.tieneSur()) {
							dibujo[ydib + 1][xdib + 2] = '\u253B';// ┻
						}
					}
					// No tiene sur
				} else {
					// Tiene este
					if (actual.tieneEste()) {
						dibujo[ydib + 1][xdib + 2] = '\u2503';// ┃

						if (cEste.tieneSur()) {
							dibujo[ydib + 1][xdib + 2] = '\u2517';// ┗
						} else if (!cSur.tieneEste()) {
							dibujo[ydib + 1][xdib + 2] = '\u2579';// ╹ (medio)
						}

						if (cEste.tieneSur() && cSur.tieneEste()) {
							dibujo[ydib + 1][xdib + 2] = '\u2523';// ┣
						}
					}
				}
			}
		}

		return dibujo;
	}

	// Genera un laberinto aleatorio
	public void generarLaberinto(boolean guardar, int modo) {
		Scanner teclado = new Scanner(System.in);
		long ultimoPaso = System.currentTimeMillis();
		int vel = 10000/(altura * anchura);// La velocidad escala con el área del laberinto

		while (!arq.terminado) {// Cada paso de la construccion
			
			if(modo == 1) {// Paso a paso
				arq.mover();
				dibujarLaberinto();
				String espera = teclado.nextLine();
				
			}else if (modo == 2 && System.currentTimeMillis() - ultimoPaso > vel){// Automático
				arq.mover();
				dibujarLaberinto();
				ultimoPaso = System.currentTimeMillis();
				// System.out.println("Velocidad: " + vel);
				
			}else if(modo == 3){// Ver terminado
				arq.mover();
			}
		}
		
		if(modo == 3) {
			dibujarLaberinto();
		}
		
		if(guardar) {
			try {
				guardarLaberinto();
				
			}catch(FileNotFoundException e) {
				System.err.println("Error al abrir el archivo.");
				System.exit(0);
			}
		}
	}

	// Getters
	public Celda[][] getMapa() {
		return mapa;
	}

	public Celda getCelda(int y, int x) {
		return mapa[y][x];
	}

	public Arquitecto getArq() {
		return arq;
	}

	public int getAltura() {
		return altura;
	}

	public int getAnchura() {
		return anchura;
	}
	
	public Jugador getJugador() {
		return jug;
	}
	public void setJugador(Jugador jug) {
		this.jug = jug;
	}
	
	public Entidad getEnemigo() {
		return ene;
	}
	public void setEnemigo(Enemigo ene) {
		this.ene = ene;
	}
	public void setEnemigo(Jugador ene) {
		this.ene = ene;
	}

	public int getVerticalidad() {
		return verticalidad;
	}
	public void setVerticalidad(int verticalidad) {
		this.verticalidad = verticalidad;
	}

	public int getHorizontalidad() {
		return horizontalidad;
	}
	public void setHorizontalidad(int horizontalidad) {
		this.horizontalidad = horizontalidad;
	}
}
