import java.awt.Color;

public class ProjetilPlayer extends Projetil {
	
	public ProjetilPlayer(int estado, double coordenada_x, double coordenada_y, double velocidade_x, double velocidade_y) {
		super(estado, coordenada_x, coordenada_y, velocidade_x, velocidade_y);
	}

	public void desenha() {
		if(estado == Elemento.ACTIVE){		
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(coordenada_x, coordenada_y - 5, coordenada_x, coordenada_y + 5);
			GameLib.drawLine(coordenada_x - 1, coordenada_y - 3, coordenada_x - 1, coordenada_y + 3);
			GameLib.drawLine(coordenada_x + 1, coordenada_y - 3, coordenada_x + 1, coordenada_y + 3);
		}
	}
}