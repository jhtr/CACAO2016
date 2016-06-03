package abstraction.equipe1;

import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;

public class TransformateurTest implements Acteur, ITransformateur {

	@Override
	public double annonceQuantiteDemandee() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Catalogue getCatalogue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificationVente(IProducteur p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
}
