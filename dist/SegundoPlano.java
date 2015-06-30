import java.awt.Color;


public class SegundoPlano extends Fundo {

	
	public SegundoPlano(int estado, double coordenada_x, double coordenada_y, double speed, double count) {
		super(estado, coordenada_x, coordenada_y, speed, count);
	}

	public void desenha() {
		GameLib.setColor(Color.GRAY);
		long delta = System.currentTimeMillis() - Main.currentTime;
		count += speed * delta;
		GameLib.fillRect(coordenada_x, (coordenada_y + count) % GameLib.HEIGHT, 2, 2);
	}

}
