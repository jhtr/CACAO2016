package abstraction.equipe1;

import java.util.List;
import java.util.Random;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class TransformateurTest implements Acteur, ITransformateur {
	private String nom;
	private Indicateur tresorerie;
	private Indicateur stockCacao;
	private double prix;
	private double quantiteDemandee;
	
	public TransformateurTest(String nom) {
		this.nom = nom;
		
		this.tresorerie = new Indicateur("Tresorerie de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		this.tresorerie.setValeur(this, 10000);
		
		this.stockCacao = new Indicateur("Stock de "+this.nom, this);
		Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
		this.stockCacao.setValeur(this, 0);
		
		this.prix = 2500;
		this.quantiteDemandee = 2000;
	}
	
	public double annonceQuantiteDemandee() {
		return this.quantiteDemandee;
	}
	
	public double annoncePrix() {
		return this.prix;
	}
	
	public void notificationVente(CommandeProduc c) {
		this.stockCacao.setValeur(this, this.stockCacao.getValeur()+c.getQuantite());
		this.tresorerie.setValeur(this, this.tresorerie.getValeur()-c.getQuantite()*c.getPrixTonne());
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void next() {
		//Random random = new Random();
		
	}
	
	@Override
	public Catalogue getCatalogue() {
		// TODO Auto-generated method stub
		return null;
		// irrelevant
	}
	
	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
		// irrelevant
	}
	
	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
		// irrelevant
	}
	
	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// TODO Auto-generated method stub
		return null;
		// irrelevant
	}
	
	@Override
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
		// irrelevant
	}
	
	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		// TODO Auto-generated method stub
		return 0;
		// deprecated
	}
	
	@Override
	public void notificationVente(IProducteur p) {
		// TODO Auto-generated method stub
		// deprecated
	}
}
