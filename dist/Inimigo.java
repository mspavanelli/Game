public class Inimigo extends Agente {
	
	double velocidade;
	double angulo;
	double velocidade_rotacao;
	double next_enemy;

	public Inimigo() {
		super();
		velocidade = v;
		angulo = a;
		velocidade_rotacao = v_r;
		next_enemy = n_e;
	}

	abstract void desenha();
}