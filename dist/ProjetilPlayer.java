public class ProjetilPlayer extends Projetil {
	
	public ProjetilPlayer(int estado, double coordenada_x, double coordenada_y,
			double velocidade_x, double velocidade_y) {
		super(estado, coordenada_x, coordenada_y, velocidade_x, velocidade_y);
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