public class GerenciamentoEstruturas {
	
	public static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	public static int findFreeIndex(int[] stateArray) {
		int i;
		for (i = 0; i < stateArray.length; i++)
			if (stateArray[i] == Main.INACTIVE) break;
		return i;
	}

	public static int[] findFreeIndex(int[] stateArray, int amount) {

		int i, k;
		int[] freeArray = { stateArray.length, stateArray.length,
				stateArray.length };

		for (i = 0, k = 0; i < stateArray.length && k < amount; i++) {

			if (stateArray[i] == Main.INACTIVE) {

				freeArray[k] = i;
				k++;
			}
		}

		return freeArray;
	}
}