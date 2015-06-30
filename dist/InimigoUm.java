import java.awt.Color;

public class InimigoUm extends Inimigo {

	public InimigoUm(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double nextShot, double raio, double velocidade, double angulo, double velocidade_rotacao, double next_enemy) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end, nextShot, raio, velocidade, angulo, velocidade_rotacao, next_enemy);
	}


	public void desenha() {
		if(estado == Elemento.EXPLODING){
			double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(coordenada_x, coordenada_y, alpha);
		}
		if(estado == Elemento.ACTIVE){
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(coordenada_x, coordenada_y, raio);
		}
	}
}