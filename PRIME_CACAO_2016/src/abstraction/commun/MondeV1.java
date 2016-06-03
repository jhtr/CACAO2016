
package abstraction.commun;

import abstraction.fourni.Monde;

import java.util.ArrayList;

import abstraction.commun.Constantes;
import abstraction.equipe5.Lindt;
import abstraction.equipe1.Producteur;
import abstraction.equipe1.ProducteurTest;
import abstraction.equipe1.TransformateurTest;
import abstraction.equipe3.Leclercv2;
import abstraction.equipe2.*;
import abstraction.equipe6.Carrefour;

public class MondeV1 extends Monde {
	
	private static ArrayList<Produit> produits = new ArrayList<Produit>();
	
	public void peupler() {
		// Transformateurs
		TransformateurTest nestle = new TransformateurTest("Nestle (test)", 1000000, 2500);
		this.ajouterActeur(nestle);
		
		TransformateurTest lindt = new TransformateurTest("Lindt (test)", 500000, 10000);
		this.ajouterActeur(lindt);

		/*
		ResteDesTransformateursMondiaux t3 = new ResteDesTransformateursMondiaux();
		this.ajouterActeur(t3);
		*/
		
		// Marché Producteur
		MarcheProducteur marcheProducteur = new MarcheProducteur();
		MarcheProducteur.LE_MARCHE = marcheProducteur;
		this.ajouterActeur(marcheProducteur);
		
		// Producteurs
		ProducteurTest p1 = new ProducteurTest("Afrique de l'Ouest (test)", 3000);
		this.ajouterActeur(p1);
		
		ProducteurTest p2 = new ProducteurTest("Asie et Amerique (test)", 1000);
		this.ajouterActeur(p2);
		
		// Liens entre les acteurs
		/*
		t3.ajouterTransformateur(nestle);
		t3.ajouterTransformateur(lindt);
		*/
		
		p1.ajouterTransformateur(nestle);
		p1.ajouterTransformateur(lindt);
		//p1.ajouterTransformateur(t3);
		
		p2.ajouterTransformateur(nestle);
		p2.ajouterTransformateur(lindt);
		//p2.ajouterTransformateur(t3);
		
		// Integration des acteurs au marche
		marcheProducteur.ajouterProducteur(p1);
		marcheProducteur.ajouterProducteur(p2);
		marcheProducteur.ajouterTransformateur(nestle);
		marcheProducteur.ajouterTransformateur(lindt);
	}
}
