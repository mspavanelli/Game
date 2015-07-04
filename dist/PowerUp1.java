import java.awt.Color;

public class PowerUp1 implements PlayerProjectilePowerUp{
  boolean estado;
  int estadop1;
  double coordenada_x;
  double coordenada_y;
  double velocidade;
  double angulo;
  double velocidadeRotacao;
  double raio;
  long nextPowerUp1;

  public PowerUp1(boolean estado,  int estadop1,double coordenada_x,
                  double coordenada_y, double velocidade, double angulo,
                  double velocidadeRotacao,double raio,long nextPowerUp1){
    this.estado=estado;
    this.estadop1=estadop1;
    this.coordenada_x=coordenada_x;
    this.coordenada_y=coordenada_y;
    this.velocidade=velocidade;
    this.angulo=angulo;
    this.velocidadeRotacao=velocidadeRotacao;
    this.raio=raio;
    this.nextPowerUp1=nextPowerUp1;
  }

  public void desenha(){
    if(estadop1 == 1 && estado==false){
      GameLib.setColor(Color.BLUE);
      GameLib.drawCircle(coordenada_x, coordenada_y, raio);
      GameLib.drawCircle(coordenada_x, coordenada_y, raio-0.05);
      GameLib.setColor(Color.GREEN);
      GameLib.drawCircle(coordenada_x, coordenada_y,0.5);
      GameLib.drawCircle(coordenada_x,coordenada_y,1.0);
      GameLib.drawCircle(coordenada_x,coordenada_y,1.5);
      GameLib.drawCircle(coordenada_x,coordenada_y,2.0);
      GameLib.drawCircle(coordenada_x,coordenada_y ,2.5);
      GameLib.drawCircle(coordenada_x,coordenada_y ,3.0);
      GameLib.drawCircle(coordenada_x,coordenada_y ,3.5);
      GameLib.drawCircle(coordenada_x,coordenada_y ,4.0);
      GameLib.drawCircle(coordenada_x,coordenada_y ,4.5);
      GameLib.drawCircle(coordenada_x,coordenada_y ,4.7);

    }
  }
}
