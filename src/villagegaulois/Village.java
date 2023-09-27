package villagegaulois;

import personnages.Chef; 
import personnages.Gaulois;


public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	private static class Marche{
		private Etal[] etals; 
		
		
		private Marche (int nbEtalsMarche) {
			etals = new Etal[nbEtalsMarche];
		}
		
		private void utiliserEtalLibre(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
					etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i =0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					return i;
				}				
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit; 
			int iEtalsProduit =0;
			int nbEtalsProduit = 0;
			for (int i=0; i<etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsProduit ++;					
				}
			}
			etalsProduit = new Etal[nbEtalsProduit];
			for(int j=0; j<etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					etalsProduit[iEtalsProduit]=etals[j]; 
					iEtalsProduit ++;
				}
			}
			return etalsProduit; 
					
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i<etals.length;i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}	
			}
			return null;
		}
		
		private String afficherMarche() {
			int i=0;
			int nbEtalVide = 0;
			while (etals[i].isEtalOccupe()) {
				etals[i].afficherEtal(); 
				i ++;
			}
			nbEtalVide = etals.length-i; 
			return "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n"; 								
		}
		
	}
	
}