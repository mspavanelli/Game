import java.awt.Color;

public class Inimigo1 implements InimigoUnico{

  private int estado;
  private double coordenada_x;
  private double coordenada_y;
  private double velocidade;
  private double angulo;
  private double velocidadeRotacao;
  private double explosion_start;
  private double explosion_end;
  private long nextShoot;
  private double raio;

  //Inimigo1 inimigo1 = new Inimigo1(new int[10],new double[10], new double[10],new double[10],new double[10],
    //                          new double[10], new double[10],new double[10],new long[10], 9.0 ,currentTime + 2000);

  public Inimigo1(int estado,double coordenada_x,double coordenada_y,
    double velocidade, double angulo, double velocidadeRotacao, double explosion_start,
    double explosion_end, long nextShoot, double raio){

    this.estado=estado;
    this.coordenada_x=coordenada_x;
    this.coordenada_y=coordenada_y;
    this.velocidade=velocidade;
    this.angulo=angulo;
    this.velocidadeRotacao=velocidadeRotacao;
    this.explosion_start=explosion_start;
    this.explosion_end=explosion_end;
    this.nextShoot=nextShoot;
    this.raio=raio;


  }

  public void desenha(long currentTime){
      if(estado == 2){
        double alpha = (currentTime - explosion_start) / (explosion_end - explosion_start);
        GameLib.drawExplosion(coordenada_x, coordenada_y, alpha);
      }
      if(estado == 1){
        GameLib.setColor(Color.CYAN);
        GameLib.drawCircle(coordenada_x, coordenada_y, raio);
      }
  }
  //steres
    public void setEstado(int e){
      this.estado=e;
    }
    public void setCoordenadax(double cx){
      this.coordenada_x=cx;
    }
    public void setCoordenaday(double cy){
      this.coordenada_y=cy;
    }
    public void setVelocidade(double v){
      this.velocidade=v;
    }
    public void setAngulo(double a){
      this.angulo=a;
    }
    public void setVelocidadeR(double vr){
      this.velocidadeRotacao=vr;
    }
    public void setExplosionS(double es){
      this.explosion_start=es;
    }
    public void setExplosionE(double ee){
      this.explosion_end=ee;
    }
    public void setnextShot(long ns){
      this.nextShoot=ns;
    }
    public void setRaio(double r){
      this.raio=r;
    }

//geteres
    public int getEstado(){
      return estado;
    }

    public double getCoordenadax(){
      return coordenada_x;
    }
    public double getCoordenaday(){
      return coordenada_y;
    }
    public double getVelocidade(){
      return velocidade;
    }
    public double getAngulo(){
      return angulo;
    }
    public double getVelocidadeR(){
      return velocidadeRotacao;
    }
    public double getExplosionS(){
      return explosion_start;
    }
    public double getExplosionE(){
      return explosion_end;
    }
    public long getnextShot(){
      return nextShoot;
    }
    public double getRaio(){
      return raio;
    }

}
