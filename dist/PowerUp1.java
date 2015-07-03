import java.awt.Color;
import java.util.Random;

public class PowerUp1 implements PowerUpState{
	
	int [] estados;
	double [] coordenada_x;
	double [] coordenada_y;
	double [] velocidade;
	double [] angulo;
	double [] raio;
	long nextPower;
	
	Random random = new Random();
	
	public PowerUp1(int [] estados, double []coordenada_x,double []coordenada_y,double [] velocidade,double [] angulo, double []raio,long nextPower){
		this.estados = estados;
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.velocidade = velocidade;
		this.angulo = angulo;
		this.raio = raio;
		this.nextPower = nextPower;
	}
	
	public void desenha(long currentTime){
		
		for(int i= 0; i <estados.length; i++ ){	
			GameLib.setColor(Color.WHITE);
			GameLib.drawSquare(coordenada_x[i], coordenada_y[i], raio[i]);
		}
	}
}
