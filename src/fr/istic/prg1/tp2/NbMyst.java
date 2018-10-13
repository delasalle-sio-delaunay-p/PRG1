package fr.istic.prg1.tp2;

public class NbMyst {
	
	/**
	 * Classe Nombre Mystérieux
	 * @author Pierre DELAUNAY, Wiem NABLI
	 * @version 1.0.0
	 * @since 19-09-2018
	 */
	
	public static void main (String[] args)
	{				
		for (int i = 0; i < 1000; ++i) {
			if ( estUnNbMyst(i) == true ) {
				System.out.println( i + " est un nombre mystérieux");
			}
		}		
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
	
	/**
	 * Permet de déterminer si le nombre est mystérieux
	 * @param int
	 * @return boolean
	 */
	public static boolean estUnNbMyst(int n) {
		
		boolean [] tabBool = new boolean [10];
		
		for (int i=0; i< tabBool.length; ++i)
		{
			tabBool[i] = false;
		}
		
		int n2 = n * n;
		int n3 = n2 * n;
		String ch2 = String.valueOf(n2);
		String ch3 = String.valueOf(n3);
		String concat = ch2 + ch3;
		
		for (int i = 0; i < concat.length(); i++) {
			
			char chiffre = concat.charAt(i);
			int chif = Integer.parseInt(String.valueOf(chiffre));
			
			
			if( !tabBool[chif]) 
			{
				
				tabBool[chif] = true;
				
			} 
			else

				return false;
		}
	
		for (int i=0;i< tabBool.length;i++)
		{
			if (!tabBool[i]) 
			return false;;
		}
		
		return true;
		
	}
	
}
