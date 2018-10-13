package fr.istic.prg1.tp3;

import java.util.Scanner;

public class InsertionPair {
	
	/**
	 * @author DELAUNAY Pierre, NABLI Wiem
	 * @version 1.0.2
	 * @since 2018-10-10
	 */

	private static final int SIZE_MAX = 10;
	private int size;
	private Pair[] array = new Pair[SIZE_MAX];


	/**
	 * Constructeur
	 */
	public InsertionPair(){
		size = 0;
	}

	/**
	 * @return copie de la partie remplie du tableau
	 */
	public Pair[] toArray(){
		Pair[] copie = new Pair[size];
		if (size > 0) {
			for (int i = 0; i < size; ++i) {
				Pair p = new Pair(array[i]);
				copie[i] = p;
			}
		}
		return copie;
	}

	/**
	 * @param Pair p
	 * 		  doublet à insérer
	 * @pre les doublets sont triés par ordre croissant
	 * @return boolean, false si p déjà présent ou array plein, true si p non présent
	 */
	public boolean insert(Pair p) {
		boolean res = false;
		// si le tableau a atteint la taille max
		if (size == SIZE_MAX) {
			res = false;
		} else {
			int i = 0;
			boolean fini = false;
			
			while (!fini && i < size) {
				if (array[i].less(p)) {
					++i;
			    // si le doublet est déjà présent
				} else if (array[i].equals(p)) {
					fini = true;
				} else {
					++size;
					for (int j = size-1; j > i; --j){
						array[j] = array[j-1];
					}
					array[i] = p;
					fini = true;
					res = true;
					
				}
			}
			if (i == size) {
				array[size] = p;
				++size;
				res = true;
			}

		}

		return res;
	}


	/**
	 * @return une chaine contenant la taille et les éléments du tableau
	 */
	public String toString() {
		String str = "";

		str = str + "Tableau : size = " + size + " ";

		for (int i = 0; i < size; ++i) {
			str = str + array[i].toString() + " ";
		}
		
		return str;
	}	


	/**
	 * array est rempli, par ordre croissant, en utilisant la fonction insert avec les valeurs lues par scanner
	 * @param scanner
	 * @pre l'utilisateur saisit un entier
	 */
	public void createArray(Scanner scanner) {
		int premierIntSaisi = scanner.nextInt();
		int deuxiemeIntSaisi;
		boolean fini = false;
		while ((premierIntSaisi != -1) && !fini) {
			deuxiemeIntSaisi = scanner.nextInt();
			if (deuxiemeIntSaisi != -1) {
				Pair p = new Pair(premierIntSaisi, deuxiemeIntSaisi);
				this.insert(p);
				premierIntSaisi = scanner.nextInt();
			} else {
				fini = true;
			}
		}
		
	}
	
	
}