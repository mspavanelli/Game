import java.util.*;
import java.awt.Color;

public class InimigoDois extends Inimigo {

	public int contagem_inimigos;
	public double coordenada_x_proximo;
	public LinkedList<ProjetilInimigo> projeteis;
	
	public InimigoDois(int estado, double coordenada_x, double coordenada_y, double explosion_start, double explosion_end, double raio, double velocidade, double angulo, double velocidade_rotacao, double next_enemy) {
		super(estado, coordenada_x, coordenada_y, explosion_start, explosion_end, raio, velocidade, angulo, velocidade_rotacao, next_enemy);
		projeteis = new LinkedList<>();
		for ( int i = 0; i < 200; i++ )
			projeteis.add( new ProjetilInimigo( Elemento.INACTIVE, coordenada_x, coordenada_y, Math.cos(angulo) * 0.45, Math.sin(angulo) * 0.45 * (-1.0), 2.0));
	}

	public void desenha() {
		if(estado == Elemento.EXPLODING){
			double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(coordenada_x, coordenada_y, alpha);
		}
				
		if(estado == Elemento.ACTIVE){
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(coordenada_x, coordenada_y, raio);
		}
	}
}