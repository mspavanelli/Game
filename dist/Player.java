public class Player extends Agente {
	
	double velocidade_x;
	double velocidade_y;
	
	public Player(double velocidade_x, double velocidade_y){
		this.velocidade_x=velocidade_x;
		this.velocidade_y=velocidade_y;
		super(explosion_start, explosion_end, nextShot, raio);
	}
}
