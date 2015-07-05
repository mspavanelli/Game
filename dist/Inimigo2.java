import java.awt.Color;
public class Inimigo2{


  public int [] estados;
  public double [] coordenada_x;
  public double [] coordenada_y;
  public double [] velocidade;
  public double [] angulo;
  public double [] velocidadeRotacao;
  public double [] explosion_start;
  public double [] explosion_end;
  public double spawnX;
  public int count;
  public double raio;
  public long nextEnemy2;

  public Inimigo2(int [] estados,double [] coordenada_x,double [] coordenada_y,double [] velocidade,
    double [] angulo,double [] velocidadeRotacao,double [] explosion_start,double [] explosion_end,
    double spawnX,int count,double raio,long nextEnemy2){

    this.estados=estados;
    this.coordenada_x=coordenada_x;
    this.coordenada_y=coordenada_y;
    this.velocidade=velocidade;
    this.angulo=angulo;
    this.velocidadeRotacao=velocidadeRotacao;
    this.explosion_start=explosion_start;
    this.explosion_end=explosion_end;
    this.spawnX=spawnX;
    this.count=count;
    this.raio=raio;
    this.nextEnemy2=nextEnemy2;

  }

  public void desenha(long currentTime){
    for(int i = 0; i < estados.length; i++){

      if(estados[i] == 2){

        double alpha = (currentTime - explosion_start[i]) / (explosion_end[i] - explosion_start[i]);
        GameLib.drawExplosion(coordenada_x[i], coordenada_y[i], alpha);
      }

      if(estados[i] == 1){

        GameLib.setColor(Color.MAGENTA);
        GameLib.drawDiamond(coordenada_x[i], coordenada_y[i],raio);
      }
    }
  }

}
