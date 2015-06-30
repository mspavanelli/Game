abstract public class Elemento implements Drawable {

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	public int estado;
	public double coordenada_x;
	public double coordenada_y;
	
	public Elemento(int estado, double coordenada_x, double coordenada_y){
		this.estado = estado;
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
	}

}
