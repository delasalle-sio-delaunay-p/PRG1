package fr.istic.prg1.tp4;

import java.awt.List;
import java.util.Scanner;

public class MySet extends List<SubSet> {

	/**
	 * Attributs
	 */
	private static final int MAX_RANG = 128;
	private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG, new SmallSet());
	private static final Scanner sc = new Scanner(System.in);

	/**
	 * Constructeur
	 */
	public MySet() {
		super();
		setFlag(FLAG_VALUE);
	}
	
	/**
	 * @return true si le nombre saisi par l'utilisateur appartient à this, false sinon
	 */
	public boolean contains(int x) {
		
		if ( (x >= 0) && (x <= 32767)  ) {
			
			Iterator<SubSet> it = this.iterator();
			
			while ((!it.isOnFlag()) && (it.getValue().rank < (x/256))) {
				it.goForward();
			}
			
			if (it.getValue().rank == (x/256)) {
				
				return it.getValue().set.contains(x%256);
			}
		}
		
		return false;
		
	}
	
	/**
	 * Supprimer de this toutes les valeurs saisies par l'utilisateur et afficher le nouveau
	 * contenu (arrêt par lecture de -1)
	 * @pre l'utilisateur saisit des entiers
	 */
	public void remove() {
		
		System.out.println( "Veuillez rentrer les valeurs à supprimer (-1 pour finir) : ");
		int x = sc.nextInt();
		Iterator<SubSet> it = this.iterator();
		
		while (x != -1) {
			
			
			while ((!it.isOnFlag()) && (it.getValue().rank < (x/256))) {
				it.goForward();
			}

			if (it.getValue().rank == (x/256)) {
				
				it.getValue().set.remove(x%256);
			}
			
			// Nouveau contenu
			System.out.println( "Le nouveau contenu est le suivant : ");
			
			// Retour sur le drapeau pour la nouvelle valeur
			it.restart();
			x = sc.nextInt();
			
		}
		
	}
	
	/**
	 * this devient l'union de this et set2
	 * @param set2, deuxième ensemble
	 */
	public void union(MySet set2) {
		
		Iterator<SubSet> it = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		while (!it2.isOnFlag()) {
			
			if (it.getValue().rank == it2.getValue().rank) {
				
				it.getValue().set.union(it2.getValue().set);
				it.goForward();
				it2.goForward();
			} else if (it.getValue().rank < it2.getValue().rank) {
				it.goForward();
			}
			else {
				// it2.getValue().rank < it.getValue().rank
				it.addLeft(it2.getValue().clone());
				it2.goForward();				
			}
			
			
		}
		
	}
}
