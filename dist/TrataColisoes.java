/* Fachada para o tratamento de colisões */

public class TrataColisoes {
	
	private Player player = null;
	private Inimigo inimigos1 = null;
	private Inimigo inimigos2 = null;
	private Inimigo inimigos3 = null;

	public TrataColisoes( Player p, Inimigo i1, Inimigo i2, Inimigo i3 ) {
		player = p;
		inimigos1 = i1;
		inimigos2 = i2;
		inimigos3 = i3;
	}


}