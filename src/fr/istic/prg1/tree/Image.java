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
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		Iterator<Node> it = this.iterator();
		int tmpX = 256, tmpY = 256;

		while (!it.isEmpty()) {

			if (tmpX == tmpY) {
				if (x < (tmpX/2))
					it.goLeft();  

				else
					it.goRight(); 

				tmpX = tmpX/2;
			}
			else {
				if (y < (tmpY/2))
					it.goLeft();  

				else
					it.goRight();  

				tmpY = tmpY/2;
			}
		}

		it.goUp();
		return (it.getValue().equals(Node.valueOf(1)));
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
		
		NodeType otype = it1.nodeType();

		switch (otype) {
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
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it = image2.iterator();
		Iterator<Node> itThis = this.iterator();

		itThis.clear();

		if (!it.isEmpty()) {
			rotateAux(it, itThis);
		}
	}
	
	/**
	 * Méthode auxiliaire
	 * @param it
	 * @param itThis
	 */
	private void rotateAux (Iterator<Node> it, Iterator<Node> itThis) {
	
		if (!it.isEmpty()) {
			
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
		// to do    
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
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
		// to do
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
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		Iterator<Node> it3 = this.iterator();

		it3.clear();

		if (!it1.isEmpty() && !it2.isEmpty()){
			intersectionAux(it1, it2, it3);
		}
	}

	
	private void intersectionAux(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> itThis) {
		if (!it1.isEmpty() && !it2.isEmpty()){
			Node nd1 = it1.getValue();
			Node nd2 = it2.getValue();
			
			
			
			
			
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	    return false;
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
		return false;
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
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction � �crire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	    return false;
	}

}
