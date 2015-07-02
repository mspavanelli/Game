import java.awt.Color;

public class ProjetilPlayer{

  //ProjectileState activeState;
  //ProjectileState inactiveState;

  //ProjectileState state;

  public int [] estadosProjetil;
  public double [] coordenada_x;
  public double [] coordenada_y;
  public double [] velocidade_x;
  public double [] velocidade_y;

  public ProjetilPlayer(int [] estadosProjetil, double [] coordenada_x,
                          double [] coordenada_y, double [] velocidade_x,
                          double [] velocidade_y){
    this.estadosProjetil=estadosProjetil;
    this.coordenada_x=coordenada_x;
    this.coordenada_y=coordenada_y;
    this.velocidade_x=velocidade_x;
    this.velocidade_y=velocidade_y;

  }
  /*
  void setState(ProjectileState state){
    this.state=state;
  }

  public ProjectileState getInactiveState(){
    return this.inactiveState;
  }

  public ProjectileState getActiveState(){
    return this.activeState;
  }
  */
  public void desenhaProjetil(){
    for(int i = 0; i < estadosProjetil.length; i++){

      if(estadosProjetil[i] == 1){

        GameLib.setColor(Color.GREEN);
        GameLib.drawLine(coordenada_x[i], coordenada_y[i] - 5, coordenada_x[i], coordenada_y[i] + 5);
        GameLib.drawLine(coordenada_x[i] - 1,coordenada_y[i] - 3,coordenada_x[i] - 1,coordenada_y[i] + 3);
        GameLib.drawLine(coordenada_x[i] + 1,coordenada_y[i] - 3, coordenada_x[i] + 1, coordenada_y[i] + 3);
      }
    }
  }
}
