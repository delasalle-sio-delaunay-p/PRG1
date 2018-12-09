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

	
	
    /**
     * Si value n'appartient pas à array[0..size-1] et size > SIZE_MAX, size est incrementé de 1, value est insere dans
     * array et les entiers array[0..size] sont triés par ordre croissant.
     * Sinon array est inhangé.
     *
     * @param value
     *          La valeur à insérer
     * @return false si value appartient à array[0..size-1] ou si array est complètement rempli;
     *          true si value n,appartient pas à array[0..size-1]
     */
    public boolean insert(int value) {
        if (value >= 0 && this.size < SIZE_MAX) {
            int insertPoint = Arrays.binarySearch(this.array, 0, this.size, value);
            boolean exist = insertPoint >= 0;

            // Ajouter value à array si value ne l'appartient pas,
            // incrementer size de 1
            // et trier array
            if (!exist) {
                this.array[this.size] = value;
                this.size++;

                Arrays.sort(this.array, 0, this.size);
            }

            return !exist;
        }

        return false;
    }
    
}
