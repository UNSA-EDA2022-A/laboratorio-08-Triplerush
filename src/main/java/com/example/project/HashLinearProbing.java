package com.example.project;

import java.util.Random;

public class HashLinearProbing {
	private int hsize; // tamano de la tabla hash
	private Persona[] buckets; // array que representa la tabla hash
	private String AVAILABLE;
	private int size; // cantidad de elementos en la tabla hash

	public HashLinearProbing(int hsize) {
		this.buckets = new Persona[hsize];
		this.hsize = hsize;
		this.AVAILABLE = "disponible";// no se repetira porque es un dni de letras
		this.size = 0;
	}

	public int hashing(String key) {
		int tamano = key.length();
		int hash = Integer.parseInt(key.substring(tamano - Integer.toString(hsize).length() , tamano)) % hsize;// Modulo de las ultimas cifras del dni
		if (hash < 0) {
			hash += hsize;
		}
		return hash;
	}

	public void insertHash(Persona key) {
		Persona wrappedPersona = key;
		int hash = hashing(key.DNI);

		if (isFull()) {
			System.out.println("Tabla hash esta llena!");
			return;
		}

		for (int i = 0; i < hsize; i++) {
			if (buckets[hash] == null || buckets[hash].DNI.equals(AVAILABLE)) {
				buckets[hash] = wrappedPersona;
				size++;
				return;
			}

			if (hash + 1 < hsize) {
				hash++;
			} else {
				hash = 0;
			}
		}
	}

	public void deleteHash(String key) {
		String wrappedPersona = key;
		int hash = hashing(key);

		if (isEmpty()) {
			System.out.println("Tabla hash esta vacia!");
			return;
		}

		for (int i = 0; i < hsize; i++) {
			if (buckets[hash] != null && buckets[hash].DNI.equals(wrappedPersona)) {
				buckets[hash].DNI = AVAILABLE;
				size--;
				return;
			}

			if (hash + 1 < hsize) {
				hash++;
			} else {
				hash = 0;
			}
		}
		System.out.println("Clave " + key + " no encontrada");
	}

	public void displayHashtable() {
		for (int i = 0; i < hsize; i++) {
			if (buckets[i] == null || buckets[i].DNI == AVAILABLE) {
				System.out.println("Celda " + i + ": Vacia");
			} else {
				System.out.println("Celda " + i + ": " + buckets[i].DNI + " - " + buckets[i].nombre);
			}
		}
	}

	public String findHash(String key) {
		String wrappedPersona = key;
		int hash = hashing(key);

		if (isEmpty()) {
			System.out.println("Tabla hash esta vacia!");
			return null;
		}

		for (int i = 0; i < hsize; i++) {
			if (buckets[hash] != null && buckets[hash].DNI.equals(wrappedPersona)) 
				return buckets[hash].nombre;;
			
			if (hash + 1 < hsize) {
				hash++;
			} else {
				hash = 0;
			}
		}
		return null;
	}

	public boolean isFull() {
		return size == hsize;
	}

	public boolean isEmpty() {
		boolean response = true;
		for (int i = 0; i < hsize; i++) {
			if (buckets[i] != null) {
				response = false;
				break;
			}
		}
		return response;
	}

	public static void main(String[] args) {
		HashLinearProbing tb = new HashLinearProbing(50);

		Random rd = new Random();

		for (int i = 0; i < 25; i++) {
			tb.insertHash(new Persona(rd.nextInt(10000) + "", "" + i));
		}

		tb.displayHashtable();
	}
}
