package fr.istic.prg1.annales;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

public class dec2017 {

	/** 
	 * EXERCICE 1
	 */
	
	public boolean isIdPresent(String store, Integer id){
		
		Stock stock = this.getValue(store); 
		
		return stock != null && stock.getValue(id) != null ;
	}
	
	public boolean isSold(String store, String name){
		
		if(!this.contains(store)) { return false; }
		
		Stock s = this.getValue(store);
		
		Iterator<Integer> it = s.iterator();
		
		while(it.hasNext()){
			// it.next() renvoie une clé (integer) de produit
			Product prd = this.getValue(it.next());
			if (prd.name.equals(name)) { return true; }
		}
		
		return false;
	}
	
	
	public String morePopularBrand(String brand1, String brand2){
		
		int count1 = 0;
		int count2 = 0;
		
		Iterator<String> it = this.iterator();
		
		while (it.hasNext()){
			
			Stock s = this.getValue(it.next());
			Iterator<Integer> it1 = s.iterator();
			
			boolean brd1local= false;
			boolean brd2local = false;
			
			while(it1.hasNext() && (!brd1local && !brd2local)){
				
				Product prd = s.getValue(it1.next());
				if (prd.brand.equals(brand1)) brd1local=true;
				if (prd.brand.equals(brand2)) brd2local=true;
				
			}
			
			if(brd1local)count1++;
			if(brd2local)count2++;
		}
		
		return ( count1 >= count2 ) ? brand1 : brand2;
	}	
	
	
	private void addNewStock (StoreStocks stocks){
		
		Iterator<String> it1 = stocks.iterator();
		
		while(it1.hasNext()){
			
			String store = it1.next();
			
			// Si un magasin de stocks est absent de this, il est ajouté avec ses produits
			if (!this.contains(store)){
				// Dans l'ideal il faudrait insérer une "nouvelle" valeur
				// Soit utiliser un clone et un new Stock()
				this.addValue(store, stocks.getValue(store));
			}
			else{
				
				Stock stock1 = this.getValue(store);
				Stock stock2 = stocks.getValue(store);
				
				Iterator<Integer> itA = stock2.iterator();
				
				while(itA.hasNext()){
					
					Integer id1 = itA.next();
					
					// Si un produit d’un magasin de stocks est absent dans ce magasin
					// dans this, il est ajouté dans ce magasin.

					if(!stock1.contains(id1)) {
						// Dans l'ideal il faudrait insérer une "nouvelle" valeur
						// Soit utiliser un clone et un new Product()
						stock1.addValue(id1, itA.getValue(id1));
					}
					else{
						int A = stock1.getValue(id1).quantity;
						int B = stock2.getValue(id1).quantity;
						A+=B;
						// Sa quantité est incrémentée par la nouvelle quantité
						stock1.modifyValue(id1, A);
					}
					
				}
			}
		}
	}
	
	/** 
	 * EXERCICE 2
	 */
	
	public boolean exists(String countryName) {
		assert (!this.isEmpty()) : "this vide";
		
		return existsAux(countryName).nodeType() != SENTINEL;		
	}
	
	// return l'itérator sur le pays recherché
	// ou l'itérator sur le butoir où elle aurait dû se trouver
	public Iterator<Country> existsAux(String countryName) {
		Iterator<Country> it = this.iterator();
		
		while (!it.isEmpty()) {
			String currentName = it.getValue().countryName;
			if ( countryName.equals(currentName)) {
				return it;
			}
			else if (countryName.compareTo(currentName) > 0) {
				it.goRight();			
			}
			else {
				it.goLeft();
			}
		}
		return it; // butoir où aurait dû se trouver le pays recherché
	}
	
	public void printNodeOfDepth(int depth) {
		
		Iterator<Node> it = this.iterator();
		
		printNodeAux(depth, it, it.getValue());		
	}
	
	public void printNodeAux(int depth, Iterator<Node> it, Node root) {
		
		// On est sur la racine
		if( it.getValue() == root ) {
			return;
		}
			
		if(depth == 0) {
			// On est à la bonne profondeur, on peut afficher les noms
			printf( it.getValue().name);
		}
		else {
			// On commence par la droite (ordre lexicographique)
			it.goRight();
			printNodeOfDepth(depth--, it, root);
			it.goUp(); it.goLeft();
			printNodeOfDepth(depth--, it, root);
			it.goUp();
		}
		
	}

	
	public Country largestCountry(){
		Iterator<Country> it = this.iterator();
		return largestCountryAux(it,it.getValue());
	}

	private Country largestCountryAux(Iterator<Country> it,Country country){
		if(!it.isEmpty()){
			if(it.nodeType() == NodeType.LEAF || it.nodeType() == NodeType.SENTINEL){
				return country;
			}
			else{
				it.goLeft();
				Country tempCountry1 = largestCountryAux(it,it.getValue());
				it.goUp();
				it.goRight();
				Country tempCountry2 = largestCountryAux(it,it.getValue());
				it.goUp();
				
				return tempCountry1.surface >= tempCountry2.surface ? tempCountry1 : tempCountry2 ;
			}
		}
		
	}
	
	public boolean isPerfect(){
		Iterator<Country> it = this.iterator();
		isPerfectAux(it);
	}

	private boolean isPerfectAux(Iterator<Country> it){
		
		if(!it.isEmpty()){
			
			if(it.nodeType() == NodeType.SIMPLE_LEFT || it.nodeType() == NodeType.SIMPLE_RIGHT){
				// Si c'est un simple il y aura forcement un fils (qui aura des butoirs) et un butoir
				// donc pas la même distance
				return false;
			} else if (it.nodeType() == NodeType.SENTINEL){
				// Si c'est un butoir l'autre fils du pere doit être butoir
				it.goUp();
				
				if(it.nodeType() == NodeType.SIMPLE_LEFT){
					it.goRight();
				}else{
					it.goLeft();
				}
				
				return it.nodeType() == nodeType.SENTINEL;
				
			} else if (it.nodeType() == NodeType.DOUBLE){
				// Si c'est un double on peut continuer le parcours de chaque coté
				it.goLeft();
				boolean b1 = isPerfectAux(it);
				it.goUp(); it.goRight();
				boolean b2 = isPerfectAux(it);
				it.goUp();
				return b1 && b2;
			}
			
		}else {
			return false;
		}
	}
    
	public void addCountry(Country country){

		if(!this.exists(country.name)){
			Iterator<Country> it = existsAux(country.name);
			it.addValue(country);
		}

	}
	
}
