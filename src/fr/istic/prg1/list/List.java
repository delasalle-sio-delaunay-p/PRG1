package fr.istic.prg1.list;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import fr.istic.prg1.list_util.SuperT;
import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.SmallSet;

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
			
		}

		@Override
		public void goBackward() { 
			
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
			// to do
		}

		@Override
		public void addRight(T v) {
			// to do
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
		
	}

	public void addTail(T v) { 
		
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
