package fr.istic.prg1.tree;

public class Image {

	
	public void affect(Image image2) {
		Iterator<Node> it = this.iterator();
		
		this.clear();
		
		affectAux(it, image2.iterator());
	}
	
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
	
	public void intersection(Image image1, Image image2) {

		Iterator<Node> it1 = image1.iterator();
		Iterator<Nodee> it2 = image2.iterator();
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

			if (nd1.equals(Node.valueOf(0)) || nd2.equals(Node.valueOf(0))) {
				itThis.addValue(Node.valueOf(0));
			}
			else if (nd1.equals(Node.valueOf(1)) && nd2.equals(Node.valueOf(1))) {
				itThis.addValue(Node.valueOf(1));
			}
			else if (nd1.equals(Node.valueOf(2)) && nd2.equals(Node.valueOf(2))) {
				itThis.addValue(Node.valueOf(2));
			}
			else {
				itThis.addValue(Node.valueOf(2));
				if (nd1.equals(Node.valueOf(1))) {
					it1.goLeft();
					it1.addValue(Node.valueOf(1));
					it1.goUp();
					it1.goRight();
					it1.addValue(Node.valueOf(1));
					it1.goUp();
				} else {
					it2.goLeft();
					it2.addValue(Node.valueOf(1));
					it2.goUp();
					it2.goRight();
					it2.addValue(Node.valueOf(1));
					it2.goUp();
				}
			}

			it1.goLeft(); it2.goLeft(); itThis.goLeft();
			intersectionAux(it1, it2, itThis);
			it1.goUp(); it2.goUp(); itThis.goUp();
			it1.goRight(); it2.goRight(); itThis.goRight();
			intersectionAux(it1, it2, itThis);
			it1.goUp(); it2.goUp(); itThis.goUp();

		}
	}	
	
	
	
}
