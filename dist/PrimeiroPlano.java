import java.awt.Color;

public class PrimeiroPlano extends Fundo {

	static double count;
	static double speed;
	
	public PrimeiroPlano(int estado, double coordenada_x, double coordenada_y, double speed, double count) {
		super(estado, coordenada_x, coordenada_y);
		this.count = count;
		this.speed = speed;
	}

	public void desenha( long currentTime ) {
		GameLib.setColor(Color.GRAY);
		long delta = System.currentTimeMillis() - currentTime;
		count += speed * delta;
		GameLib.fillRect(coordenada_x, (coordenada_y + count) % GameLib.HEIGHT, 3, 3);
	}

}
