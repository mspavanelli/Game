import java.awt.Color;

public class Player{

	PlayerState state;
	PlayerProjectile projectile;

	public int estado;
	public double coordenada_x;
	public double coordenada_y;
	public double velocidade_x;
	public double velocidade_y;
	public double raio;
	public double explosion_start;
	public double explosion_end;
	public long nextShot;

	public Player(PlayerProjectile projectile, int estado, double coordenada_x, double coordenada_y, double velocidade_x, double velocidade_y,
					 double raio, double explosion_start, double explosion_end, long nextShot){

		this.projectile=projectile;
		this.estado=estado;
		this.coordenada_x=coordenada_x;
		this.coordenada_y=coordenada_y;
		this.velocidade_x=velocidade_x;
		this.velocidade_y=velocidade_y;
		this.raio=raio;
		this.explosion_start=explosion_start;
		this.explosion_end=explosion_end;
		this.nextShot=nextShot;
		state = new PlayerActiveState( this );
	}
/*
	void setState(PlayerState state){
		this.state=state;
	}

	public PlayerState getActiveState(){
		return this.activeState;
	}

	public PlayerState getExplodingState(){
		return this.explodingState;
	}
*/
	//controle de movimento do player
	public boolean controleMovimetoPlayer(long currentTime, long delta , boolean running){
		if(estado == 1){

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
		}
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) return false;
		return true;
	}
	//desenha o player
	public void desenhaPlayer(long currentTime){
		if(estado == 2){

			double alpha = (currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(coordenada_x, coordenada_y, alpha);
		}
		else{

			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(coordenada_x, coordenada_y, raio);
		}
	}
}