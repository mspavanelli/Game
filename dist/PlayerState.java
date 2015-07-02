import java.awt.Color;

public abstract class PlayerState{

  public abstract void colisaoInimigos();
  public abstract void colisaoPlayerProjetilInimigo();
  public abstract void desenhaPlayer( long currentTime );
}
