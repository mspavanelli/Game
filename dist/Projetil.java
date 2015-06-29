public abstract class Projetil extends Elemento {

	private double velocidade_x;
	private double velocidade_y;
	
	public Projetil(int estado, double coordenada_x, double coordenada_y,
			double velocidade_x, double velocidade_y) {
		super(estado, coordenada_x, coordenada_y);
		this.velocidade_x = velocidade_x;
		this.velocidade_y = velocidade_y;
	}
	
}