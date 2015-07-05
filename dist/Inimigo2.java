import java.awt.Color;
public class Inimigo2{

  boolean verificaExplosao;
  boolean saiudaTela;

  public int [] estado;
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
  public long nextEnemy;

  public Inimigo2(boolean verificaExplosao,int [] estado,double [] coordenada_x,double [] coordenada_y,double [] velocidade,
    double [] angulo,double [] velocidadeRotacao,double [] explosion_start,double [] explosion_end,
    double spawnX,int count,double raio,long nextEnemy){
    this.verificaExplosao=verificaExplosao;
    this.estado=estado;
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
    this.nextEnemy = nextEnemy;
  }

  public void desenha(long currentTime){
    for(int i = 0; i < estado.length; i++){

      if(estado[i] == 2){

        double alpha = (currentTime - explosion_start[i]) / (explosion_end[i] - explosion_start[i]);
        GameLib.drawExplosion(coordenada_x[i], coordenada_y[i], alpha);
      }

      if(estado[i] == 1){

        GameLib.setColor(Color.MAGENTA);
        GameLib.drawDiamond(coordenada_x[i], coordenada_y[i],raio);
      }
    }
  }
  public void verificaExplosao(){
    int  numerodeexplosoes = 0;
    for(int i=0; i<estado.length; i++){
      if(estado[i]!=1){
        numerodeexplosoes++;
      }
    }
    //System.out.println(numerodeexplosoes);
    if(numerodeexplosoes==10) verificaExplosao = true;
  }

  public void verificaSaidaTela(){
    int verificasaida=0;
    for(int i=0; i<estado.length; i++){
      if(coordenada_x[i] < -10 || coordenada_x[i] > GameLib.WIDTH + 10){
        verificasaida++;
      }
    //  System.out.println(verificasaida);
    if(verificasaida==10) saiudaTela=true;
    }
  }

}
