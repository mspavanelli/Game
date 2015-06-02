import java.util.Scanner;

class Animais {
	
	public static void main(String[] args) {
		int qtdeAves = 0;
		int qtdeCadelas = 0; 
		int mediaPesoCadelas = 0;
		int totalPesoCadelas = 0;
		int maiorPesoGatoMacho = 0;

		Scanner s = new Scanner( System.in );
		int n = s.nextInt(); 			// qtde de animais
		for ( int i = 0; i < n; i++ ) {
			int tipo = s.nextInt(); 	// (1 – ave; 2 – cão; 3 – gato)
			int peso = s.nextInt();
			int genero = s.nextInt();	// (1 – fêmea; 2 – macho)

			if ( tipo == 3 && genero == 2 )
				if ( peso > maiorPesoGatoMacho ) maiorPesoGatoMacho = peso;

			if ( tipo == 2 && genero == 2 ) {
				totalPesoCadelas += peso;
				qtdeCadelas++;
			}

			if ( tipo == 1 ) qtdeAves++;
		}
		mediaPesoCadelas = totalPesoCadelas / qtdeCadelas;

		System.out.println( maiorPesoGatoMacho );
		System.out.println( mediaPesoCadelas );
		System.out.println( qtdeAves );
	}
}