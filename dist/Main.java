import java.util.*;
import java.awt.Color;

public class Main {

	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */

	public static void busyWait(long time){

		while(System.currentTimeMillis() < time) Thread.yield();
	}

	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */

	public static int findFreeIndex(int [] stateArray){

		int i;

		for(i = 0; i < stateArray.length; i++){

			if(stateArray[i] == INACTIVE) break;
		}

		return i;
	}

	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */

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

	public static void colisaoProjetilPlayerComInimigos( Player player, Inimigo inimigoX, long currentTime, int k ) {
		for(int i = 0; i < inimigoX.estados.length; i++){
			if(inimigoX.estados[i] == ACTIVE){

				double dx = inimigoX.coordenada_x[i] - player.projectile.coordenada_x[k];
				double dy = inimigoX.coordenada_y[i] - player.projectile.coordenada_y[k];
				double dist = Math.sqrt(dx * dx + dy * dy);			

				if(dist < inimigoX.raio){

					inimigoX.estados[i] = EXPLODING;
					inimigoX.explosion_start[i] = currentTime;
					inimigoX.explosion_end[i] = currentTime + 500;
				}
			}
		}
	}

	public static void colisaoPlayerComInimigos( Player player, Inimigo inimigoX, long currentTime ) {
		for(int i = 0; i < inimigoX.estados.length; i++){

			double dx = inimigoX.coordenada_x[i] - player.coordenada_x;
			double dy = inimigoX.coordenada_y[i] - player.coordenada_y;
			double dist = Math.sqrt(dx * dx + dy * dy);

			if(dist < (player.raio + inimigoX.raio) * 0.8){

				player.estado = EXPLODING;
				player.explosion_start = currentTime;
				player.explosion_end = currentTime + 2000;
			}
		}
	}

	/* Método principal */

	public static void main(String [] args){

		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();

		Player player = new Player(new PlayerProjectile(new int[10], new double[10], new double[10], new double[10], new double[10]), ACTIVE,  GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0 , 0, 0, currentTime);

		Inimigo1 inimigo1 = new Inimigo1(new int[10],new double[10], new double[10],new double[10],new double[10],new double[10], new double[10],new double[10],new long[10], 9.0 ,currentTime + 2000);
		Inimigo2 inimigo2 = new Inimigo2(new int[10], new double[10],  new double[10], new double[10], new double[10], new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.20,0,12.0,currentTime + 7000);
		Inimigo3 inimigo3 = new Inimigo3(new int[10], new double[10],  new double[10], new double[10], new double[10], new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.20,0,20.0,currentTime + 10000);

		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2, quanto tipo 3) */
		InimigoProjectile inimigoProjectile = new InimigoProjectile(new int[200],new double[200],new double[200],new double[200],new double[200],2.0);
		
		/* estrelas que formam o fundo próximo e distante */

		ArrayList<PrimeiroPlano> primeiroPlano = new ArrayList<>();
		for ( int i = 0; i < 20; i++ )
			primeiroPlano.add( new PrimeiroPlano( 0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.070, 0.0) );

		ArrayList<SegundoPlano> segundoPlano = new ArrayList<>();
		for ( int i = 0; i < 50; i++ )
			segundoPlano.add( new SegundoPlano(0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.045, 0.0));

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

		while(running){

			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */

			delta = System.currentTimeMillis() - currentTime;

			/* Já a variável "currentTime" nos dá o timestamp atual.  */

			currentTime = System.currentTimeMillis();

			/***************************/
			/* Verificação de colisões */
			/***************************/

			if(player.estado == ACTIVE) {

				/* colisões player - projeteis (inimigo) */

				for(int i = 0; i < inimigoProjectile.estados.length; i++){

					double dx = inimigoProjectile.coordenada_x[i] - player.coordenada_x;
					double dy = inimigoProjectile.coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.raio + inimigoProjectile.raio) * 0.8){

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					}
				}

				/* colisões player - inimigos */

