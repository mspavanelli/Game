public class ProjetilPlayerActive extends ProjetilPlayerState {
	
	private ProjetilPlayerActive ppa;

	public ProjetilPlayerActive(ProjetilPlayerActive ppa){
		this.ppa=ppa;
	}

	public void desenha(){
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(coordenada_x, coordenada_y - 5, coordenada_x, coordenada_y + 5);
		GameLib.drawLine(coordenada_x - 1, coordenada_y - 3, coordenada_x - 1, coordenada_y + 3);
		GameLib.drawLine(coordenada_x + 1, coordenada_y - 3, coordenada_x + 1, coordenada_y + 3);
	}
}