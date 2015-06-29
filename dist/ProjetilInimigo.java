public class ProjetilInimigo extends Projetil {
	
	double raio;
	
	public ProjetilInimigo(double raio){
	  this.raio=raio;
	  super(velocidade_x, velocidade_y);
 	}
}
