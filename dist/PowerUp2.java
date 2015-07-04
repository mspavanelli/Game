import java.awt.Color;

public class PowerUp2 implements PlayerPowerUp{
  boolean estado2;
  int estadop2;
  double coordenada_x;
  double coordenada_y;
  double velocidade;
  double angulo;
  double velocidadeRotacao;
  double raio;
  long nextPowerUp2;

  public PowerUp2(boolean estado2, int estadop2,double coordenada_x,
                  double coordenada_y, double velocidade, double angulo,
                  double velocidadeRotacao,double raio,long nextPowerUp2){
    this.estado2=estado2;
    this.estadop2=estadop2;
    this.coordenada_x=coordenada_x;
    this.coordenada_y=coordenada_y;
    this.velocidade=velocidade;
    this.angulo=angulo;
    this.velocidadeRotacao=velocidadeRotacao;
    this.raio=raio;
    this.nextPowerUp2=nextPowerUp2;
  }

  public void desenha(){
    if(estadop2 == 1 && estado2==false){
      GameLib.setColor(Color.RED);
      GameLib.drawCircle(coordenada_x, coordenada_y, raio);
      GameLib.drawCircle(coordenada_x, coordenada_y, raio-0.05);
      GameLib.setColor(Color.BLUE);
      GameLib.drawPlayer(coordenada_x, coordenada_y, raio-4);

    }
  }
}
