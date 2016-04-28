package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Lindt implements Acteur, ITransformateur{
	
	private HistoriqueCommandeDist hist;
	private Stock stock_cacao;
	private Stock stock_chocolat;
	private Indicateur etatStockCacao;
	private Indicateur etatStockChocolat;
	private Indicateur venteChocolat;
	private Tresorerie treso;
	private AchatProd achatProd;
	private double quantiteMiseEnVenteProducteur1; 
	private double quantiteMiseEnVenteProducteur2; 
	private IProducteur P1;
	private IProducteur P2;
	private IDistributeur D1;
	private IDistributeur D2;
	
	
	public Lindt(){
		this.hist = new HistoriqueCommandeDist();
		this.stock_cacao = new Stock(0);
		this.stock_chocolat = new Stock(0);
		this.etatStockCacao = new Indicateur("Stock de Cacao Lindt", this, this.stock_cacao.getStock());
		this.etatStockChocolat = new Indicateur("Stock de Chocolat Lindt", this, this.stock_chocolat.getStock());
		this.venteChocolat = new Indicateur("quantité de chocolat vendue Lindt", this, this.stock_chocolat.getStock());
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockCacao );
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockChocolat );
		Monde.LE_MONDE.ajouterIndicateur(venteChocolat);
		this.treso=new Tresorerie(this.getHist());
		this.achatProd = new AchatProd(hist, quantiteMiseEnVenteProducteur1, quantiteMiseEnVenteProducteur2);

		
	}
	
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;}
	
	public HistoriqueCommandeDist getHist(){
		return this.hist;
	}
	

	public void next() {
		P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
		D1 = (IDistributeur)Monde.LE_MONDE.getActeur("Carrefour");
		D2 = (IDistributeur)Monde.LE_MONDE.getActeur("Leclerc");
		quantiteMiseEnVenteProducteur1 = P1.annonceQuantiteMiseEnVente(this);
		quantiteMiseEnVenteProducteur2 = P2.annonceQuantiteMiseEnVente(this);
		this.getHist().ajouter(D1.getDemande(this)+ D2.getDemande(this));
		stock_chocolat.ajouterStock(this.getHist().valeur(Constante.STEP_PRECEDENT_MOINS_2));
		stock_chocolat.retirerStock(this.getHist().valeur(Constante.STEP_PRECEDENT_MOINS_3));
		stock_cacao.ajouterStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT)); // stock lié au reste du monde
		stock_cacao.retirerStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT_MOINS_2)); // stock lié au reste du monde
		treso.ajouterTresorerie(treso.marge());
		treso.retirerTresorerie(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * 3000); // achat cacao au reste du monde
		
		this.etatStockCacao.setValeur(this, this.stock_cacao.getStock());
		this.etatStockChocolat.setValeur(this, this.stock_chocolat.getStock());
		this.venteChocolat.setValeur(this, this.stock_chocolat.getStock());	
	}

	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	public void notificationVente(IProducteur p){ // on travaille avec chaque producteur d'où le ratio de 0.3 à chaque fois
		stock_cacao.ajouterStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT));
		stock_cacao.retirerStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT_MOINS_2));
		treso.retirerTresorerie(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * p.annoncePrix());
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.achatProd.annonceQuantiteDemandee(p);
	}
} 