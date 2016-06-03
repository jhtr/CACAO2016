package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

/**
 * Acteur gerant l'evolution du cours du cacao et realisant les echanges entre
 * producteurs et transformateurs.
 * 
 * @author equipe 1
 */
public class MarcheProducteur implements Acteur {
	/** Prix initial avant toute transaction */
	public static final double PRIX_DE_BASE = 3000.0;
	
	/** Reference statique de l'unique instance du marche du cacao */
	public static MarcheProducteur LE_MARCHE;

	
	/** Historique du prix du marche */
	private Indicateur coursCacao;
	
	/** Variation du cout par step */
	public static final double VARIATION_PRIX = 0.02; 
	
	//Extrema des prix du marche
	/** Prix minimum du cacao sur le marche */
	public static final double PRIX_MINIMUM = 2000;
	/** Prix maximum du cacao sur le marche */
	public static final double PRIX_MAXIMUM = 4000;

	private static ArrayList<IProducteur> producteurs;
	private static ArrayList<ITransformateur> transformateurs;
	
	/** Quantites de cacao proposees a chaque ITransformateur par chaque IProducteur */
	private HashMap<IProducteur,HashMap<ITransformateur,Double>> quantitesDisponibles;
	/** Quantites de cacao commandees a chaque IProducteur par chaque ITransformateur */
	private HashMap<ITransformateur,HashMap<IProducteur,Double>> commandesPassees;
	/**
	 * Constructeur du marche
	 */
	public MarcheProducteur() {
		MarcheProducteur.producteurs = new ArrayList<IProducteur>();
		MarcheProducteur.transformateurs = new ArrayList<ITransformateur>();
		this.coursCacao = new Indicateur("Cours Cacao",this,PRIX_DE_BASE);
		Monde.LE_MONDE.ajouterIndicateur(this.coursCacao);
		this.commandesPassees = new HashMap<ITransformateur,HashMap<IProducteur,Double>>();
		this.quantitesDisponibles = new HashMap<IProducteur,HashMap<ITransformateur,Double>>();
	}

	
	/** 
	 * Ajoute le producteur argument dans la liste des producteurs.
	 * Puis initialise une nouvelle paire dans quantiteDisponibles : la clef est le producteur argument, la valeur
	 * est une nouvelle hashmap associant chaque transformateur connu par le marche a la valeur zero.
	 */
	
