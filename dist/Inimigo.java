public abstract class Inimigo {
	
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
	boolean verificaExplosao;
	boolean saiudaTela;

	public Inimigo(boolean verificaExplosao,int [] estado,double [] coordenada_x,double [] coordenada_y,double [] velocidade, double [] angulo,double [] velocidadeRotacao,double [] explosion_start,double [] explosion_end, double spawnX,int count,double raio) {
		this.verificaExplosao = verificaExplosao;
		this.estado = estado;
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.velocidade = velocidade;
		this.angulo = angulo;
		this.velocidadeRotacao = velocidadeRotacao;
		this.explosion_start = explosion_start;
		this.explosion_end = explosion_end;
		this.spawnX = spawnX;
		this.count = count;
		this.raio = raio;
	}
}