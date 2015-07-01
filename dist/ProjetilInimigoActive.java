public class StateInimigoActive extends ProjetilInimigoState {
	
	private StateInimigoActive sia;

	public StateActive(StateInimigoActive sia){
		this.sa=sa;
	}

	public void desenha(){
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(coordenada_x, coordenada_y, raio);
	}
}