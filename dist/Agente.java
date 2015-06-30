public abstract class Agente extends Elemento {
	
	public double explosion_start;
	public double explosion_end;
	public double raio;
	
	public Agente(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double raio) {
		super(estado, coordenada_x, coordenada_y);
		this.explosion_start = explosion_start;
		this.explosion_end = explosion_end;
		this.raio = raio;
	}

	public abstract void localizacao();

}
