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

	public void desenha( long currentTime ){
		GameLib.setColor(Color.BLUE);
		GameLib.drawPlayer(player.coordenada_x, player.coordenada_y, player.raio);
	}

	public void controleMovimetoPlayer(long currentTime, long delta){

		if(GameLib.iskeyPressed(GameLib.KEY_UP)) coordenada_y -= delta * velocidade_y;
		if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) coordenada_y += delta * velocidade_y;
		if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) coordenada_x -= delta * velocidade_x;
		if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) coordenada_x += delta * velocidade_y;
		if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {

			if(currentTime > nextShot){
				int free = Main.findFreeIndex(projectile.estadosProjetil);

				if(free < projectile.estadosProjetil.length){

					projectile.coordenada_x[free] = coordenada_x;
					projectile.coordenada_y[free] = coordenada_y - 2 * raio;
					projectile.velocidade_x[free] = 0.0;
					projectile.velocidade_y[free] = -1.0;
					projectile.estadosProjetil[free] = 1;
					nextShot = currentTime + 100;
				}
			}
		}
		
		if(coordenada_x < 0.0) coordenada_x = 0.0;
		if(coordenada_x >= GameLib.WIDTH) coordenada_x = GameLib.WIDTH - 1;
		if(coordenada_y< 25.0) coordenada_y = 25.0;
		if(coordenada_y >= GameLib.HEIGHT) coordenada_y = GameLib.HEIGHT -1;
		
	}
}
