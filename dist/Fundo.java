public abstract class Fundo extends Elemento {

	private double speed;
	private double count;
	
	public Fundo(int estado, double coordenada_x, double coordenada_y,
			double speed, double count) {
		super(estado, coordenada_x, coordenada_y);
		this.speed = speed;
		this.count = count;
	}
}