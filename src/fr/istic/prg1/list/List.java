package fr.istic.prg1.list;

import fr.istic.prg1.list_util.SuperT;
import fr.istic.prg1.list_util.Iterator;

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

		@Override
		public void goForward() { 
			current = current.right;
		}

		@Override
		public void goBackward() { 
			current = current.left;
		}

		@Override
		public void restart() {
			current = flag.right;
		}

		@Override
	    public boolean isOnFlag() { 
			return current == flag; 
		}

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

		@Override		 
		public T getValue() { return current.value; }

		@Override
	        public T nextValue() { goForward(); return current.value;  }

		@Override
		public void addLeft(T v) { 
			Element leftNeighbor = current.left;
			
			Element elem = new Element (v);
			
			elem.left = leftNeighbor;
			elem.right = current;
			current.left = elem;
			leftNeighbor.right = elem;

		}

		@Override
		public void addRight(T v) {
			Element rightNeighbor = current.right;
			
			Element elem = new Element (v);
			
			elem.left = current;
			elem.right = rightNeighbor;
			current.right = elem;
			rightNeighbor.left = elem;
			
		}

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

	public boolean isEmpty() { 
		return flag.right == flag && flag.left == flag; 
	}

	public void clear() {  
		setFlag(flag.value); 
	}

	public void setFlag(T v) {
        flag = new Element(v);
        flag.left = flag;
        flag.right = flag;
	}

	public void addHead(T v) { 
        Element elt = new Element(v);
        
        elt.right = flag.right;
        elt.left = flag;
        flag.right.left = elt;
        flag.right = elt;
			
	}

	public void addTail(T v) { 
		Element elt = new Element(v);
		
	    elt.left = flag.left;
	    elt.right = flag;
	    flag.left.right = elt;
	    flag.left = elt;
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
