package fr.istic.prg1.list;

public class SmallSet {

	private boolean[] tab = new boolean[256];


	public SmallSet() {

		for (int i = 0; i < 256; i++) {
			tab[i] = false;
		}
	}

	public SmallSet(boolean[] t) {

		for (int i = 0; i < 256; i++) {
			tab[i] = t[i];
		}
	}


	public int size() {
		int count = 0;

		for (int i = 0; i < 256; i++) {
			if (tab[i])
				count++;
		}

		return count;
	}


	public boolean contains(int x) {
		if ((x >= 0) && (x < 256))
			return tab[x];

		else
			return false;
	}


	public boolean isEmpty() {
		for (int i = 0; i < 256; i++) {
			if (tab[i])
				return false;
		}

		return true;
	}


	public void add(int x) {
		if ((x >= 0) && (x < 256))
			tab[x] = true;
	}

	public void remove(int x) {
		if ((x >= 0) && (x < 256))
			tab[x] = false;
	}


	public void addInterval(int deb, int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb < fin)) {
			for (int i = deb; i <= fin; i++) {
				tab[i] = true;
			}
		}
	}


	public void removeInterval(int deb, int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb < fin)) {
			for (int i = deb; i <= fin; i++) {
				tab[i] = false;
			}
		}
	}


	public void union(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if (f.contains(i))
				tab[i] = true;
		}
	}


	public void intersection(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			tab[i] = ((f.contains(i)) && (tab[i])) ? true : false;
		}
	}


	public void difference(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if ((f.contains(i)) && (tab[i]))
				tab[i] = false;
		}
	}


	public void symetricDifference(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			tab[i] = ((f.contains(i)) && (tab[i])) ? false : true;
		}
	}


	public void complement() {
		for (int i = 0; i < 256; i++) {
			tab[i] = (tab[i]) ? false : true;
		}
	}


	public void clear() {
		for (int i = 0; i < 256; i++) {
			tab[i] = false;
		}
	}


	public boolean isIncludedIn(SmallSet f) {
		for (int i = 0; i < 256; i++) {
			if ((tab[i]) && (!f.contains(i)))
				return false;
		}

		return true;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		else if (!(o instanceof SmallSet)) {
			return false;
		}

		else {
			SmallSet ss = (SmallSet)o;
			for (int i = 0; i < 256; i++) {
				if (tab[i] != ss.contains(i))
					return false;
			}

			return true;
		}
	}


	public SmallSet copy() {
		return new SmallSet(tab);
	}


	@Override
	public String toString() {
		String ret = "El�ments pr�sents: ";
		for (int i = 0; i < 256; i++) {
			ret += i + " ";
		}
		return ret;
	}

}