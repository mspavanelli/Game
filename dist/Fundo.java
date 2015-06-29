public class Fundo extends Elemento {
	
	double speed;
	double count;

	public Fundo(double coordenada_x, double coordenada_y, double speed, double count ) {
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.speed = speed;
		this.count = count;
	}

	abstract void desenha();
}