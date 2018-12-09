package fr.istic.prg1.list;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import fr.istic.prg1.list_util.Comparison;
import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.List;
import fr.istic.prg1.list_util.SmallSet;

/**
 * @author Micka�l Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2018-10-02
 */

public class MySet extends List<SubSet> {

	/**
	 * Borne sup�rieure pour les rangs des sous-ensembles.
	 */
	private static final int MAX_RANG = 128;
	/**
	 * Sous-ensemble de rang maximal � mettre dans le drapeau de la liste.
	 */
	private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG,
			new SmallSet());
	/**
	 * Entr�e standard.
	 */
	private static final Scanner standardInput = new Scanner(System.in);

	public MySet() {
		super();
		setFlag(FLAG_VALUE);
	}

	/**
	 * Fermer tout (actuellement juste l'entr�e standard).
	 */
	public static void closeAll() {
		standardInput.close();
	}

	private static Comparison compare(int a, int b) {
		if (a < b) {
			return Comparison.INF;
		} else if (a == b) {
			return Comparison.EGAL;
		} else {
			return Comparison.SUP;
		}
	}

	/**
	 * Afficher � l'�cran les entiers appartenant � this, dix entiers par ligne
	 * d'�cran.
	 */
	public void print() {
		System.out.println(" [version corrigee de contenu]");
		this.print(System.out);
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////// Appartenance, Ajout, Suppression, Cardinal
	// ////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Ajouter � this toutes les valeurs saisies par l'utilisateur et afficher
	 * le nouveau contenu (arr�t par lecture de -1).
	 */
	public void add() {
		System.out.println(" valeurs a ajouter (-1 pour finir) : ");
		this.add(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Ajouter � this toutes les valeurs prises dans is.
	 * C'est une fonction auxiliaire pour add() et restore().
	 * 
	 * @param is
	 *            flux d'entr�e.
	 */	
	public void add(InputStream is) {
		
        Scanner sc = new Scanner(is);
        int value = readValue(sc, -1);
        
        while (value != -1) {
            this.addNumber(value);
            value = readValue(sc, -1);
        }	
	}
	
	/**
	 * 
	 * @param rank
	 * @return it, iterator
	 */
	private Iterator<SubSet> searchRank(int rank) {
		
        Iterator<SubSet> it = iterator();
        
        while (!it.isOnFlag() && rank > it.getValue().rank) {
        	it.goForward();
        }		
        
        return it;
	}
	
	/**
	 * Ajouter value � this.
	 * 
	 * @param value
	 *            valuer � ajouter.
	 */
	public void addNumber(int value) {
		
		int rank = value / 256;
		Iterator<SubSet> it = this.searchRank(rank);
           
        if (rank == it.getValue().rank) {
            it.getValue().set.add(value % 256);
        } else {
            SubSet newSet = new SubSet(rank, new SmallSet());
            newSet.set.add(value % 256);
            it.addLeft(newSet);
        }	
		
	}
	
	
	/**
	 * Supprimer de this toutes les valeurs saisies par l'utilisateur et
	 * afficher le nouveau contenu (arr�t par lecture de -1).
	 */
	public void remove() {
		System.out.println("  valeurs a supprimer (-1 pour finir) : ");
		this.remove(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Supprimer de this toutes les valeurs prises dans is.
	 * 
	 * @param is
	 *            flux d'entr�e
	 */
	public void remove(InputStream is) {
		
        Scanner sc = new Scanner(is);
        int value = readValue(sc, -1);
        
        while (value != -1) {
            this.removeNumber(value);
            value = readValue(sc, -1);
        }
	}

	/**
	 * Supprimer value de this.
	 * 
	 * @param value
	 *            valeur � supprimer
	 */
	public void removeNumber(int value) {
		
        int rank = value / 256;
        Iterator<SubSet> it = this.searchRank(rank);
            
        SubSet cur = it.getValue();
        if (rank == cur.rank) {
            cur.set.remove(value % 256);
            if (cur.set.isEmpty())
                it.remove();
        }	
		
	}

	/**
	 * @return taille de l'ensemble this
	 */
	public int size() {
		
        Iterator<SubSet> it = this.iterator();
        int size = 0;
        
        while (!it.isOnFlag()) {
            size = size + it.getValue().set.size();
            it.goForward();
        }
        
        return size;
	}


	/**
	 * @return true si le nombre saisi par l'utilisateur appartient � this,
	 *         false sinon
	 */
	public boolean contains() {
		System.out.println(" valeur cherchee : ");
		int value = readValue(standardInput, 0);
		return this.contains(value);
	}

	/**
	 * @param value
	 *            valeur � tester
	 * @return true si valeur appartient � l'ensemble, false sinon
	 */

	public boolean contains(int value) {
		
        int rank = value / 256;
        Iterator<SubSet> it = this.searchRank(rank);

		if (it.getValue().rank == rank) {
			return it.getValue().set.contains(value%256);
		}
		
		return false;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////// Difference, DifferenceSymetrique, Intersection, Union ///////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * This devient la diff�rence de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void difference(MySet set2) {
		
		// Dans le cas où set2 est identique à this, on clear this
        if (set2 == this) {
            clear();
            return;
        }
        
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		// stop quand set2 est parcouru en entier
		while (!it2.isOnFlag()) {
			
			SubSet cur = it1.getValue();
			SubSet cur2 = it2.getValue();
				
			if (cur.rank == cur2.rank) {
				
				cur.set.difference(cur2.set);
				
				// si le subset de this est vide après la diffèrence on le supprime sinon on avance
                if (cur.set.isEmpty()) {
                	it1.remove();
                }
                else {
                	it1.goForward();
                }
				
			}
			
            if (cur.rank < cur2.rank) {
                it1.goForward();
            }
            else {
                it2.goForward();
            }

		}
		
	}

	/**
	 * This devient la diff�rence sym�trique de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void symmetricDifference(MySet set2) {

		// Dans le cas où set2 est identique à this, on clear this
        if (set2 == this) {
            clear();
            return;
        }
        
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		while (!it1.isOnFlag() || !it2.isOnFlag()) {
			
			SubSet cur = it1.getValue();
			SubSet cur2 = it2.getValue();
				
			if (cur.rank == cur2.rank) {
				
				cur.set.symmetricDifference(cur2.set);
				
				// si le subset de this est vide après la diffèrence on le supprime sinon on avance
                if (cur.set.isEmpty()) {
                	it1.remove();
                }
                else {
                	it1.goForward();
                }
				
			}
			
            if (cur.rank > cur2.rank) {
                it1.addLeft(cur2.clone());
            }
            
            if (cur.rank < cur2.rank) {
            	it1.goForward();
            }
            else {
            	it2.goForward();
            }
		}
	}

	/**
	 * This devient l'intersection de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void intersection(MySet set2) {
		
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		while (!it1.isOnFlag()) {
			
			SubSet cur = it1.getValue();
			SubSet cur2 = it2.getValue();
				
			if (cur.rank == cur2.rank) {
				
				cur.set.intersection(cur2.set);
				
				// après intersection supprimer le set vide
				if (cur.set.isEmpty()) {
					it1.remove();
				}
				else {
					it1.goForward();
				}
				
			}
			
			// supprimer les rangs de this qui ne sont pas dans set2
			if (cur.rank < cur2.rank) {
				it1.remove();
			}
			else {
				it2.goForward();
			}

		}
	}

	/**
	 * This devient l'union de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void union(MySet set2) {
		
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		while (!it2.isOnFlag()) {
			
			if (it1.getValue().rank == it2.getValue().rank) {
				it1.getValue().set.union(it2.getValue().set);
				it1.goForward();
				it2.goForward();
			}
			// set2 a des ranks en + donc on les clone dans this
			else if (it2.getValue().rank < it1.getValue().rank) {
				it1.addLeft(it2.getValue().clone());
				it2.goForward();
			}
			else {
				it1.goForward();
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////// Egalit�, Inclusion ////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * @param o
	 *            deuxi�me ensemble
	 * 
	 * @return true si les ensembles this et o sont �gaux, false sinon
	 */
	@Override
	public boolean equals(Object o) {
		boolean b = true;
		if (this == o) {
			b = true;
		} else if (o == null) {
			b = false;
		} else if (!(o instanceof MySet)) {
			b = false;
		} else {
			
			MySet other = (MySet) o;
			Iterator<SubSet> itO = other.iterator();
			Iterator<SubSet> it = this.iterator();
			
			while (!it.isOnFlag() && !itO.isOnFlag()) {
				
                if (it.getValue().rank != itO.getValue().rank) {
                	b = false;
                }
                    
                if (!it.getValue().set.equals(itO.getValue().set)) {
                	b = false;
                }
                    
                it.goForward();
                itO.goForward();				
			}
			
            if (!it.isOnFlag() || !itO.isOnFlag()) {
            	b = false;
            }
		}
		return b;
	}

	/**
	 * @param set2
	 *            deuxi�me ensemble
	 * @return true si this est inclus dans set2, false sinon
	 */
	public boolean isIncludedIn(MySet set2) {
		
        Iterator<SubSet> it1 = iterator();
        Iterator<SubSet> it2 = set2.iterator();
        
        while (!it1.isOnFlag() && !it2.isOnFlag()) {
        			
            while (it1.getValue().rank > it2.getValue().rank) {
                it2.goForward();
            }
            
            if (it1.getValue().rank < it2.getValue().rank) {
                return false;
            }
            
            if (!it1.getValue().set.isIncludedIn(it2.getValue().set)) {
                return false;
            }
            
            it1.goForward();         
        }
        
		return true;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// //////// Rangs, Restauration, Sauvegarde, Affichage //////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * Afficher les rangs pr�sents dans this.
	 */
	public void printRanks() {
		System.out.println(" [version corrigee de rangs]");
		this.printRanksAux();
	}

	private void printRanksAux() {
		int count = 0;
		System.out.println(" Rangs presents :");
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			System.out.print("" + it.getValue().rank + "  ");
			count = count + 1;
			if (count == 10) {
				System.out.println();
				count = 0;
			}
			it.goForward();
		}
		if (count > 0) {
			System.out.println();
		}
	}

	/**
	 * Cr�er this � partir d'un fichier choisi par l'utilisateur contenant une
	 * s�quence d'entiers positifs termin�e par -1 (cf f0.ens, f1.ens, f2.ens,
	 * f3.ens et f4.ens).
	 */
	public void restore() {
		String fileName = readFileName();
		InputStream inFile;
		try {
			inFile = new FileInputStream(fileName);
			System.out.println(" [version corrigee de restauration]");
			this.clear();
			this.add(inFile);
			inFile.close();
			System.out.println(" nouveau contenu :");
			this.printNewState();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("fichier " + fileName + " inexistant");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier " + fileName);
		}
	}

	/**
	 * Sauvegarder this dans un fichier d'entiers positifs termin� par -1.
	 */
	public void save() {
		System.out.println(" [version corrigee de sauvegarde]");
		OutputStream outFile;
		try {
			outFile = new FileOutputStream(readFileName());
			this.print(outFile);
			outFile.write("-1\n".getBytes());
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("pb ouverture fichier lors de la sauvegarde");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier");
		}
	}

	/**
	 * @return l'ensemble this sous forme de cha�ne de caract�res.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int count = 0;
		SubSet subSet;
		int startValue;
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			subSet = it.getValue();
			startValue = subSet.rank * 256;
			for (int i = 0; i < 256; ++i) {
				if (subSet.set.contains(i)) {
					String number = String.valueOf(startValue + i);
					int numberLength = number.length();
					for (int j = 6; j > numberLength; --j) {
						number += " ";
					}
					result.append(number);
					++count;
					if (count == 10) {
						result.append("\n");
						count = 0;
					}
				}
			}
			it.goForward();
		}
		if (count > 0) {
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * Imprimer this dans outFile.
	 * 
	 * @param outFile
	 *            flux de sortie
	 */
	private void print(OutputStream outFile) {
		try {
			String string = this.toString();
			outFile.write(string.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Afficher l'ensemble avec sa taille et les rangs pr�sents.
	 */
	private void printNewState() {
		this.print(System.out);
		System.out.println(" Nombre d'elements : " + this.size());
		this.printRanksAux();
	}

	/**
	 * @param scanner
	 * @param min
	 *            valeur minimale possible
	 * @return l'entier lu au clavier (doit être entre min et 32767)
	 */
	private static int readValue(Scanner scanner, int min) {
		int value = scanner.nextInt();
		while (value < min || value > 32767) {
			System.out.println("valeur incorrecte");
			value = scanner.nextInt();
		}
		return value;
	}

	/**
	 * @return nom de fichier saisi psar l'utilisateur
	 */
	private static String readFileName() {
		System.out.print(" nom du fichier : ");
		String fileName = standardInput.next();
		return fileName;
	}
}
