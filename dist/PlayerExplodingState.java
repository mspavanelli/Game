import java.awt.Color;

public class PlayerExplodingState extends PlayerState{

	protected Player player;

	public PlayerExplodingState(Player player){
		this.player=player;
	}


	public void desenha( long currentTime ) {
		double alpha = (currentTime - player.explosion_start) / (player.explosion_end - player.explosion_start);
		GameLib.drawExplosion(player.coordenada_x, player.coordenada_y, alpha);
	}

	public void controleMovimetoPlayer(long currentTime, long delta) {}
}
