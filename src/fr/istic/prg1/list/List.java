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
		 * Positionner l'élément courant sur son voisin droit
		 */
		@Override
		public void goForward() { 
			current = current.right;
		}
		
		/**
		 * Positionner l'élément courant sur son voisin gauche
		 */
		@Override
		public void goBackward() { 
			current = current.left;
		}
		
		/**
		 * Positionner l'élément courant sur l'élément de tête 
		 */
		@Override
		public void restart() {
			current = flag.right;
		}

		/**
		 * Vérifier si l'élément courant est positionné sur le drapeau
		 * @return boolean
		 */
		@Override
	    public boolean isOnFlag() { 
			return current == flag; 
		}

		/**
		 * Supprimer l'élément courant, l'élément courant se positionne à droite de l'élément supprimé
		 * @pre l'élément courant n'est pas le drapeau
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
		 * Retourner la valeur de l'élément courant
		 */
		@Override		 
		public T getValue() { return current.value; }
		
		/**
		 * Avancer l'itérateur et retourner la valeur du nouvel élément courant
		 */
		@Override
	    public T nextValue() { goForward(); return current.value;  }

		/**
		 * Ajouter v à gauche de l'élément courant et positionner l'élément courant sur le nouvel élément
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
		 * Ajouter v à droite de l'élément courant et positionner l'élément courant sur le nouvel élément
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
		 * Met à jour la valeur de l'élément courant
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
		flag = null; 
	}

	public ListIterator iterator() { 
		return new ListIterator(); 
	}
	
	/**
	 * Vérifie si la liste est vide
	 * @return true si la liste vide, false sinon
	 */
	public boolean isEmpty() { 
		return flag.right == flag && flag.left == flag; 
	}

	/**
	 * Supprimer toutes les valeurs de la liste
	 */
	public void clear() {  
		setFlag(flag.value); 
	}

	/**
	 * Affecter la valeur v au drapeau
	 * @param v
	 */
	public void setFlag(T v) {
        flag = new Element(v);
        flag.left = flag;
        flag.right = flag;
	}

	/**
	 * Ajouter v en tête de liste
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
