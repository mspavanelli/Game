import java.awt.Color;

public abstract class PlayerState{

  public abstract void desenha( long currentTime );
  public abstract void controleMovimetoPlayer(long currentTime, long delta);
}
