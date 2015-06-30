public abstract class Inimigo extends Agente {

	double velocidade;
	double angulo;
	double velocidade_rotacao;
	double next_enemy;
	
	public Inimigo(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double raio, double velocidade, double angulo, double velocidade_rotacao, double next_enemy) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end, raio);
		this.velocidade = velocidade;
		this.angulo = angulo;
		this.velocidade_rotacao = velocidade_rotacao;
		this.next_enemy = next_enemy;
	}

}