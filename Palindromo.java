import java.util.Scanner;

class Palindromo {
	
	public static void main(String[] args) {
		System.out.println(ehPalindromo( new Scanner( System.in ).nextLine() ) 
			? "É UM PALÍNDROMO" : "NÃO E UM PALÍNDROMO" );
	}

	public static boolean ehPalindromo( String palavra ) {
		for ( int i = 0; i < palavra.length() / 2 ; i++ ) {
			if ( palavra.charAt(i) != palavra.charAt(palavra.length() - 1 - i))
				return false;
		}		
		return true;
	}
}