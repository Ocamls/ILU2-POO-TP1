package villagegaulois;

import villagegaulois.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private static Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			
			return -1;
			
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtal = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtal += 1;
				}
			}
			
			Etal[] etalProduit = new Etal[nbEtal];
			int nbEtalPlace = 0;
			
			for (Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					etalProduit[nbEtalPlace] = etal;
					nbEtalPlace ++;
				}

			}
			
			return etalProduit;
			
		}
		
		public Etal trouverVendeur(Gaulois gaulois){
			for (Etal etal : etals) {
				if(etal.isEtalOccupe() && etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalLibre = 0;
			String dialogue = "";
			
			for(Etal etal: etals) {
				if(etal.isEtalOccupe()) {
					dialogue += etal.afficherEtal();
				} else {
					nbEtalLibre ++;
				}
			}
			dialogue += "Il reste " + nbEtalLibre +  " étals non utilisés dans le marché. \n";
			return dialogue;
		}
		
	}
		
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		String dialogue = "Le vendeur "+ vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".";
		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre != -1) {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			dialogue += "\nLe vendeur " + vendeur.getNom() + " vend des " + produit + " sur l'�tal n�" + etalLibre + ".";
		}
		return dialogue + "\n";
	}
	
	public String rechercherVendeursProduit(String produit) {
		String dialogue = "Les vendeurs qui proposent des " + produit + " sont :";
		Etal[] etals = marche.trouverEtals(produit);
		
		for(Etal etal: etals) {
			dialogue += "\n- " + etal.getVendeur().getNom();
		}
		
		return dialogue + "\n";
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = marche.trouverVendeur(vendeur);
		String dialogue = etalVendeur.libererEtal();
		return dialogue;
	}
	
	public String afficherMarche() {
		String dialogue = "Le marché du village \"" + this.nom + "\" possède plusieurs étals : \n";
		return dialogue + marche.afficherMarche();
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

	public String afficherVillageois() throws VillageSansChefException {
		
		if (this.chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef");
		}
		
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
}