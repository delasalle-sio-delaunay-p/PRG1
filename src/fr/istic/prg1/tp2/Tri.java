package fr.istic.prg1.tp2;

import java.util.Scanner;

public class Tri {

	/**
	 * Classe Tri
	 * @author Pierre DELAUNAY, Wiem NABLI
	 * @version 1.0.0
	 * @since 19-09-2018
	 */
	
	private static Scanner sc = new Scanner (System.in);
    
	/**
	 * Tri naïf
	 * @param int []tab
	 * @param int n
	 * @param int a
	 * @return boolean
	 */
	public static void triNaif(int[] tab, int n) {
	
		for (int i = 0; i <= n-2; i++) 
		{
			int rangmin = i;
			
			for (int j=i+1; j<=n-1; j++) 
			{
				if (tab[j] < tab[rangmin]) 
				{
					rangmin=j;
				}
				int aux=tab[i];
				tab[i]=tab[rangmin];
				tab[rangmin]=aux;
			}
		}
		
	}
	
	/**
	 * Recherche dichotomique
	 * @param int []tab
	 * @param int n
	 * @param int a
	 * @return boolean
	 */
	public static boolean rechDich (int []tab,int n,int a)
	{
		int deb=0;
		int fin=n-1;
		int milieu=((deb+fin)/2);
		while (deb<=fin && a!=tab[milieu])
		{
			if (a<tab[milieu])
				fin=milieu-1;
			if (a>tab[milieu])
				deb=milieu+1;
			milieu=(deb+fin)/2;
		}
		return (deb<=fin);
		
		
	}
	
	/**
	 * Affiche les éléments d'un tableau
	 * @param int []tab
	 * @param int
	 */
	public static void affichTab (int []tab, int n)
	{
		for (int i=0;i<n;i++)
		{
			System.out.println(tab[i]);
		}
	}
	
	public static void main(String[] args) {
		int n;
		int [] tab = new int [60];
		int x;
		
	  do 
	  { System.out.println("Entrez la taille du tableau:");
	    n= sc.nextInt();
	  } while (n<1 || n>50);
	  
	  for (int i=0; i<n; i++)
	  {
		  System.out.println("Entrer la case:");
		  tab[i]=sc.nextInt();
		  
	  }
	  
	  System.out.println("Tableau avant le tri");
	  affichTab(tab,n);
	  System.out.println("Tableau après le tri");
	  triNaif (tab,n);
	  affichTab(tab,n);
	  System.out.println("Donnez une valeur à rechercher:");
	  x=sc.nextInt();
	  boolean rep = rechDich(tab,n,x);
	  System.out.println(rep);
		
	}

}
