public class Agente extends Elemento {
	
	double explosion_start;
	double explosion_end;
	double nextShot;
	double raio;
	
	public Agente(double explosion_start, double explosion_end, double nextShot, double raio){
		this.explosion_start=explosion_start;
		this.explosion_end=explosion_end;
		this.nextShot=nextShot;
		this.raio=raio;
		super(coordenada_x, coordenada_y);
	}
}
