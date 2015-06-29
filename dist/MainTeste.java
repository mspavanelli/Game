import java.awt.Color;

public class Main {

	public static void busyWait(long time) {
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	public static int findFreeIndex(int [] stateArray){	
		int i;	
		for(i = 0; i < stateArray.length; i++)	
			if(stateArray[i] == INACTIVE) break;
		return i;
	}

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}

	public static void main(String[] args) {
		
		boolean running = true;		
		long delta;
		long currentTime = System.currentTimeMillis();

		Elemento player = new Player(Elemento.ACTIVE, GameLib.WIDTH / 2, GameLib.HEIGHT * 0.9, 0.25, 0.25, 12, 0, 0, currentTime);

		LinkedList<Inimigos> inimigos;
	}
}