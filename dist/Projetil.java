public class Projetil extends Elemento {

	double velocidade_x;
	double velocidade_y;
	
	public Projetil(double coordenada_x, double coordenada_y, double velocidade_x, double velocidade_y){
		super(coordenada_x, coordenada_y);
		this.velocidade_x = velocidade_x;
		this.velocidade_y = velocidade_y;
	}

	abstract void desenha();
}
