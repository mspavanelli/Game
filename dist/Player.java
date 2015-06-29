public class Player extends Agente {
	
	double velocidade_x;
	double velocidade_y;
	
	public Player(double coordenada_x, double coordenada_y, double raio, double explosion_start, double explosion_end, double nextShot ,double velocidade_x, double velocidade_y){
		super();	// construtor da classe Agente
		this.velocidade_x = velocidade_x;
		this.velocidade_y = velocidade_y;
	}

	public void desenha() {
		if ( estado == EXPLODING ) {
			double alpha = (currentTime - player_explosion_start) / (player_explosion_end - player_explosion_start);
			GameLib.drawExplosion(player_X, player_Y, alpha);
		}
		else {
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(player_X, player_Y, player_radius);
		}
	}
}
