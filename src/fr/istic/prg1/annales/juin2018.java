package fr.istic.prg1.annales;

import fr.istic.prg1.tp3.Pair;
import fr.istic.prg1.tree.BinaryTree;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

public class juin2018 {

	/** 
	 * EXERCICE 1
	 */
	
	public boolean hasBought(Client client, int productId) {	
		if(this.contains(client)){
			ClientProduct cp = this.getValue(client);
			Iterator<Integer> it = cp.iterator();	
			// On parcourt les produits
			while(it.hasNext()){
				Integer i = it.next();
				if(cp.getValue(i).productID == productID) { return true; }
			}
		}
		return false;	
	}
	
	public int popularity(int productId) {
		int popularity = 0;		
		// Iterator sur les clés de this (de type Client)
		Iterator<Client> it = this.iterator();
		
		while (it.hasNext()){
			if ( this.hasBought(it.next(), productId) ) {	
				popularity++;
			}
		}	
		return popularity;
	}
		
	public int productNumber() {
		assert (!this.isEmpty()) : "this vide";
		int count = 0;
		
		// Iterator sur les clés de this (de type Client)
		Iterator<Client> it = this.iterator();
		
		while (it.hasNext()){
			ClientProducts cp = this.getValue(it.next());
			if (!cp.isEmpty()) { count += cp.size(); };
		}
	}
	
	public int clientCount(int price, int quantity) {

		assert (!this.isEmpty()) : "this vide";
		int countProduct = 0, countClient = 0;
		
		// Iterator sur les clés de this (de type Client)
		Iterator<Client> it = this.iterator();
		
		while (it.hasNext()){
			ClientProducts cp = this.getValue(it.next());
		
			// Iterator sur les products clés de cp (de type Integer)
			Iterator<Integer> it1 = cp.iterator();
			
			while(it1.hasNext()){
			
				Product prd = cp.getValue(it1.next());
				if (prd.quantity >= quantity && prd.price > price) { countProduct++; }
			}			
			if(countProduct > 0){ countClient ++; countProduct = 0; }
		}
		
		return countClient;
	}
	
	public void returnProducts(Table<Client, Set<Product>> returnTable) {
		
		assert (!this.isEmpty()) : "this vide";
		assert (!returnTable.isEmpty()) : "returnTable vide";
		
		Iterator<Client> it = returnTable.iterator();
		
		while(it.hasNext()){
			
			Client rtClient = it.next();
			Set<Product> set = returnTable.getValue(rtClient);
			
			Iterator<Product> it1 = set.iterator();
			
			while (it1.hasNext()) {
				
				Integer i = it1.next();
				Product rtPrd = set.getValue(i);
				
				// mise à jour de la quantité
				this.getValue(rtClient).getValue(rtPrd).quantity -= rtPrd.quantity;
				
			}
			
		}		
	}

	
	/** 
	 * EXERCICE 2
	 */
	
	public static void printSomeValues(BinaryTree<Integer> tree) {
		
		assert (!tree.isEmpty()) : "tree vide";
		Iterator<Integer> it = tree.iterator();
		printSomeValues(it);
		
	}

	private static void printSomeValuesAux(Iterator<Integer> it) {
		
		if (!it.isEmpty()) {
	
			int v = it.getValue();
			if ( (v % 5) == 0) { System.out.println(v);}
			
			it.goLeft();
			printSomeValuesAux(it);
			it.goUp(); it.goRight();
			printSomeValuesAux(it);
			it.goUp(); 	
			
		}
			
	}

	public static int simpleNodeCount(BinaryTree<Integer> tree)

		if (!tree.isEmpty()) { return -1; }
		else {
			Iterator<Node> it = tree.iterator();	
			return simpleNodeCountAux(it);			
		}
	}
	
	private int simpleNodeCountAux(Iterator<Node> it) {
		int count = 0;

		NodeType ntype = it.nodeType();

		switch (ntype) {
		case SIMPLE_LEFT:
			it.goLeft();
			count += numberOfNodesAux(it);
			it.goUp();
			break;
		case SIMPLE_RIGHT:
			it.goRight();
			count += numberOfNodesAux(it);
			it.goUp();
			break;
		case DOUBLE:
			it.goLeft();
			numberOfNodesAux(it);
			it.goUp(); it.goRight();
			numberOfNodesAux(it);
			it.goUp();
		default: // sentinel, leaf
			return 0;
			break;		
		}
		
		return count;
	}
	
	
	public static BinaryTree<Integer> merge(BinaryTree<Integer> tree1, BinaryTree<Integer> tree2, int root) {
		
		assert(!tree1.isEmpty() && !tree2.isEmpty()) : "tree1 et tree2 vides";
		
		BinaryTree<Integer> returnTree = new BinaryTree();
		Iterator<Integer> it = tree.iterator();
		Iterator<Integer> it1 = tree1.iterator();
		Iterator<Integer> it2 = tree2.iterator();
		
		// add new root
		it.addValue(root);
		it.goLeft();
		// copy tree1
		mergeAux(it, it1);
		it.goRoot(); it.goRight();
		// copy tree2 
		mergeAux(it, it2);
		it.goRoot();
		
		return returnTree;
	}
	
	// copy pasta affectAux TP6
	private static void mergeAux(Iterator<Integer> it, Iterator<Integer> it1) {
		if (!it.isEmpty()) {
			it.addValue(it1.getValue());
			it.goLeft(); it1.goLeft();
			mergeAux(it, it1);
			it.goUp(); it1.goUp();
			it.goRight(); it1.goRight();
			mergeAux(it, it1);
			it.goUp(); it1.goUp();
		}
	}
		
	public static BinaryTree<Pair> minimumTree(BinaryTree<Integer> tree) {
		
		assert(!tree.isEmpty()) : "tree vide";
		BinaryTree<Pair> returnTree = new BinaryTree();
		Iterator<Pair> it1 = returnTree.iterator();
		Iterator<Integer> it2 = tree.iterator();
		minimumTreeAux(it1, it2);
		
		return returnTree;
	}
	
	private void minimumTreeAux(Iterator<Pair> it1, Iterator<Integer> it2) {
		if (!it2.isEmpty()) {
			
			NodeType ntype = it2.nodeType();
			int value = it2.getValue();
			int min = minValue(it2);
			it1.addValue(new Pair(value, min));
			
			switch (ntype) {
			case SIMPLE_LEFT:
				it2.goLeft();
				minimumTreeAux(it1, it2);
				it2.goUp();
				break;
			case SIMPLE_RIGHT:
				it2.goRight();
				minimumTreeAux(it1, it2);
				it2.goUp();
				break;
			case DOUBLE:
				it2.goLeft();
				minimumTreeAux(it1, it2);
				it2.goUp(); it2.goRight();
				minimumTreeAux(it1, it2);
				it2.goUp();
				break;
			default: // leaf
				break;
			}	
		}
	}
	
	// retourne la valeur min dans un subtree càd la value de la leaf la plus à gauche
	private int minValue(Iterator<Integer> it) {
		assert(!it.isEmpty()) : "iterator sur butoir";
		
		while(!it.isEmpty()) {
			it.goLeft();
		}
		
		return it.getValue().clone();
	}

}
