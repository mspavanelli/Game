import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
	public static void busyWait(long time) {

		while (System.currentTimeMillis() < time)
			Thread.yield();
	}

	public static int findFreeIndex(int[] stateArray) {
		int i;
		for (i = 0; i < stateArray.length; i++)
			if (stateArray[i] == 0) break;
		return i;
	}

	public static int[] findFreeIndex(int[] stateArray, int amount) {

		int i, k;
		int[] freeArray = { stateArray.length, stateArray.length,
				stateArray.length };

		for (i = 0, k = 0; i < stateArray.length && k < amount; i++) {

			if (stateArray[i] == 0) {

				freeArray[k] = i;
				k++;
			}
		}

		return freeArray;
	}

	/* Método principal */

	public static void main(String[] args) {

		boolean running = true;

		long delta;
		long currentTime = System.currentTimeMillis();
		//Instancição do player
		Player player = new Player(new PowerUp2(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07, 3 * Math.PI / 2, 0.0, 9.0, currentTime + 25000),
		new	PlayerProjectile(new int[10], new double[10], new double[10], new double[10], new double[10], 	new PowerUp1(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07,
		 3 * Math.PI / 2, 0.0, 9.0, currentTime + 20000)),1, GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0, 0, 0, currentTime);
		//Instancição da lista de inimigos do tipo1
		ListInimigo1 listaInimigo1 = new ListInimigo1(new LinkedList<Inimigo1>(), currentTime + 2000);
		//Instanciação da lista(unitária) de inimigos do tipo 2
		ListInimigo2 listaInimigo2 =new ListInimigo2(new LinkedList<Inimigo2>());
		//Adição de um inimigo do tipo 2 na lista
		listaInimigo2.addLista(currentTime,1);
		//Instanciação da lista(unitária) de inimigos do tipo 3
		ListInimigo3 listaInimigo3 = new ListInimigo3(new LinkedList<Inimigo3>());
		//Adição de um inimigo do tipo 3 na lista
		listaInimigo3.addLista(currentTime,1);

		// variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto * tipo 2, quanto tipo 3)
		InimigoProjectile inimigoProjectile = new InimigoProjectile(new int[200], new double[200], new double[200], new double[200], new double[200], 2.0);

		/* Plano de Fundo (próximo e distante) */
		ArrayList<PrimeiroPlano> primeiroPlano = new ArrayList<>();
		for (int i = 0; i < 20; i++)
			primeiroPlano.add(new PrimeiroPlano(0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.070, 0.0));
		ArrayList<SegundoPlano> segundoPlano = new ArrayList<>();
		for (int i = 0; i < 50; i++)
			segundoPlano.add(new SegundoPlano(0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.045, 0.0));
		//Instanciação da Interface do Jogo
		GameFacade facade=new GameFacade(listaInimigo1, listaInimigo2, listaInimigo3, player, inimigoProjectile, primeiroPlano, segundoPlano);

		/* iniciado interface gráfica */

		GameLib.initGraphics();

		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/

		while (running) {

			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			//chamada de metodo da interface que verifica as colisões
			facade.verificaColisoes(currentTime);
			//chamada do método que atualiza os estados dos elementos do jogo
			facade.atualizaEstado(currentTime, delta);
			//chamada do método que permite o controle do player
			running = facade.verificaControlePlayer( running,  currentTime, delta);
			//chamada do método que desenha a cena
			facade.desenhaCena(currentTime, delta);

			GameLib.display();

			// faz uma pausa de modo que cada execução do laço do main loop * demore aproximadamente 5 ms.

			busyWait(currentTime + 5);
		}

		System.exit(0);
	}
}
