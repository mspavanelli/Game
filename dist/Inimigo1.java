import java.awt.Color;

public class Inimigo1 extends Inimigo {

	public long [] nextShoot;
	public long nextEnemy1;

	public Inimigo1(  int [] estados,double [] coordenada_x,double [] coordenada_y,
		double [] velocidade, double [] angulo, double [] velocidadeRotacao, double [] explosion_start,
		double [] explosion_end, long [] nextShoot, double raio, long nextEnemy1){
		super( estados );
		this.coordenada_x=coordenada_x;
		this.coordenada_y=coordenada_y;
		this.velocidade=velocidade;
		this.angulo=angulo;
		this.velocidadeRotacao=velocidadeRotacao;
		this.explosion_start=explosion_start;
		this.explosion_end=explosion_end;
		this.nextShoot=nextShoot;
		this.raio=raio;
		this.nextEnemy1=nextEnemy1;
	}

	public void desenha(long currentTime){
		for(int i = 0; i < estados.length; i++){

			if(estados[i] == 2){

				double alpha = (currentTime - explosion_start[i]) / (explosion_end[i] - explosion_start[i]);
				GameLib.drawExplosion(coordenada_x[i], coordenada_y[i], alpha);
			}

			if(estados[i] == 1){

				GameLib.setColor(Color.CYAN);
				GameLib.drawCircle(coordenada_x[i], coordenada_y[i], raio);
			}
		}
	}

}