				colisaoPlayerComInimigos( player, inimigo1, currentTime );
				colisaoPlayerComInimigos( player, inimigo2, currentTime );
				colisaoPlayerComInimigos( player, inimigo3, currentTime );
			}

			/* colisões projeteis (player) - inimigos */

			for(int k = 0; k < player.projectile.estadosProjetil.length; k++) {
				colisaoProjetilPlayerComInimigos( player, inimigo1, currentTime, k );
				colisaoProjetilPlayerComInimigos( player, inimigo2, currentTime, k );
				colisaoProjetilPlayerComInimigos( player, inimigo3, currentTime, k );
			}

			/***************************/
			/* Atualizações de estados */
			/***************************/

			/* projeteis (player) */

			for(int i = 0; i < player.projectile.estadosProjetil.length; i++){

				if(player.projectile.estadosProjetil[i] == ACTIVE){

					/* verificando se projétil saiu da tela */
					if(player.projectile.coordenada_y[i] < 0) {

						player.projectile.estadosProjetil[i] = INACTIVE;
					}
					else {

						player.projectile.coordenada_x[i] += player.projectile.velocidade_x[i] * delta;
						player.projectile.coordenada_y[i] += player.projectile.velocidade_y[i] * delta;
					}
				}
			}

			/* projeteis (inimigos) */

			for(int i = 0; i < inimigoProjectile.estados.length; i++){

				if(inimigoProjectile.estados[i] == ACTIVE){

					/* verificando se projétil saiu da tela */
					if(inimigoProjectile.coordenada_y[i] > GameLib.HEIGHT) {

						inimigoProjectile.estados[i] = INACTIVE;
					}
					else {

						inimigoProjectile.coordenada_x[i] += inimigoProjectile.velocidade_x[i] * delta;
						inimigoProjectile.coordenada_y[i] += inimigoProjectile.velocidade_y[i] * delta;
					}
				}
			}

			/* inimigos tipo 1 */

			for(int i = 0; i < inimigo1.estados.length; i++){

				if(inimigo1.estados[i] == EXPLODING){

					if(currentTime > inimigo1.explosion_end[i]){

						inimigo1.estados[i] = INACTIVE;
					}
				}

				if(inimigo1.estados[i] == ACTIVE){

					/* verificando se inimigo saiu da tela */
					if(inimigo1.coordenada_y[i] > GameLib.HEIGHT + 10) {

						inimigo1.estados[i] = INACTIVE;
					}
					else {

						inimigo1.coordenada_x[i] += inimigo1.velocidade[i] * Math.cos(inimigo1.angulo[i]) * delta;
						inimigo1.coordenada_y[i] += inimigo1.velocidade[i] * Math.sin(inimigo1.angulo[i]) * delta * (-1.0);
						inimigo1.angulo[i] += inimigo1.velocidadeRotacao[i] * delta;

						if(currentTime > inimigo1.nextShoot[i] && inimigo1.coordenada_y[i] < player.coordenada_y){

							int free = findFreeIndex(inimigoProjectile.estados);

							if(free < inimigoProjectile.estados.length){

								inimigoProjectile.coordenada_x[free] = inimigo1.coordenada_x[i];
								inimigoProjectile.coordenada_y[free] = inimigo1.coordenada_y[i];
								inimigoProjectile.velocidade_x[free] = Math.cos(inimigo1.angulo[i]) * 0.45;
								inimigoProjectile.velocidade_y[free] = Math.sin(inimigo1.angulo[i]) * 0.45 * (-1.0);
								inimigoProjectile.estados[free] = 1;

								inimigo1.nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
							}
						}
					}
				}
			}

			/* inimigos tipo 2 */

			for(int i = 0; i < inimigo2.estados.length; i++){

				if(inimigo2.estados[i] == EXPLODING){

					if(currentTime > inimigo2.explosion_end[i]){

						inimigo2.estados[i] = INACTIVE;
					}
				}

				if(inimigo2.estados[i] == ACTIVE){

					/* verificando se inimigo saiu da tela */
					if(	inimigo2.coordenada_x[i] < -10 || inimigo2.coordenada_x[i] > GameLib.WIDTH + 10 ) {

						inimigo2.estados[i] = INACTIVE;
					}
					else {

						boolean shootNow = false;
						double previousY = inimigo2.coordenada_y[i];

						inimigo2.coordenada_x[i] += inimigo2.velocidade[i] * Math.cos(inimigo2.angulo[i]) * delta;
						inimigo2.coordenada_y[i] += inimigo2.velocidade[i] * Math.sin(inimigo2.angulo[i]) * delta * (-1.0);
						inimigo2.angulo[i] += inimigo2.velocidadeRotacao[i] * delta;

						double threshold = GameLib.HEIGHT * 0.30;

						if(previousY < threshold && inimigo2.coordenada_y[i] >= threshold) {

							if(inimigo2.coordenada_x[i] < GameLib.WIDTH / 2) inimigo2.velocidadeRotacao[i] = 0.003;
							else inimigo2.velocidadeRotacao[i] = -0.003;
						}

						if(inimigo2.velocidadeRotacao[i] > 0 && Math.abs(inimigo2.angulo[i] - 3 * Math.PI) < 0.05){

							inimigo2.velocidadeRotacao[i] = 0.0;
							inimigo2.angulo[i] = 3 * Math.PI;
							shootNow = true;
						}

						if(inimigo2.velocidadeRotacao[i] < 0 && Math.abs(inimigo2.angulo[i]) < 0.05){

							inimigo2.velocidadeRotacao[i] = 0.0;
							inimigo2.angulo[i] = 0.0;
							shootNow = true;
						}

						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							int [] freeArray = findFreeIndex(inimigoProjectile.estados, angles.length);

							for(int k = 0; k < freeArray.length; k++){

								int free = freeArray[k];

								if(free < inimigoProjectile.estados.length){

									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									inimigoProjectile.coordenada_x[free] = inimigo2.coordenada_x[i];
									inimigoProjectile.coordenada_y[free] = inimigo2.coordenada_y[i];
									inimigoProjectile.velocidade_x[free] = vx * 0.30;
									inimigoProjectile.velocidade_y[free] = vy * 0.30;
									inimigoProjectile.estados[free] = 1;
								}
							}
						}
					}
				}
			}

			/* inimigos tipo 3*/
			for(int i = 0; i < inimigo3.estados.length; i++){

				if(inimigo3.estados[i] == EXPLODING){

					if(currentTime > inimigo3.explosion_end[i]){

						inimigo3.estados[i] = INACTIVE;
					}
				}

				if(inimigo3.estados[i] == ACTIVE){

					/* verificando se inimigo saiu da tela */
					if(	inimigo3.coordenada_x[i] < -10 || inimigo3.coordenada_x[i] > GameLib.WIDTH + 10 ) {

						inimigo3.estados[i] = INACTIVE;
					}
					else {

						boolean shootNow = false;
						double previousY = inimigo3.coordenada_y[i];

						inimigo3.coordenada_x[i] += inimigo3.velocidade[i] * Math.cos(inimigo3.angulo[i]) * delta;
						inimigo3.coordenada_y[i] += inimigo3.velocidade[i] * Math.sin(inimigo3.angulo[i]) * delta * (-1.0);
						inimigo3.angulo[i] += inimigo3.velocidadeRotacao[i] * delta;

						double threshold = GameLib.HEIGHT * 0.30;

						if(previousY < threshold && inimigo3.coordenada_y[i] >= threshold) {

							if(inimigo3.coordenada_x[i] < GameLib.WIDTH / 2) inimigo3.velocidadeRotacao[i] = 0.01;
							else inimigo3.velocidadeRotacao[i] = -0.01;
						}

						if(inimigo3.velocidadeRotacao[i] > 0 && Math.abs(inimigo3.angulo[i] - 3 * Math.PI) < 0.05){

							inimigo3.velocidadeRotacao[i] = 0.0;
							inimigo3.angulo[i] = 3 * Math.PI;
							shootNow = true;
						}

						if(inimigo3.velocidadeRotacao[i] < 0 && Math.abs(inimigo3.angulo[i]) < 0.05){

							inimigo3.velocidadeRotacao[i] = 0.0;
							inimigo3.angulo[i] = 0.0;
							shootNow = true;
						}

						if(shootNow){

							double [] angles = { Math.PI/2, Math.PI/2, Math.PI/2 };
							int [] freeArray = findFreeIndex(inimigoProjectile.estados, angles.length);

							for(int k = 0; k < freeArray.length; k++){

								int free = freeArray[k];

								if(free < inimigoProjectile.estados.length){

									double a = angles[k] + Math.random() * Math.PI/4 - Math.PI/8;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									inimigoProjectile.coordenada_x[free] = inimigo3.coordenada_x[i];
									inimigoProjectile.coordenada_y[free] = inimigo3.coordenada_y[i];
									inimigoProjectile.velocidade_x[free] = vx * 0.05;
									inimigoProjectile.velocidade_y[free] = vy * 0.05;
									inimigoProjectile.estados[free] = 1;
								}
							}
						}
					}
				}
			}

			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */

			if(currentTime > inimigo1.nextEnemy1){

				int free = findFreeIndex(inimigo1.estados);

				if(free < inimigo1.estados.length){

					inimigo1.coordenada_x[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
					inimigo1.coordenada_y[free] = -10.0;
					inimigo1.velocidade[free] = 0.20 + Math.random() * 0.15;
					inimigo1.angulo[free] = 3 * Math.PI / 2;
					inimigo1.velocidadeRotacao[free] = 0.0;
					inimigo1.estados[free] = ACTIVE;
					inimigo1.nextShoot[free] = currentTime + 500;
					inimigo1.nextEnemy1 = currentTime + 500;
				}
			}

			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */

			if(currentTime > inimigo2.nextEnemy2){

				int free = findFreeIndex(inimigo2.estados);

				if(free < inimigo2.estados.length){

					inimigo2.coordenada_x[free] = inimigo2.spawnX;
					inimigo2.coordenada_y[free] = -10.0;
					inimigo2.velocidade[free] = 0.42;
					inimigo2.angulo[free] = (3 * Math.PI) / 2;
					inimigo2.velocidadeRotacao[free] = 0.0;
					inimigo2.estados[free] = ACTIVE;

					inimigo2.count++;

					if(inimigo2.count < 10){

						inimigo2.nextEnemy2 = currentTime + 120;
					}
					else {

						inimigo2.count = 0;
						inimigo2.spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
						inimigo2.nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
					}
				}
			}

			/* verificando se novos inimigos (tipo 3) devem ser "lançados" */

			if(currentTime > inimigo3.nextEnemy3){

				int free = findFreeIndex(inimigo3.estados);

				if(free < inimigo3.estados.length){

					inimigo3.coordenada_x[free] = inimigo3.spawnX;
					inimigo3.coordenada_y[free] = -10.0;
					inimigo3.velocidade[free] = 0.42;
					inimigo3.angulo[free] = (3 * Math.PI) / 2;
					inimigo3.velocidadeRotacao[free] = 0.0;
					inimigo3.estados[free] = ACTIVE;

					inimigo3.count++;

					if(inimigo3.count < 10){

						inimigo3.nextEnemy3 = currentTime + 120;
					}
					else {

						inimigo3.count = 0;
						inimigo3.spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.4 : GameLib.WIDTH * 0.6;
						inimigo3.nextEnemy3 = (long) (currentTime + 3000 + Math.random() * 3000);
					}
				}
			}

			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			if(player.estado == EXPLODING){

				if(currentTime > player.explosion_end){

					player.estado = ACTIVE;
				}
			}

/*
			APLICANDO PADRAO STATE

			PlayerExploding.java

			void tempoDeExplosao( currentTime ) {
				if ( currentTIme > player.explosion_end )
					estado = new PlayerActiveState(player);
			}

*/
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/

			player.controleMovimetoPlayer( currentTime, delta );
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/*******************/
			/* Desenho da cena */
			/*******************/

			PrimeiroPlano.count += PrimeiroPlano.speed * delta;
			for ( int i = 0; i < 20; i++ )
				primeiroPlano.get(i).desenha( currentTime );
			
			SegundoPlano.count += SegundoPlano.speed * delta;
			for ( int i = 0; i < 50; i++ )
				segundoPlano.get(i).desenha( currentTime );

			player.desenha(currentTime);
			player.projectile.desenha();

			inimigo1.desenha(currentTime);
			inimigo2.desenha(currentTime);
			inimigo3.desenha(currentTime);
			inimigoProjectile.desenha();

			GameLib.display();

			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			busyWait(currentTime + 5);
		}

		System.exit(0);
	}
}
