package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		etal.libererEtal();
		
		
		Gaulois gaulois = new Gaulois("gaulois", 10);
		etal.occuperEtal(gaulois, "Potion", 200);
		etal.acheterProduit(100, null);
		
		
		try {
			etal.acheterProduit(-100, gaulois);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		etal.libererEtal();
		etal.occuperEtal(null, "Potion", 100);
		
		try {
			etal.acheterProduit(100, gaulois);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");
		}
}