public class InimigoDois extends Inimigo {
	
	int contagem_inimigos;
	double coordenada_x_proximo;
	
	public InimigoDois(int contagem_inimigos, coordenada_x_proximo){
		this.contagem_inimigos=contagem_inimigos;
		this.coordenada_x_proximo=coordenada_x_proximo;
		super(velocidade, angulo, velocidade_rotacao, next_enemy);
	}
}
