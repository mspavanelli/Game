import java.util.Scanner;

public class Seno {

	public static void main(String[] args) {
		Scanner s = new Scanner( System.in );
		while ( s.hasNextDouble() )
			System.out.println( "\n" + calculaSeno( s.nextDouble() ) );
	}

	static double calculaSeno( double angulo ) {
		if ( angulo < 0 || angulo > 6.28 ) return -1;
		double seno = 0, parcela = 1, denominador = 1, sinal = 1;
		for ( int i = 0; parcela > Math.pow(10,-8); i++ ) {
			parcela = Math.pow(angulo, denominador) / fatorial(denominador);
			seno += parcela * sinal;
			denominador = denominador + 2;
			sinal = - sinal;
		}
		return seno;
	}
	static int fatorial( double n ) {
		return (int) n < 2 ? 1 : (int) n * fatorial( (int) n - 1 );
	}
}