package fr.istic.prg1.tp4;

public class SmallSet {
	
	/**
	 * Attributs
	 */

	private boolean[] tab = new boolean[256];
	
	/**
	 * Constructeurs
	 */
	
	public SmallSet () {
		for (int i = 0; i <= 255; ++i) {
			tab[i] = false;
		}
	}
	
	public SmallSet (boolean[] t) {
		for (int i = 0; i <= 255; ++i) {
			tab[i] = t[i];
		}		
	}
	
	/**
	 * @return nombre de valeurs appartenant à l'ensemble
	 */
	public int size () {
		int count = 0;
		
		for (int i = 0; i <= 255; ++i) {
			if (this.tab[i]) 
			{ ++count; }
		}
		
		return count;
		
	}
	
	/**
	 * @param x valeur à tester
	 * @pre 0 <= x <= 255
	 * @return true, si l'entier x appartient à l'ensemble, false sinon
	 */
	public boolean contains (int x) {
		
		if (tab[x]==true)
			return true;
		else 
			return false;
		
	}
	
	/**
	 * @return true, si l'ensemble est vide, false sinon
	 */
	public boolean isEmpty () {
		
		int i=0;
		while (tab[i]==false && i<256)
		{
			++i;
		}
		if (i<256)
			return false;
		else 
			return true;	
	}
	
	/**
	 * Ajoute x à l'ensemble (sans effet si x déjà présent)
	 * 
	 * @param x valeur à ajouter
	 * @pre 0 <= x <= 255
	 */
	public void add (int x) {
		
		if (!this.contains(x) && x >= 0 && x < 256) {
			this.tab[x] = true;
		}	
	}

	/**
	 * Retire x de l'ensemble (sans effet si x n'est pas présent)
	 * @param x valeur à supprimer
	 * @pre 0 <= x <= 255
	 */
	public void remove (int x) {
		
		if (contains(x)==true) {
			this.tab[x] = false;
		}	
	}
	
	/**
	 * Ajoute à l'ensemble les valeurs deb, deb+1, deb+2, ..., fin
	 * 
	 * @param begin début de l'intervalle
	 * @param end fin de l'intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void addInterval (int deb, int fin) {
		
		for (int i = deb; i <= fin; ++i) {
			this.tab[i] = true;
		}
	}

	/**
	 * Retire de  l'ensemble les valeurs deb, deb+1, deb+2, ..., fin
	 * 
	 * @param begin début de l'intervalle
	 * @param end fin de l'intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void removeInterval (int deb, int fin) {
		
		for (int i = deb; i <= fin; ++i) {
			this.tab[i] = false;
		}	
	}
	
	/**
	 *  Réalise l'opération this = this U set2
	 * @param set2 second ensemble
	 */
	public void union (SmallSet set2) {
		
		for (int i = 0; i <= 255; ++i) {
			
			if (set2.contains(i)) {
				this.tab[i] = true;
			}
			
		}		
	}
	
	/**
	 *  Réalise l'opération this = this INTER set2
	 * @param set2 second ensemble
	 */
	public void intersection (SmallSet set2) {
		
		for (int i = 0; i <= 255; ++i) {
			
			if ( !(set2.tab[i] == this.tab[i]) ) {
				 this.tab[i] = false;
			}		
		}			
	}
	
	/**
	 *  Réalise l'opération this = this \ set2
	 * @param set2 second ensemble
	 */
	public void difference (SmallSet set2) {
		
		for (int i = 0; i <= 255; ++i) {
			
			if ( (set2.tab[i]==true) ) {
				 this.tab[i] = false;
			}		
		}		
	}
	
	/**
	 *  Réalise l'opération this = this DELTA set2
	 * @param set2 second ensemble
	 */
	public void symmetricDifference (SmallSet set2) {
		
		/*
		for (int i = 0; i <256/2; ++i) {
			int j= (255/2)+1+i;
			if (set2.tab[j]==true) 
			  this.tab[i]=false; 	
		}
		*/

		
		for (int i = 0; i <= 255; ++i) {
			
			if ( set2.tab[i]== this.tab[i]  ) {
				 this.tab[i] = false;
			}
			else {
				this.tab[i] = true;
			}
		}
		
	}
	
	/**
	 * Réalise l'opération this = NOT(this)
	 */
	public void complement () {
		
		for (int i = 0; i <= 255; ++i) {
			if(this.tab[i]) 
			    this.tab[i] = false; 
			else 
				this.tab[i] = true; 
		}			
	}
	
	/**
	 * Réalise l'opération this = 0
	 */
	public void clear () {
		
		for (int i = 0; i <= 255; ++i) {
			this.tab[i] = false;
		}			
	}
	
	/**
	 * 
	 * @param set2 second ensemble
	 * @return true, si this C set2, false sinon
	 */
	public boolean isIncludedIn (SmallSet set2) {
		
		for (int i = 0; i <= 255; ++i) {
			if (tab[i]==true && tab[i]!=set2.tab[i])
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param copie de this
	 */
	public SmallSet clone () {
		
		SmallSet copie = new SmallSet ();
		
		for (int i = 0; i <= 255; ++i) {
			copie.tab[i] = this.tab[i];
		}	
		
		return copie;	
	}
	
	/**
	 * @return true, si this et set2 sont égaux, false sinon
	 */
	@Override
	public boolean equals (Object set2) {
		
		if (set2==null)
			return false;
		else if (!(set2 instanceof SmallSet)) 
			return false;
		else if (isIncludedIn((SmallSet)set2))
			return false;
		else if (this == set2) 
			return true;
		else
			return true;
		}
	
	@Override
	public String toString () {
		String s = "éléments présents : ";
		for (int i = 0; i <= 255; ++i) {
			if (tab[i]) { 
				s = s + i + " "; 
			}
		}
		return s;
	}
}
