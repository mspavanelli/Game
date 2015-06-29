public class Agente extends Elemento {
	
	double explosion_start;
	double explosion_end;
	double nextShot;
	double raio;
	
	public Agente(double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double nextShot, double raio){
		super(coordenada_x, coordenada_y);
		this.explosion_start = explosion_start;
		this.explosion_end = explosion_end;
		this.nextShot = nextShot;
		this.raio = raio;
	}

	abstract void desenha();
}
