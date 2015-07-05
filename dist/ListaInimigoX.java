import java.util.List;

public abstract class ListaInimigoX {
	
	List<Inimigo> lista;

	public ListaInimigoX( List<Inimigo> lista ) {
		this.lista = lista;
	}

	public List<Inimigo> getListInimigo(){
		return this.lista;
	}
}