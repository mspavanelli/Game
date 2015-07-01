import java.util.*;
import java.awt.Color;

public class Player extends Agente {

	public double velocidade_x;
	public double velocidade_y;
	public long nextShot;
	public LinkedList<ProjetilPlayer> projeteis;
		
	public Player(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, long nextShot, double raio, double velocidade_x, double velocidade_y) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end, raio);
		this.velocidade_x = velocidade_x;
		this.velocidade_y = velocidade_y;
		this.nextShot = nextShot;
		projeteis = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			projeteis.add( new ProjetilPlayer(Elemento.INACTIVE, coordenada_x, coordenada_y, 0.0, -1.0) );
		}
	}

	public void movimenta( double currentTime, long delta ) {
		if(estado == Elemento.ACTIVE) {
			
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) coordenada_y -= delta * velocidade_y;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) coordenada_y += delta * velocidade_y;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) coordenada_x -= delta * velocidade_x;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) coordenada_x += delta * velocidade_x;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				if(currentTime > nextShot){
					
					// int free = findFreeIndex(projectile_states);
											
					// if(free < projectile_states.length){
						
					// 	projectile_X[free] = coordenada_x;
					// 	projectile_Y[free] = coordenada_y - 2 * raio;
					// 	projectile_VX[free] = 0.0;
					// 	projectile_VY[free] = -1.0;
					// 	projectile_states[free] = 1;
					// 	nextShot = currentTime + 100;
					// }
				}	
			}
		}
		
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) Main.running = false;
		
		/* Verificando se coordenadas do player ainda estão dentro	*/
		/* da tela de jogo após processar entrada do usuário.       */
		
		if(coordenada_x < 0.0) coordenada_x = 0.0;
		if(coordenada_x >= GameLib.WIDTH) coordenada_x = GameLib.WIDTH - 1;
		if(coordenada_y < 25.0) coordenada_y = 25.0;
		if(coordenada_y >= GameLib.HEIGHT) coordenada_y = GameLib.HEIGHT - 1;

	}
	
	public void desenha() {
		if(estado == EXPLODING){	
			double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(coordenada_x, coordenada_y, alpha);
		}
		else {
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(coordenada_x, coordenada_y, raio);
		}
	}
}
