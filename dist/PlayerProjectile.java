import java.awt.Color;

public class PlayerProjectile{

	PowerUp1 powerUp1;
	public int [] estadosProjetil;
	public double [] coordenada_x;
	public double [] coordenada_y;
	public double [] velocidade_x;
	public double [] velocidade_y;

	public PlayerProjectile(int [] estadosProjetil, double [] coordenada_x, double [] coordenada_y, double [] velocidade_x, double [] velocidade_y, PowerUp1 powerUp1){
		this.estadosProjetil=estadosProjetil;
		this.coordenada_x=coordenada_x;
		this.coordenada_y=coordenada_y;
		this.velocidade_x=velocidade_x;
		this.velocidade_y=velocidade_y;
		this.powerUp1=powerUp1;

	}

	public void desenha(){


		for(int i = 0; i < estadosProjetil.length; i++){

			if(estadosProjetil[i] == 1 && powerUp1.estado==false){

				GameLib.setColor(Color.GREEN);
				GameLib.drawLine(coordenada_x[i], coordenada_y[i] - 5, coordenada_x[i], coordenada_y[i] + 5);
				GameLib.drawLine(coordenada_x[i] - 1,coordenada_y[i] - 3,coordenada_x[i] - 1,coordenada_y[i] + 3);
				GameLib.drawLine(coordenada_x[i] + 1,coordenada_y[i] - 3, coordenada_x[i] + 1, coordenada_y[i] + 3);
			}
		 if(estadosProjetil[i] == 1 && powerUp1.estado==true){
			 GameLib.setColor(Color.GREEN);
			 GameLib.drawCircle(coordenada_x[i], coordenada_y[i] - 5,9.5);
			 GameLib.drawCircle(coordenada_x[i] - 1,coordenada_y[i] - 3,9.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,8.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,7.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,6.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,5.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,4.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,3.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,2.0);
			 GameLib.drawCircle(coordenada_x[i] + 1,coordenada_y[i] - 3,1.0);
		 }

		}
	}
}
