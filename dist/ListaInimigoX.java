import java.util.List;

public abstract class ListaInimigoX <T extends Inimigo> {
	
	List<T> lista;

	public abstract void addLista( long currentTime, int qtdeInimigos );

	public List<T> getListInimigo(){
		return this.lista;
	}
}