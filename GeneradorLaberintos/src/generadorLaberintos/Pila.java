package generadorLaberintos;

import java.util.ArrayList;

public class Pila {
//Atributos
	ArrayList<int[]> elementos = new ArrayList<int[]>();// 0=y 1=x

//Metodos
	public Pila() {
	}

	// Devuelve si esta vacia la pila
	public boolean estaVacia() {
		return elementos.isEmpty();
	}

	// Devuelve el tamaño
	public int getTamaño() {
		return elementos.size();
	}

	// Añade un elemento a la pila
	public void apilar(int y, int x) {
		int[] nuevo = { y, x };
		elementos.add(nuevo);
	}

	// Elimina un elemento de la pila
	public int[] retirar() {
		int[] arr = elementos.get(getTamaño() - 1);
		elementos.remove(getTamaño() - 1);
		return arr;
	}

	public void mostrar() {
		for (int i = 0; i < getTamaño(); i++) {
			System.out.println("- y:" + elementos.get(i)[0] + " x:" + elementos.get(i)[1]);
		}
	}
}
