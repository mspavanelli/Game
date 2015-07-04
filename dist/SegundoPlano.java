import java.awt.Color;


public class SegundoPlano extends Fundo {

	public static double speed;
	public static double count;
	
	public SegundoPlano(int estado, double coordenada_x, double coordenada_y, double speed, double count) {
		super(estado, coordenada_x, coordenada_y, speed, count);
		this.speed = speed;
		this.count = count;
	}

	public void desenha( long currentTime ) {
		GameLib.setColor(Color.GRAY);
		long delta = System.currentTimeMillis() - currentTime;
		count += speed * delta;
		GameLib.fillRect(coordenada_x, (coordenada_y + count) % GameLib.HEIGHT, 2, 2);
	}

}