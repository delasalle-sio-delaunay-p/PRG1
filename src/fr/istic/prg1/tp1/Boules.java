package fr.istic.prg1.tp1;

public class Boules {

	final static int nombreBoules = 10;
	static char[] tableauBoules;

	
	public static char[] lireTableauBoules()
	{
		char[] tab = new char[nombreBoules];
		
		for (int i = 0; i < nombreBoules; ++i)
		{
			tab[i] = Terminal.lireChar();
		}
		
		return tab;
	}
	
	public static void ecrireTableauBoules(char[] tab)
	{
		for (int i = 0; i < nombreBoules; ++i)
		{
			Terminal.ecrireChar(tab[i]);
		}
	}
	
	public static void echange(int i, int j, char[] tab)
	{
		char c = tab[i];
		tab[i] = tab[j];
		tab[j] = c;
	}
	
	public static void photo(int r, int s, int t, char[] tab)
	{
		Terminal.ecrireStringln(" r = " + " s = " + s + " t = " + t);
		ecrireTableauBoules(tab);
		Terminal.ecrireStringln("");
	}
	
	
	public static void main(String args[]) {
		
		Terminal.ecrireString("Suite des " + nombreBoules + " boules : ");
		tableauBoules = lireTableauBoules();

		
		int r = 0, s = 0, t = nombreBoules - 1;
		Terminal.ecrireString("entered");
		
		while ( s != t)
		{
			switch (tableauBoules[s]) 
			{
				
				case 'v' : 
					echange(r, s, tableauBoules);
					++r;
					++s;
					break;
				
				case 'b' : 
					++s;
					break;
				
				case 'r' : 
					echange(s, t, tableauBoules);
					--t;
					++s;
					break;
				
				default : Terminal.ecrireString("erreur :  s = " + s + ", boule = " + tableauBoules[s]);
				System.exit(0);
				break;
				
			} // end switch
			
			photo(r, s, t, tableauBoules);
			
		} // end while
		
		Terminal.ecrireString("Résultat du tri : ");
		ecrireTableauBoules(tableauBoules);
		Terminal.ecrireStringln("");
	}
	
}
