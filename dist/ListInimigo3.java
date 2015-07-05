import java.util.List;

public class ListInimigo3 {

	List<Inimigo3> lista;

	public ListInimigo3(List<Inimigo3> lista){
		this.lista=lista;
	}

	public void addLista(long currentTime, int quantidadeInimigo3){
	if(Math.random() > 0.5){
		for(int i=0; i<=quantidadeInimigo3;i++){
			lista.add(new Inimigo3(false,new int[10], new double[10],  new double[10], new double[10], new double[10],
			new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.40,0,20.0,currentTime + (long) (currentTime + 8000 - Math .random() * 8000)));
			}
		} else {
			for(int i=0; i<=quantidadeInimigo3;i++){
				lista.add(new Inimigo3(false,new int[10], new double[10],  new double[10], new double[10], new double[10],
				new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.60,0,20.0,(long) (currentTime + 8000 - Math .random() * 8000)));
				}
		}
	}

	public List<Inimigo3> getListInimigo3(){
		return this.lista;
	}
}
