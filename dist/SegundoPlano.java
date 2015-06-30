import java.awt.Color;

public class SegundoPlano extends Fundo {

	static double count;
	static double speed;
	
	public SegundoPlano(int estado, double coordenada_x, double coordenada_y, double speed, double count) {
		super(estado, coordenada_x, coordenada_y);
		this.count = count;
		this.speed = speed;
	}

	public void desenha() {
		GameLib.setColor(Color.GRAY);
		long delta = System.currentTimeMillis() - Main.currentTime;
		count += speed * delta;
		GameLib.fillRect(coordenada_x, (coordenada_y + count) % GameLib.HEIGHT, 2, 2);
	}

}