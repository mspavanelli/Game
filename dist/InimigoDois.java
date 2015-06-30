public class InimigoDois extends Inimigo {

	private int contagem_inimigos;
	private double coordenada_x_proximo;
	
	public InimigoDois(int estado, double coordenada_x, double coordenada_y,
			double explosion_start, double explosion_end, double nextShot,
			double raio, double velocidade, double angulo,
			double velocidade_rotacao, double next_enemy) {
		super(estado, coordenada_x, coordenada_y, explosion_start,
				explosion_end, nextShot, raio, velocidade, angulo,
				velocidade_rotacao, next_enemy);
	}

	public void desenha() {
		if(enemy2_states[i] == Elemento.EXPLODING){
			double alpha = (MainTeste.currentTime - enemy2_explosion_start[i]) / (enemy2_explosion_end[i] - enemy2_explosion_start[i]);
			GameLib.drawExplosion(enemy2_X[i], enemy2_Y[i], alpha);
		}
				
		if(enemy2_states[i] == Elemento.ACTIVE){
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(enemy2_X[i], enemy2_Y[i], enemy2_radius);
		}
	}
}