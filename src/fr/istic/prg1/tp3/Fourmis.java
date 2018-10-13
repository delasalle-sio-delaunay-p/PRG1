package fr.istic.prg1.tp3;

public class Fourmis {

	/**
	 * @author DELAUNAY Pierre, NABLI Wiem
	 * @version 1.0.2
	 * @since 2018-10-10
	 */
	
	/**
	 * @pre s.length() > 0 
	 * @param String ui
	 * @return le terme suivant de la suite des fourmis
	 */
	public static String next(String ui) {
		
		String str = "";
		
		for (int i = 0; i < ui.length(); ++i) {

			// compteur pour les répétitions d'un chiffre identique dans ui
			int compteur = 1;
			
			// chiffre extrait
			char chiffre = ui.charAt(i);
			
			for (int j = i; j < (ui.length() - 1); ++j)
			{
				if (ui.charAt(i+1) == chiffre) {
					++compteur;
					++i;
				}
			}

			str = str + compteur + chiffre;
		}
		
		return str;
		
	}
	
	
}