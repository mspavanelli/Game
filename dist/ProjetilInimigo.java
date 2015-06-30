public class ProjetilInimigo extends Projetil {

	double raio;

	public ProjetilInimigo(int estado, double coordenada_x, double coordenada_y, double velocidade_x, double velocidade_y, double raio) {
		super(estado, coordenada_x, coordenada_y, velocidade_x, velocidade_y);
		this.raio = raio;
	}

	public void desenha() {
		/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == Elemento.ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e_projectile_X[i], e_projectile_Y[i], e_projectile_radius);
				}
			}
			
			/* desenhando inimigos (tipo 1) */
			
			for(int i = 0; i < enemy1_states.length; i++){
				
				if(enemy1_states[i] == Elemento.EXPLODING){
					
					double alpha = (MainTeste.currentTime - enemy1_explosion_start[i]) / (enemy1_explosion_end[i] - enemy1_explosion_start[i]);
					GameLib.drawExplosion(enemy1_X[i], enemy1_Y[i], alpha);
				}
				
				if(enemy1_states[i] == Elemento.ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1_X[i], enemy1_Y[i], enemy1_radius);
				}
			}
			
			/* desenhando inimigos (tipo 2) */
			
			for(int i = 0; i < enemy2_states.length; i++){
				
				if(enemy2_states[i] == Elemento.EXPLODING){
					
					double alpha = (MainTeste.currentTime - enemy2_explosion_start[i]) / (enemy2_explosion_end[i] - enemy2_explosion_start[i]);
					GameLib.drawExplosion(enemy2_X[i], enemy2_Y[i], alpha);
				}
				
				if(enemy2_states[i] == Elemento.ACTIVE){
			
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2_X[i], enemy2_Y[i], enemy2_radius);
				}
			}
	}
}