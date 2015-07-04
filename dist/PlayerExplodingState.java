import java.awt.Color;

public class PlayerExplodingState extends PlayerState{

	Player player;

	public PlayerExplodingState(Player player){
		this.player=player;
	}

	public void colisaoInimigos(){
		System.out.println( "voltando ao estado ativo" );
		new PlayerActiveState( player );
	}
	public void colisaoPlayerProjetilInimigo() {
		System.out.println( "voltando ao estado ativo" );
		new PlayerActiveState( player );
	}
	public void desenha( long currentTime ){
			double alpha = (currentTime - player.explosion_start) / (player.explosion_end - player.explosion_start);
			GameLib.drawExplosion(player.coordenada_x, player.coordenada_y, alpha);
	}

	public void controleMovimetoPlayer(long currentTime, long delta){
		
	}
}
