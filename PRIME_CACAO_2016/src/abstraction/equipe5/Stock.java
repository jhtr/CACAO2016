package abstraction.equipe5;

public class Stock {
	private double stock;
	
	public Stock (double d) {
		this.stock = d;
	}
	
	public Stock() {
		this.stock = 0;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}
	
	public void ajouterStock(double d) {
		this.setStock(this.getStock()+d);
	}
	
	public void retirerStock(double d) {
		if (this.getStock()-d >= 0) {
			this.setStock(this.getStock()-d);
		}	
	}
}