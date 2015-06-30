public class InimigoUm extends Inimigo {


	public InimigoUm(int estado, double coordenada_x, double coordenada_y,
			double explosion_start, double explosion_end, double nextShot,
			double raio, double velocidade, double angulo,
			double velocidade_rotacao, double next_enemy) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end,
				nextShot, raio, velocidade, angulo, velocidade_rotacao, next_enemy);
	}

	public void desenha() {
		if(enemy1_states[i] == Elemento.EXPLODING){
			double alpha = (MainTeste.currentTime - enemy1_explosion_start[i]) / (enemy1_explosion_end[i] - enemy1_explosion_start[i]);
			GameLib.drawExplosion(enemy1_X[i], enemy1_Y[i], alpha);
		}
		if(enemy1_states[i] == Elemento.ACTIVE){
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(enemy1_X[i], enemy1_Y[i], enemy1_radius);
		}
	}
}