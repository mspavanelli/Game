import java.awt.Color;

public class PlayerActiveState extends PlayerState{

	Player player;

	public PlayerActiveState(Player player){
		this.player=player;
	}

	public void colisaoInimigos(){
		System.out.println( "colisaoInimigos" );
		new PlayerExplodingState( player );
	}
	
	public void colisaoPlayerProjetilInimigo(){
		System.out.println( "colisaoPlayerProjetilInimigo" );
		new PlayerExplodingState( player );
	}

	public void desenhaPlayer( long currentTime ){
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(player.coordenada_x, player.coordenada_y, player.raio);
	}
}
