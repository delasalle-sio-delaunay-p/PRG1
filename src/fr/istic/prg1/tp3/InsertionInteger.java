package fr.istic.prg1.tp3;

import java.util.Scanner;

public class InsertionInteger {
	
	/**
	 * @author DELAUNAY Pierre, NABLI Wiem
	 * @version 1.0.2
	 * @since 2018-10-10
	 */
	
	/**
	 * Attributs
	 */
	private static final int SIZE_MAX = 10;
	private int size;
	private int[] array = new int[SIZE_MAX];
	
	/**
	 * Constructeur
	 */
	public InsertionInteger() {
		this.size = 0;
	}
		
	/** 
	 * @return copie de la partie remplie du tableau
	 */
	public int[] toArray() {
		int[] copie = new int[size];
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				copie[i] = array[i];
			}
		}
		return copie;
	}
	
	/**
	 * @param value
	 * 		  valeur à insérer
	 * @pre les valeurs de array sont triées par ordre croissant
	 * @return boolean, false si x déjà présent ou array plein, true si x non présent
	 */
	public boolean insert(int value) {
		boolean res = false;
		// si le tableau a atteint la taille max
		if (size == SIZE_MAX) {
			res = false;
		} else {
			int i = 0;
			boolean fini = false;
			
			while (!fini && i < size) {
				
				if (array[i] < value) {
					++i;
				// si la valeur est déjà présente
				} else if (array[i] == value) {
					res = false;
					fini = true;
				} else {
					++size;
					
					for (int j = size-1; j > i; --j){
						array[j] = array[j-1];
					}
					array[i] = value;
					fini = true;
					res = true;
							
				}
			}
			if (i == size) {
				array[size] = value;
				++size;
				res = true;
			}

		}

		return res;
	}
	
	
	/**
	 * array est rempli, par ordre croissant, en utilisant la fonction insert avec les valeurs lues par scanner
	 * @param scanner
	 * @pre l'utilisateur saisit un entier
	 */
	public void createArray(Scanner scanner) {
		int intSaisi = scanner.nextInt();
		while(intSaisi != -1) {
			this.insert(intSaisi);
			intSaisi = scanner.nextInt();
		}
	}
	
	/**
	 * @return une chaine contenant la taille et les éléments du tableau
	 */
	public String toString() {
		String str = "";

		str = str + "Tableau : size = " + size;

		for (int i = 0; i < size; ++i) {
			str = str + " [" + i + "] = " + array[i] + " ";
		}
		
		return str;
	}	
}