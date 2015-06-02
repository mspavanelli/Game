import java.util.*;

public class Frequencia {

	public static void main(String[] args) {
		Scanner s = new Scanner( System.in );
		frequencia( s.nextLine() );
	}
	public static void frequencia( String palavra ) {
		int maiorFrequencia = 0;
		String letraDeMaiorFrequencia = new String();

		String[] letras = palavra.toLowerCase().split( "" );
		Arrays.sort( letras );

		boolean ehIgualAoAnterior = false;
		int subFrequencia = 0;
		String memoria = letras[0];
		for ( int i = 1; i < letras.length; i++ ) {
			if ( letras[i] == memoria ) {	// caso seja mantida a sequencia
				subFrequencia++;

			}
			memoria = letras[i];
			subFrequencia = 1;
		}
		System.out.println( letraDeMaiorFrequencia );
	}
}