	public void ajouterProducteur(IProducteur producteur){
		MarcheProducteur.producteurs.add(producteur);
		this.quantitesDisponibles.put(producteur, new HashMap<ITransformateur,Double>());
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			this.quantitesDisponibles.get(producteur).put(t, 0.0);
		}
	}

	
	/** 
	 * Ajoute le transformateur argument dans la liste des transformateurs.
	 * Puis initialise une nouvelle paire dans commandesPassees : la clef est le transformateur argument, la valeur
	 * est une nouvelle hashmap associant chaque producteur connu par le marche a la valeur zero.
	 */
	public void ajouterTransformateur(ITransformateur transformateur){
		MarcheProducteur.transformateurs.add(transformateur);
		this.commandesPassees.put(transformateur, new HashMap<IProducteur,Double>());
		for (IProducteur p : MarcheProducteur.producteurs) {
			this.commandesPassees.get(transformateur).put(p, 0.0);
		}
	}

	

	/**
	 * Renvoie la valeur actuelle du cout du cacao en euro par tonne
	 */
	public double getCours() {
		return coursCacao.getValeur();
	}

	/**
	 * Renvoie l'historique du cours du cacao pour l'affichage
	 */
	public Historique getHistorique(){
		return coursCacao.getHistorique();
	}
	
	/**
	 * Modifie le cours du cacao selon l'offre et la demande du step courant.
	 */
	private void actualiserCours() {
		// Toutes les quantites mises en vente
		double totalQuantitesEnVenteP = 0.0;
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			for (IProducteur p : MarcheProducteur.producteurs){
				totalQuantitesEnVenteP+=p.annonceQuantiteMiseEnVente(t);
			}
		}
		// Toutes les quantites demandees
		double totalQuantitesDemandeesT = 0.0;
		for (ITransformateur t: MarcheProducteur.transformateurs) {
			totalQuantitesDemandeesT+=t.annonceQuantiteDemandee();
		}
		// Si l'offre est superieure a la demande
		if (totalQuantitesEnVenteP > totalQuantitesDemandeesT){
			coursCacao.setValeur(this, Math.max(coursCacao.getValeur()*(1-VARIATION_PRIX),PRIX_MINIMUM));
		}
		// Si l'offre est inferieure a la demande
		else if (totalQuantitesEnVenteP < totalQuantitesDemandeesT){
			coursCacao.setValeur(this,Math.min(coursCacao.getValeur()*(1+VARIATION_PRIX),PRIX_MAXIMUM));
		}
		// Si l'offre est exactement la meme que la demande
		else {
			coursCacao.setValeur(this, coursCacao.getValeur()); //On affecte une valeur pour actualiser l'historique
		}
	}
	/**
	 * Renvoie le nom du marche
	 */
	public String getNom() {
		return "Marche Cacao";
	}
	
	/**
	 * Renvoie la somme des quantite commandees durant le step courant par le transformateur t a tous les producteurs.
	 */
	private double getCommandeTotale(ITransformateur t) {
		double quantite = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			quantite += this.commandesPassees.get(t).get(p);
		}
		return quantite;
	}
	
	/**
	 * Renvoie le nombre de producteurs auxquels le transformateur t a pour l'instant passe une commande strictement
	 * inferieure a la quantite qu'ils proposaient (individuellement).
	 */
	private int getNombreVendeursDisponibles(ITransformateur t) {
		int n = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			if (this.quantitesDisponibles.get(p).get(t) > 0) {
				n += 1;
			}
		}
		return n;
	}
	
	/**
	 * Renvoie true uniquement si la demande du transformateur t est satisfaite (la somme de ses commandes etant alors
	 * egale a sa demande initiale) ou s'il a deja commande absolument tout le cacao qui lui etait propose par l'ensemble
	 * des producteurs.
	 */
	private boolean satisfait(ITransformateur t) {
		return (this.getCommandeTotale(t) == t.annonceQuantiteDemandee()) || (this.getNombreVendeursDisponibles(t) == 0);
	}
	
	/**
	 * Cree une liste contenant tous les transformateurs non satisfaits au debut de la phase d'echange. Autrement dit,
	 * seuls les transformateurs qui ne commandent rien ou ceux a qui aucun producteur ne propose de cacao ne seront pas
	 * dans cette liste.
	 */
	private List<ITransformateur> creerListeAttente() {
		List<ITransformateur> attente = new ArrayList<ITransformateur>();
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			if (!this.satisfait(t)) {
				attente.add(t);
			}
		}
		return attente;
	}
	
	/**
	 * Methode appelee au tout debut d'une phase d'echange : toutes les commandes sont remises a zero et toutes les
	 * quantites disponibles sont recuperees aupres des producteurs.
	 */
	private void actualiserStocksEtCommandes() {
		for (IProducteur p : MarcheProducteur.producteurs) {
			for (ITransformateur t : MarcheProducteur.transformateurs) {
				this.quantitesDisponibles.get(p).put(t, p.annonceQuantiteMiseEnVente(t));
				this.commandesPassees.get(t).put(p,0.0);
			}
		}
	}
	
	/**
	 * Modifie les deux HashMaps en fonction de la quantite commandee par le transformateur t aupres du producteur p.
	 * Ainsi commandesPassees ajoute cette quantite a celle qu'elle avait deja en memoire, et quantitesDisponibles retire
	 * cette quantite a celle qu'elle avait deja en memoire.
	 */
	private void ajouterCommande(ITransformateur t, IProducteur p, double quantite) {
		this.commandesPassees.get(t).put(p,this.commandesPassees.get(t).get(p)+quantite);
		this.quantitesDisponibles.get(p).put(t,this.quantitesDisponibles.get(p).get(t)-quantite);
	}
	
	/**
	 * Methode permettant de repartir equitablement les commandes d'un transformateur aupres des producteurs.
	 * Elle doit etre appelee plusieurs fois par une autre methode (prevoirCommandes) pour arriver a la repartition finale.
	 * 
	 * Lors d'un appel, elle determine le besoin effectif du transformateur (sa demande initiale moins ce qu'il a deja
	 * commande lors des appels precedents) et le nombre de producteurs disposant encore de cacao pouvant etre achete par
	 * ce transformateur.
	 * Dans le while :
	 * En theorie, le transformateur devrait commander besoinEffectif/vendeursDisponibles a chaque producteur : dans un cas
	 * ideal, les stocks disponibles permettent de repondre totalement a la demande.
	 * Dans le cas contraire, tant que le transformateur n'est pas satisfait (et que tous les producteurs n'ont pas ete
	 * passes en revue), il commande le minimum entre ce qui est disponible chez le producteur actuel (accessible via
	 * MarcheProducteur.producteurs.get(i)) et la commande theorique (besoinEffectif/vendeursDisponibles).
	 */
	private void repartirCommandes(ITransformateur t) {
		double q = 0;
		double besoinEffectif = t.annonceQuantiteDemandee()-this.getCommandeTotale(t);
		double vendeursDisponibles = this.getNombreVendeursDisponibles(t);
		
		int i = 0;
		while (i<MarcheProducteur.producteurs.size() && !this.satisfait(t)) {
			q = Math.min(this.quantitesDisponibles.get(MarcheProducteur.producteurs.get(i)).get(t),besoinEffectif/vendeursDisponibles);
			this.ajouterCommande(t,MarcheProducteur.producteurs.get(i),q);
			i++;
		}
	}
	
	/**
	 * Methode principale gerant les echanges au sein du marche. Tant qu'il existe des transformateurs non "satisfaits"
	 * (voir la methode satisfait(ITransformateur t)), donc presents dans la liste d'attente, ceux-ci passent des commandes
	 * via la methode repartirCommandes. Une fois un transformateur satisfait, il quitte la liste d'attente.
	 */
	private void prevoirCommandes() {
		List<ITransformateur> attente = creerListeAttente();
		int i = 0;
		while (attente.size() > 0) {
			i = 0;
			while (attente.size() > 0 && i < attente.size()) {
				this.repartirCommandes(attente.get(i));
				if (this.satisfait(attente.get(i))) {
					attente.remove(i);
				} else {
					i++;
				}
			}
		}
	}
	
	/**
	 * Methode appelee a la fin de la phase d'echange, donc une fois que tous les transformateurs sont "satisfaits".
	 * Notifie chaque ITransformateur et chaque IProducteur des commandes qui le concernent. 
	 */
	private void effectuerCommandes() {
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			for (IProducteur p : MarcheProducteur.producteurs) {
				CommandeProduc c = new CommandeProduc(t,p,this.commandesPassees.get(t).get(p),t.annoncePrix());
				t.notificationVente(c);
				p.notificationVente(c);
			}
		}
	}
	
	/**
	 * Methode appelee par le monde, au meme niveau que les nexts des autres Acteurs.
	 * Le marche reinitialise ses commandes et recupere les quantites proposees par les producteurs aux transformateurs,
	 * puis decide de la repartition des commandes entre les producteurs, puis notifie chaque acteur des commandes qui le
	 * concernent, puis actualise le cours du cacao.
	 */
	public void next() {
		this.actualiserStocksEtCommandes();
		this.prevoirCommandes();
		this.effectuerCommandes();
		this.actualiserCours();
	}
}
