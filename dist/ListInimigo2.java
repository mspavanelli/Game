import java.util.*;
public class ListInimigo2{

  List<Inimigo2> lista;

  public ListInimigo2(List<Inimigo2> lista){
    this.lista=lista;
  }

  public void addLista(long currentTime, int quantidadeInimigo2){
    if(Math.random() > 0.5){
      for(int i=0; i<=quantidadeInimigo2;i++){
        lista.add(new Inimigo2(false,new int[10], new double[10],  new double[10], new double[10], new double[10],
  		    new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.20,0,12.0,(long) (currentTime + 7000 + Math.random() * 7000)));
        }
      } else {
        for(int i=0; i<=quantidadeInimigo2;i++){
          lista.add(new Inimigo2(false,new int[10], new double[10],  new double[10], new double[10], new double[10],
            new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.80,0,12.0,(long) (currentTime + 7000 + Math.random() * 7000)));
          }
      }
  }

  public List<Inimigo2> getListInimigo2(){
    return this.lista;
  }
}
