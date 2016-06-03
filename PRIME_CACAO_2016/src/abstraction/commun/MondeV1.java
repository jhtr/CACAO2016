
package abstraction.commun;

import abstraction.fourni.Monde;

import java.util.ArrayList;
import java.util.HashMap;

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
		TransformateurTest t1 = new TransformateurTest("Nestle (test)", 500000, 3000);
		this.ajouterActeur(t1);
		
		TransformateurTest t2 = new TransformateurTest("Lindt (test)", 500000, 2000);
		this.ajouterActeur(t2);

		/*
		ResteDesTransformateursMondiaux t3 = new ResteDesTransformateursMondiaux();
		this.ajouterActeur(t3);
		*/
		
		// Marché Producteur
		MarcheProducteur marcheProducteur = new MarcheProducteur();
		MarcheProducteur.LE_MARCHE = marcheProducteur;
		this.ajouterActeur(marcheProducteur);
		
		// Producteurs
		HashMap<ITransformateur,Double> prefP1 = new HashMap<ITransformateur,Double>();
		prefP1.put(t1, 1.0);
		prefP1.put(t2, 0.0);
		
		ProducteurTest p1 = new ProducteurTest("Afrique de l'Ouest (test)", 1000, prefP1, 2000);
		this.ajouterActeur(p1);
		
		HashMap<ITransformateur,Double> prefP2 = new HashMap<ITransformateur,Double>();
		prefP2.put(t1, 0.0);
		prefP2.put(t2, 1.0);
		
		ProducteurTest p2 = new ProducteurTest("Asie et Amerique (test)", 500, prefP2, 500);
		this.ajouterActeur(p2);
		
		// Liens entre les acteurs
		/*
		t3.ajouterTransformateur(nestle);
		t3.ajouterTransformateur(lindt);
		*/
		
		p1.ajouterTransformateur(t1);
		p1.ajouterTransformateur(t2);
		//p1.ajouterTransformateur(t3);
		
		p2.ajouterTransformateur(t1);
		p2.ajouterTransformateur(t2);
		//p2.ajouterTransformateur(t3);
		
		// Integration des acteurs au marche
		marcheProducteur.ajouterProducteur(p1);
		marcheProducteur.ajouterProducteur(p2);
		marcheProducteur.ajouterTransformateur(t1);
		marcheProducteur.ajouterTransformateur(t2);
	}
}
