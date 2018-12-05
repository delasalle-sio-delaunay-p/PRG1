package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @pre !this.isEmpty()
	 * @return hauteur de this
	 */
	public int height() {
		Iterator<Node> it = this.iterator();
		return heightAux(it);
	}
	
	/**
	 * Methode auxiliaire pour le calcul de la hauteur
	 * @param it
	 * @return int
	 */
	private int heightAux(Iterator<Node> it) {
		NodeType nType = it.nodeType();
		
		int leftH = 0;
		int rightH = 0;
		
		if (nType == NodeType.LEAF) {
			return 0;
		}
		
		if (nType == NodeType.DOUBLE) {
			it.goLeft();
			leftH += heightAux(it);
			it.goUp(); it.goRight();
			rightH += heightAux(it);
			it.goUp();
		}

		return Math.max( (leftH + 1), (rightH + 1) );
	}
	
	/**
	 * @pre !this.isEmpty()
	 * @return nombre de noeuds de this
	 */
	public int numberOfNodes() {
		Iterator<Node> it = this.iterator();
		
		return numberOfNodesAux(it);
	}
	
	/**
	 * Methode auxiliaire pour le calcul du nombre de noeuds
	 * @param it
	 * @return int
	 */
	private int numberOfNodesAux(Iterator<Node> it) {
		int count = 0;

		NodeType ntype = it.nodeType();

		switch (ntype) {
		case LEAF:
			return 1;
		case DOUBLE:
			it.goLeft();
			count += numberOfNodesAux(it);
			it.goUp();
			it.goRight();
			count += numberOfNodesAux(it);
			it.goUp();
		}
		
		return count;
	}
	
	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		
		int depth = 0;
	    int upperX = 0, upperY = 0;
	    int width = 256;
	    int height;

	    Iterator<Node> it = this.iterator();
	    
	     while (it.nodeType() != NodeType.LEAF) {
	            height = width / 2;
	            if (depth % 2 == 0) {
	                if (y < height + upperY) {
	                    it.goLeft();
	                } else {
	                    upperY += height;
	                    it.goRight();
	                }
	            } else {
	                width = height;
	                if (x < upperX + height) {
	                    it.goLeft();
	                } else {
	                    upperX += height;
	                    it.goRight();
	                }
	            }
	            
	            depth++;
	     }

	     return it.getValue().state == 1;
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		
		it.clear();
		
		affectAux(it, image2.iterator());
	}

	/**
	 * Méthode auxiliaire pour affect
	 * @param it
	 * @param it1
	 */
	private void affectAux(Iterator<Node> it, Iterator<Node> it1) {
		
		NodeType ntype = it1.nodeType();

		switch (ntype) {
		case LEAF:
			it.addValue(it1.getValue());
			break;
		case DOUBLE:
			it.addValue(Node.valueOf(2));
			it.goLeft(); it1.goLeft();
			affectAux(it, it1);
			it.goUp(); it1.goUp();
			it.goRight(); it1.goRight();
			affectAux(it, it1);
			it.goUp(); it1.goUp();
			break;
		}
	}

	/**
	 * Surchage affectAux
	 * @param it, iterator sur this
	 * @param it2, iterator sur image2
	 * @param level, niveau/profondeur du noeud
	 */
	private void affectAux(Iterator<Node> it, Iterator<Node> it2, int level) {
		
        if (level < 14) {
            it.addValue(Node.valueOf(it2.getValue().state));
            if (it2.nodeType() != NodeType.LEAF) {
            	it.goLeft(); it2.goLeft();
   
                this.affectAux(it, it2, level++);
                it.goUp(); it2.goUp();
                level--;
                it.goRight(); it2.goRight();
                
                this.affectAux(it, it2, level++);
                it.goUp(); it2.goUp();
                level--;
            }
        } else { 
        	
        	// Après la profondeur 14 ajouter que les feuilles
            if (it2.getValue().state != 2) {           	
                it.addValue(Node.valueOf(it2.getValue().state));
            } else {
            	
                it2.goLeft();
                int leftChildState = it2.getValue().state;
                it2.goUp();
                it2.goRight();
                int rightChildSate = it2.getValue().state;
                it2.goUp();
                
                if (leftChildState != 1 && rightChildSate != 1) {
                	
                    if (leftChildState != 2 || rightChildSate != 2) {
                        it.addValue(Node.valueOf(0));
                    } else {
                        it.addValue(Node.valueOf(1));
                    }
                    
                } else {
                    it.addValue(Node.valueOf(1));
                }
            }
        }		
	
	}
	
	/***
	 * affectFromNode - copie des les noeuds de l'arbre 2 à partir de root dans this
	 * @param it, itérateur sur l'arbre à remplir
	 * @param it2, itérateur sur l'arbre à copier
	 * @param root, racine à copier
	 */
	private void affectFromNode(Iterator<Node> it, Iterator<Node> it2, Node root) {
		
		it.addValue(Node.valueOf(it2.getValue().state));
		
		if (it2.nodeType() == NodeType.DOUBLE) {
			it.goRight(); it2.goRight();
			affectFromNode(it, it2, null);
			it.goLeft(); it2.goLeft();
			affectFromNode(it, it2, null);
		}
		
		if (!it2.getValue().equals(root)) {
			it.goUp(); it2.goUp();
		}
		
	}
	
	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		
		assert (!image2.isEmpty()) : "image2 vide";
		
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
	
		it.clear();

		rotate180Aux(it, it2);
		
	}
	
	/**
	 * Méthode auxiliaire pour rotate180
	 * Swap des states entre les fils
	 * 
	 * @param it, iterator sur this
	 * @param it2, iterator sur image2 
	 */
	private void rotate180Aux(Iterator<Node> it, Iterator<Node> it2) {
	
        it.addValue( Node.valueOf(it2.getValue().state) );
        
        if (it2.nodeType() != NodeType.LEAF) {
        	
            it.goRight();
            it2.goLeft();
            rotate180Aux(it, it2);
            
            it.goUp(); it2.goUp();
            it.goLeft(); it2.goRight();
            rotate180Aux(it, it2);
            
            it.goUp(); it2.goUp();
        }
					
	}	

	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		
		assert (!image2.isEmpty()) : "image2 vide";
		
		Iterator<Node> itThis = this.iterator();
		Iterator<Node> it = image2.iterator();
	
		itThis.clear();

		rotate90Aux(it, itThis);   
	}

	/**
	 * Méthode auxiliaire pour rotate90
	 * @param it, iterator sur image2
	 * @param itThis, iterator sur this
	 */
	private void rotate90Aux(Iterator<Node> it, Iterator<Node> itThis) {
	
		/*
		 * TO DO
		 */
					
	}	
	
	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {

		assert (!this.isEmpty()) : "this vide";
		
		Iterator<Node> it = this.iterator();
	
		videoInverseAux(it);
	
	}

	/**
	 * Méthode auxiliaire pour videoInverse
	 * @param it, iterator sur this
	 */
	private void videoInverseAux(Iterator<Node> it) {
		
		if (!it.isEmpty()) {
			int st = it.getValue().state;
			
			// inversion des pixels allumes (1) et eteints (0)
			
			if (st == 0) { it.setValue(Node.valueOf(1)); }
			else if (st == 1) { it.setValue(Node.valueOf(0)); }
			
			it.goLeft();
			videoInverseAux(it);
			it.goUp();
			it.goRight();
			videoInverseAux(it);
			it.goUp();			
		}
	}
	
	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		
		assert (!image2.isEmpty()) : "image2 vide";
		
        Iterator<Node> it = this.iterator();
        Iterator<Node> it2 = image2.iterator();
        
        it.clear();
         
        mirrorVAux(it, it2, it2.getValue(), true);
	}
	
	/**
	 * Méthode auxiliaire pour mirrorV
	 * @param it, iterator sur this
	 * @param it2, iterator sur image2
	 * @param root, racine d'image2
	 * @param cutType
	 */	
    private void mirrorVAux(Iterator<Node> it, Iterator<Node> it2, Node root, boolean cutType) {
    	
		it.addValue(Node.valueOf(it2.getValue().state));

		if (it2.nodeType() == NodeType.DOUBLE) {

			// Coupe Horizontale 
			if (cutType) {

				it.goLeft();
				it2.goRight();
				mirrorVAux(it, it2, null, !cutType);

				it.goRight();
				it2.goLeft();
				mirrorVAux(it, it2, null, !cutType);

				
			} else {
				// Coupe verticale
				
				it.goLeft(); it2.goLeft();
				mirrorVAux(it, it2, null, !cutType);

				it.goRight(); it2.goRight();
				mirrorVAux(it, it2, null, !cutType);
			}

		}

		if (it2.getValue() != root) {
			it.goUp(); it2.goUp();
		}
    }
    
	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		
		assert (!image2.isEmpty()) : "image2 vide";
		
        Iterator<Node> it = this.iterator();
        Iterator<Node> it2 = image2.iterator();
        
        it.clear();
        
        mirrorHAux(it, it2, it2.getValue(), false);
        
	}

	/**
	 * Méthode auxiliaire pour mirrorH
	 * @param it, iterator sur this
	 * @param it2, iterator sur image2
	 * @param root, racine d'image2
	 * @param cutType
	 */
    private void mirrorHAux(Iterator<Node> it, Iterator<Node> it2, Node root, boolean cutType) {
    	
		it.addValue(Node.valueOf(it2.getValue().state));

		if (it2.nodeType() == NodeType.DOUBLE) {

			// Coupe verticale : copier le fils droit du noeud courant de it2
			// comme fils gauche du noeud courant de it
			
			if (cutType) {

				it.goLeft();
				it2.goRight();
				mirrorHAux(it, it2, null, !cutType);

				it.goRight();
				it2.goLeft();
				mirrorHAux(it, it2, null, !cutType);

				
			} else {
				// Coupe horizontale
				
				it.goLeft(); it2.goLeft();
				mirrorHAux(it, it2, null, !cutType);

				it.goRight(); it2.goRight();
				mirrorHAux(it, it2, null, !cutType);
			}

		}

		if (it2.getValue() != root) {
			it.goUp(); it2.goUp();
		}
    }
	
	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		
        Iterator<Node> itThis = this.iterator();
        Iterator<Node> it2 = image2.iterator();
        
        itThis.clear();
        
        // Image pleine
        if (it2.nodeType() == NodeType.LEAF) { 
        	this.affect(image2); 
        }
        
		it2.goLeft();
		
		// Image avec moitié supérieure pleine
		if (it2.nodeType() == NodeType.LEAF) {
			itThis.addValue(Node.valueOf( it2.getValue().state));
		}
		else {
			
			// Cas général
			it2.goLeft();
			affectFromNode(itThis, it2, it2.getValue());
			
		}
		
	}
	


	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		
		/**
		 * Work in progress
		 */
		
		/*
		Iterator<Node> itThis = this.iterator();
	    Iterator<Node> it2 = image2.iterator();
	        
	    itThis.clear();
	        
	    itThis.addValue(Node.valueOf(2));
	    itThis.goLeft();
	    itThis.addValue(Node.valueOf(0)); // moitié inférieure éteinte
	        
	    itThis.goRoot();
	    itThis.goLeft();
	    itThis.addValue(Node.valueOf(2));
	    itThis.goRight();
	    itThis.addValue(Node.valueOf(0)); // coin supérieur droit éteint
	        
	    itThis.goUp();
	    itThis.goLeft();      
	        
	    this.affectFromNode(itThis, it2, it2.getValue());
	    
	    */
   
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		
		assert (!(image1.isEmpty() || image2.isEmpty())) : "Les deux images sont vides";
		assert (image1 != this) : "image1 == this";
		assert (image2 != this) : "image2 == this";
		
		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image1.iterator();
		Iterator<Node> it3 = image2.iterator();
		
		it1.clear();
		
		intersectionAux(it1, it2, it3);
		
	}

	
	private void intersectionAux(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> it3) {
		
        if (it2.getValue().state == 0 || it3.getValue().state == 0) {
        	
            it1.addValue(Node.valueOf(0));
            
        } else if (it2.getValue().state == it3.getValue().state) {
        	
            switch (it2.getValue().state) {
                case 1:
                    it1.addValue(Node.valueOf(1));
                    break;

                case 2:
                    it1.addValue(Node.valueOf(2));
                    it1.goLeft(); it2.goLeft(); it3.goLeft();
                    
                    this.intersectionAux(it1, it2, it3);
                    
                    int state1 = it1.getValue().state;
                    it1.goUp(); it2.goUp(); it3.goUp();
                    it1.goRight(); it2.goRight(); it3.goRight();
                    
                    this.intersectionAux(it1, it2, it3);
                    
                    int state2 = it1.getValue().state;
                    it1.goUp(); it2.goUp(); it3.goUp();

                    if (state1 == state2 && state2 != 2) {
                        it1.clear();
                        it1.addValue(Node.valueOf(state1));
                    }
                    break;
            }
            
        } else {
        	
            if (it2.getValue().state == 2) {
                this.affectAux(it1, it2);
            } else {
                this.affectAux(it1, it3);
            }
            
        }
        
	} 	
	
	
	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		Iterator<Node> itThis = this.iterator();

		itThis.clear();
		
		unionAux(it1, it2, itThis);
	}
	
	/**
	 * Méthode auxiliaire pour union
	 * @param it1
	 * @param it2
	 * @param itThis
	 */
	private void unionAux(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> itThis) {
		
		int st = it1.getValue().state + it2.getValue().state;
		
		if (st == 4) {
			// les 2 states sont à 2
			itThis.addValue(Node.valueOf(2));
			it1.goLeft(); it2.goLeft(); itThis.goLeft();
			unionAux(it1, it2, itThis);
			int stLeft = itThis.getValue().state;
			it1.goUp(); it2.goUp(); itThis.goUp();
			it1.goRight(); it2.goRight(); itThis.goRight();
			unionAux(it1, it2, itThis);
			int stRight = itThis.getValue().state;
			it1.goUp(); it2.goUp(); itThis.goUp();
			
            if (stLeft == stRight && stLeft != 2) {         
                itThis.clear();
                itThis.addValue(Node.valueOf(stLeft));
            }			
	
		} else if (st == 3) {
			// un des 2 states vaut 1 : 2,1 || 1,2
			itThis.addValue(Node.valueOf(1));
		}
		else if (st == 2 ) {
			// 3 cas possibles : 2,0 || 0,2 || 1,1
            if (it1.getValue().state == 0) {
                affectAux(itThis, it2);
            } else if (it1.getValue().state == 2) {
                affectAux(itThis, it1);
            } else {
                itThis.addValue(Node.valueOf(1));
            }	
		}
		else {
			// un des 2 states vaut 1 ou les 2 sont à 0
			itThis.addValue(Node.valueOf(st));
		}
						
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		
		boolean rtn = false;
		Iterator<Node> itThis = iterator();
			
		rtn = testDiagonalAux(itThis);	
		
	    return rtn;
	}

	/***
	 * Méthode auxiliaire testDiagonal
	 * @param itThis
	 * @return boolean
	 */
	private boolean testDiagonalAux(Iterator<Node> it) {
		
		if (it.nodeType() == NodeType.LEAF) {
			return it.getValue().state == 1;
		}
			

		boolean rtn;

		// Test de la moitié supérieure
		it.goLeft();

		// Si la section est pleine
		if (it.nodeType() == NodeType.LEAF) {
			rtn = it.getValue().state == 1;
			it.goUp();

			// Test du quart supérieur gauche sinon
		} else {

			it.goLeft();
			rtn = testDiagonalAux(it);
			it.goUp();
			it.goUp();

		}

		// Test de la moitié inférieure
		it.goRight();

		// Si la section est pleine
		if (it.nodeType() == NodeType.LEAF) {
			rtn = rtn && it.getValue().state == 1;
			it.goUp();
		}

		// Tester le quart inférieur droit sinon
		else {

			it.goRight();
			rtn = rtn && testDiagonalAux(it);
			it.goUp();
			it.goUp();

		}

		return rtn;
	}
	
	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		
		Iterator<Node> it = this.iterator();
	    int rank = 0;
	    int height;
	    int x0 = 0, y0 = 0;
	    int width = 256; 
	    
	    while (it.nodeType() != NodeType.LEAF) {
	    	height = width / 2;
	    	
	        if (rank % 2 == 0) {
	        	if ((y1 < y0 + height) && (y2 < y0 + height)) {
	        		it.goLeft();
	             } else if ((y1 >= y0 + height) && (y2 >= y0 + height)) {
	            	 y0 += height;
	                 it.goRight();
	            } else {
	            	return false;
	            }
	        	
	        } else {
	        	width = height;
	        	
	            if ((x1 < x0 + width) && (x2 < x0 + width)) {
	            	it.goLeft();
	            } else if ((x1 >= x0 + width) && (x2 >= x0 + width)) {
	            	x0 += width;
	                it.goRight();
	            } else {
	            	return false;
	            }
	        }
	        
	        rank++;
	    }
	    
	    return true;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		
		Iterator<Node> itThis = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		
		return this == image2 || isIncludedInAux(itThis, it2, it2.getValue());
	}

	private boolean isIncludedInAux(Iterator<Node> itThis, Iterator<Node> it2, Node root) {
		boolean rtn = false, keepGoing = true;
		
		if (itThis.nodeType() == NodeType.LEAF) {
			rtn = itThis.getValue().state == 0;
			keepGoing = false;
		}
		
		if (it2.nodeType() == NodeType.LEAF) {
			rtn = rtn || it2.getValue().state == 1;
			keepGoing = false;
		}
		
		if (keepGoing) {
			itThis.goRight(); it2.goRight();
			rtn = isIncludedInAux(itThis, it2, null);
			itThis.goLeft(); it2.goRight();
			rtn = rtn && isIncludedInAux(itThis, it2, null);
		}
		
		if (!it2.getValue().equals(root)) {
			itThis.goUp(); it2.goUp();
		}
		
		return rtn;
		
	}
	
    /**
     * 
     * @param it
     */	
    private void verifyNodes(Iterator<Node> it) {
    	
        it.goLeft();
        int leftChildState = it.getValue().state;
        it.goUp();
        it.goRight();
        int rightChildState = it.getValue().state;
        it.goUp();

        if (leftChildState == 2) {
        	
            it.goLeft();
            verifyNodes(it);
            leftChildState = it.getValue().state;
            it.goUp();
            
        }

        if (rightChildState == 2) {
        	
            it.goRight();
            verifyNodes(it);
            rightChildState = it.getValue().state;
            it.goUp();
            
        }

        if (leftChildState == rightChildState && leftChildState != 2) {
        	
            it.clear();
            it.addValue(Node.valueOf(leftChildState));
            
        }
        
    }
}
