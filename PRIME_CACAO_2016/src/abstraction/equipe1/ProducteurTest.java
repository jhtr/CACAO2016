package abstraction.equipe1;

import java.util.HashMap;
import java.util.Map;

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
	
	public ProducteurTest(String nom) {
		this.nom = nom;
		
		this.tresorerie = new Indicateur("Tresorerie de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		this.tresorerie.setValeur(this, 0);
		
		this.stockCacao = new Indicateur("Stock de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
		this.stockCacao.setValeur(this, 2000);
		
		this.quantitesProposees = new HashMap<ITransformateur,Double>();
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
		
	}
	
	public void ajouterTransformateur(ITransformateur t) {
		this.quantitesProposees.put(t, 0.0);
	}
}
