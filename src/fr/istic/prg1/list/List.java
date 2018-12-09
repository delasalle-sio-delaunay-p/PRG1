package fr.istic.prg1.list;

import fr.istic.prg1.list_util.SuperT;
import fr.istic.prg1.list_util.Iterator;

/**
 * Classe List - TP5
 * @author NABLI Wiem, DELAUNAY Pierre
 * @version 1.0.1
 * @since 2018-11-14
 */

public class List<T extends SuperT> {
	// liste en double chainage par references

	private class Element {
		// element de List<Item> : (Item, Element, Element)
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}
		
		public Element(T val) { 
			value = val; 
			left = null; 
			right = null; 
		}
		
	} // class Element

	public class ListIterator implements Iterator<T> {
		private Element current;

		private ListIterator() { 
			current = flag.right;
		}
		
		/**
		 * Positionner l'�l�ment courant sur son voisin droit
		 */
		@Override
		public void goForward() { 
			current = current.right;
		}
		
		/**
		 * Positionner l'�l�ment courant sur son voisin gauche
		 */
		@Override
		public void goBackward() { 
			current = current.left;
		}
		
		/**
		 * Positionner l'�l�ment courant sur l'�l�ment de t�te 
		 */
		@Override
		public void restart() {
			current = flag.right;
		}

		/**
		 * V�rifier si l'�l�ment courant est positionn� sur le drapeau
		 * @return boolean
		 */
		@Override
	    public boolean isOnFlag() { 
			return current == flag; 
		}

		/**
		 * Supprimer l'�l�ment courant, l'�l�ment courant se positionne � droite de l'�l�ment supprim�
		 * @pre l'�l�ment courant n'est pas le drapeau
		 */
		@Override
		public void remove() {
			try {
				assert current != flag : "\n\n\nimpossible de retirer le drapeau\n\n\n";
				Element leftNeighbor = current.left;
				Element rightNeighbor = current.right;
				leftNeighbor.right = rightNeighbor;
				rightNeighbor.right = leftNeighbor;
				current = rightNeighbor;
				
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Retourner la valeur de l'�l�ment courant
		 */
		@Override		 
		public T getValue() { return current.value; }
		
		/**
		 * Avancer l'it�rateur et retourner la valeur du nouvel �l�ment courant
		 */
		@Override
	    public T nextValue() { goForward(); return current.value;  }

		/**
		 * Ajouter v � gauche de l'�l�ment courant et positionner l'�l�ment courant sur le nouvel �l�ment
		 */
		@Override
		public void addLeft(T v) { 
            Element elem = new Element(v);
            
            elem.left = current.left;
            elem.right = current;
            current.left.right = elem;
            current.left = elem;
            
            goBackward();
		}
		
		/**
		 * Ajouter v � droite de l'�l�ment courant et positionner l'�l�ment courant sur le nouvel �l�ment
		 */
		@Override
		public void addRight(T v) {
            Element elem = new Element(v);
            
            elem.right = current.right;
            elem.left = current;
            current.right.left = elem;
            current.right = elem;
            
            goForward();		
		}

		/**
		 * Met � jour la valeur de l'�l�ment courant
		 */
		@Override
		public void setValue(T v) { 
			current.value = v; 
		}

		@Override
		public String toString() {
			return "parcours de liste : pas d'affichage possible \n";
		}

	} // class IterateurListe

	private Element flag;

	public List() { 	
		flag = new Element();
		flag.left = flag;
		flag.right = flag;
	}

	public ListIterator iterator() { 
		return new ListIterator(); 
	}
	
	/**
	 * V�rifie si la liste est vide
	 * @return true si la liste vide, false sinon
	 */
	public boolean isEmpty() { 
		return flag.right == flag && flag.left == flag; 
	}

	/**
	 * Supprimer toutes les valeurs de la liste
	 */
	public void clear() {  
        flag.left = flag;
        flag.right = flag;
	}

	/**
	 * Affecter la valeur v au drapeau
	 * @param v
	 */
	public void setFlag(T v) {
		setFlag(v); 
	}

	/**
	 * Ajouter v en t�te de liste
	 * @param v
	 */
	public void addHead(T v) { 
        Element elem = new Element(v);
        
        elem.right = flag.right;
        elem.left = flag;
        flag.right.left = elem;
        flag.right = elem;
			
	}

	/**
	 * Ajouter v en queue de liste
	 * @param v
	 */
	public void addTail(T v) { 
		Element elem = new Element(v);
		
	    elem.left = flag.left;
	    elem.right = flag;
	    flag.left.right = elem;
	    flag.left = elem;
	}

	@SuppressWarnings("unchecked")
	public List<T> clone() {
		List<T> nouvListe = new List<T>();
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().clone());
			// UNE COPIE EST NECESSAIRE !!!
			p.goForward();
		}
		return nouvListe;
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}
