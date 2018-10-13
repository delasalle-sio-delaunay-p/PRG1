package fr.istic.prg1.tp3;

public class Pair {

	/**
	 * @author DELAUNAY Pierre, NABLI Wiem
	 * @version 1.0.2
	 * @since 2018-10-10
	 */
	
	/**
	 * Attributs
	 */
	public int x;
	public int y;

	/**
	 * Constructeurs
	 */
	public Pair() {
		this.x = 0;
		this.y = 0;
	}
	
	public Pair(int a, int b){
		this.x = a;
		this.y = b;
	}


	public Pair(Pair p){
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * @param Pair p
	 * @return boolean, vrai seulement si (x1 == x2) et (y1 == y2)
	 */
	public boolean equals(Pair p) {
		if (p == null) {
			return false;
		}
		else {
			if (this.x == p.x && this.y == p.y) {
				return true;
			} else {
				return false;
			}
		}
	}

	public String toString() {
	    return "(" + this.x + ", " + this.y + ")";
	}

	/**
	 * @param Pair p
	 * @return boolean, vrai seulement si (x1 < x2) ou (x1 == x2 et y1 < y2)
	 */
	public boolean less(Pair p) {
		if (p == null) {
			return false;
		} else {
			if (this.x < p.x || this.x == p.x && this.y < p.y){
				return true;
			} else {
				return false;
			}
		}
	}	
}