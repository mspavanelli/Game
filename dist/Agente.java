public abstract class Agente extends Elemento {
	
	private double explosion_start;
	private double explosion_end;
	private double nextShot;
	private double raio;
	
	public Agente(int estado, double coordenada_x, double coordenada_y,
			double explosion_start, double explosion_end, double nextShot,
			double raio) {
		super(estado, coordenada_x, coordenada_y);
		this.explosion_start = explosion_start;
		this.explosion_end = explosion_end;
		this.nextShot = nextShot;
		this.raio = raio;
	}

}