import java.util.*;
public class ListInimigo1{

  List<Inimigo1> lista;
  long nextEnemy1;

  public ListInimigo1(List<Inimigo1> lista, long nextEnemy1){
    this.lista=lista;
    this.nextEnemy1=nextEnemy1;
  }

  public void addLista(long currentTime, int quantidadeinimigo1){

  for(int i=0; i<=quantidadeinimigo1;i++){
    lista.add(new Inimigo1(1,Math.random() * (GameLib.WIDTH - 20.0) + 10.0,
    -10.0, 0.20 + Math.random() * 0.15, 3 * Math.PI / 2, 0.0, currentTime,
    currentTime + 500, currentTime + 500, 9.0));
    }
  }

  public List<Inimigo1> getListInimigo1(){
    return this.lista;
  }
  public void setnextEnemy1(long ne){
    this.nextEnemy1=ne;
  }
  public long getnextEnemy1(){
    return nextEnemy1;
  }
}
