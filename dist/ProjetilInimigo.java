public class ProjetilInimigo extends Projetil {

	double raio;

	public ProjetilInimigo(int estado, double coordenada_x,
			double coordenada_y, double velocidade_x, double velocidade_y,
			double raio) {
		super(estado, coordenada_x, coordenada_y, velocidade_x, velocidade_y);
		this.raio = raio;
	}

	public void desenha() {

	}
}