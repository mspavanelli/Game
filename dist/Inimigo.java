public abstract class Inimigo {

	public int [] estados;
	public double [] coordenada_x;
	public double [] coordenada_y;
	public double [] velocidade;
	public double [] angulo;
	public double [] velocidadeRotacao;
	public double [] explosion_start;
	public double [] explosion_end;
	public int count;
	public double raio;

	public Inimigo( int [] estados ) {
		this.estados = estados;
	}
}
