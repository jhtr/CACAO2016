package abstraction.commun;
import java.util.HashMap;
import java.util.List;

public interface IDistributeur {
	
	/** 
	 * @deprecated
	 */
	public double getDemande(ITransformateur t);
	// Rajouter une variable delais de livraisons

	/**
	 * @deprecated
	 * @return
	 */
	public double getPrix();
	
	public List<CommandeDistri> Demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> ContreDemande (List<CommandeDistri> cd);
	
}
