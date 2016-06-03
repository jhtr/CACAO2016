package abstraction.equipe1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class ProducteurTest implements Acteur, IProducteur {
	private String nom;
	private Indicateur tresorerie;
	private Indicateur stockCacao;
	private Map<ITransformateur,Double> quantitesProposees;
	
	private Map<ITransformateur,Double> preference;
	
	public ProducteurTest(String nom, double stock, Map<ITransformateur,Double> preference) {
		this.nom = nom;
		
		this.tresorerie = new Indicateur("Tresorerie de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		this.tresorerie.setValeur(this, 0);
		
		this.stockCacao = new Indicateur("Stock de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
		this.stockCacao.setValeur(this, stock);
		
		this.quantitesProposees = new HashMap<ITransformateur,Double>();
		this.preference = preference;
	}
	
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return this.stockCacao.getValeur()/this.quantitesProposees.size();
	}
	
	public void notificationVente(CommandeProduc c) {
		this.stockCacao.setValeur(this, this.stockCacao.getValeur()-c.getQuantite());
		this.tresorerie.setValeur(this, this.tresorerie.getValeur()+c.getQuantite()*c.getPrixTonne());
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void next() {
		//Random random = new Random();
		this.stockCacao.setValeur(this, this.stockCacao.getValeur()+1000);
	}
	
	public void ajouterTransformateur(ITransformateur t) {
		this.quantitesProposees.put(t, 0.0);
	}
}
