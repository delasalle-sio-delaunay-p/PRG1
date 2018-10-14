package fr.istic.prg1.tp4;

public class SubSet {

	public final int rank;
	public SmallSet set;



	public SubSet() {
		rank = 0;
		set = new SmallSet();
	}



	public SubSet(int r, SmallSet f) {
		rank = r;
		set = f;
	}
}
