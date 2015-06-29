public class Inimigo extends Agente {
	
	double velocidade;
	double angulo;
	double velocidade_rotacao;
	double next_enemy;
	
	public Inimigo(double velocidade, double angulo, double velocidade_rotacao, double next_enemy){
		this.velocidade=velocidade;
		this.angulo=angulo;
		this.velocidade_rotacao=velocidade_rotacao;
		this.next_enemy=next_enemy;
		super(explosion_start, explosion_end, nextShot, raio);
	}
}
