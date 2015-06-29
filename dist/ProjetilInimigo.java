public class ProjetilInimigo extends Projetil {
	
	double raio;
	
	public ProjetilInimigo(double raio){
	  this.raio=raio;
	  super(velocidade_x);
	  super(velocidade_y);
	  super(coordenada_x);
	  super(coordenada_y);
 	}
}
