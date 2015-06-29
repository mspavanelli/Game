public class Player extends Agente {

	private double velocidade_x;
	private double velocidade_y;
		
	public Player(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double nextShot, double raio, double velocidade_x, double velocidade_y) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end, nextShot, raio);
		this.velocidade_x = velocidade_x;
		this.velocidade_y = velocidade_y;
	}

	public void desenha() {
		if(player_state == EXPLODING){
				
				double alpha = (currentTime - player_explosion_start) / (player_explosion_end - player_explosion_start);
				GameLib.drawExplosion(player_X, player_Y, alpha);
			}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(player_X, player_Y, player_radius);
		}
	}

}
