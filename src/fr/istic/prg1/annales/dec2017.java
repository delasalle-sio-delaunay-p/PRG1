package fr.istic.prg1.annales;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

public class dec2017 {

	
	public boolean exists(String countryName) {
		
	}
	
	public void printNodeOfDepth(int depth) {
		
		Iterator<Node> it = this.iterator();
		
		printNodeOfDepth(depth, it, it.getValue());		
	}
	
	public void printNodeAux(int depth, Iterator<Node> it, Node root) {
		
		// On est sur la racine
		if( it.getValue() == root ) {
			return;
		}
			
		if(depth == 0) {
			// On est à la bonne profondeur, on peut afficher les noms
			printf( it.getValue().name);
			return;
		}
		else {
			// On commence par la droite (ordre lexicographique)
			it.goRight();
			printNodeOfDepth(depth--);
			it.goUp(); it.goLeft();
			printNodeOfDepth(depth--);
			it.goUp();
		}
		
	}

	
    
}
