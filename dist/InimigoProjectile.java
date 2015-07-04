import java.awt.Color;

public class InimigoProjectile{

	public int [] estados;
	public double [] coordenada_x;
	public double [] coordenada_y;
	public double [] velocidade_x;
	public double [] velocidade_y;
	public double raio;


	public InimigoProjectile(int [] estados, double [] coordenada_x, double [] coordenada_y, double [] velocidade_x,  double [] velocidade_y, double raio){
		this.estados=estados;
		this.coordenada_x=coordenada_x;
		this.coordenada_y=coordenada_y;
		this.velocidade_x=velocidade_x;
		this.velocidade_y=velocidade_y;
		this.raio=raio;
	}
	public void desenha(){
		for(int i = 0; i < estados.length; i++){

			if(estados[i] == 1){

				GameLib.setColor(Color.RED);
				GameLib.drawCircle(coordenada_x[i], coordenada_y[i], raio);
			}
		}
	}

}
