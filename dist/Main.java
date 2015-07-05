import java.awt.Color;
import java.util.*;

public class Main {

	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	public static boolean running;


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

	/* Método principal */

	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */

		long delta;
		long currentTime = System.currentTimeMillis();

		/* variáveis dos projéteis disparados pelo player */
		/* variáveis do player */
		Player player = new Player(
		new PowerUp2(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0,-10.0,0.10 + Math.random() * 0.10,3 * Math.PI / 2,0.0,
		9.0,currentTime+25000), new PlayerProjectile(new int[10], new double[10], new double[10], new double[10], new double[10],new PowerUp1(
		false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0,-10.0,0.10 + Math.random() * 0.10,3 * Math.PI / 2,0.0,
		9.0,currentTime+20000)), ACTIVE,  GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0 , 0, 0, currentTime);

		/* variáveis dos inimigos tipo 1 */

		ListInimigo1 listaInimigo1 = new ListInimigo1(new LinkedList<Inimigo1>(), currentTime + 2000);

		/* variáveis dos inimigos tipo 2 */
		
		Inimigo2 inimigo2 = new Inimigo2(new int[10], new double[10],  new double[10], new double[10], new double[10],
		new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.20,0,12.0,currentTime + 7000);

		/* variáveis dos inimigos tipo 3 */

		Inimigo3 inimigo3 = new Inimigo3(new int[10], new double[10],  new double[10], new double[10], new double[10],
		new double[10],new double[10],  new double[10],GameLib.WIDTH * 0.20,0,20.0,currentTime + 10000);

		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2, quanto tipo 3) */
		InimigoProjectile inimigoProjectile = new InimigoProjectile(new int[200],new double[200],new double[200],new double[200],new double[200],2.0);



		ArrayList<PrimeiroPlano> primeiroPlano = new ArrayList<>();
		for ( int i = 0; i < 20; i++ )
			primeiroPlano.add( new PrimeiroPlano( 0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.070, 0.0) );
		
		ArrayList<SegundoPlano> segundoPlano = new ArrayList<>();
		for ( int i = 0; i < 50; i++ )
			segundoPlano.add( new SegundoPlano(0, Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT, 0.045, 0.0));

		/* inicializações */

		for(int i = 0; i < player.projectile.estadosProjetil.length; i++) player.projectile.estadosProjetil[i] = INACTIVE;
		for(int i = 0; i < inimigoProjectile.estados.length; i++) inimigoProjectile.estados[i] = INACTIVE;
	//	for(int i = 0; i < inimigo2.estados.length; i++) inimigo2.estados[i] = INACTIVE;
	//		for(int i = 0; i < inimigo3.estados.length; i++) inimigo3.estados[i] = INACTIVE;





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

			if(player.estado == ACTIVE){

				/* colisões player, player com escudo - projeteis (inimigo) */

				for(int i = 0; i < inimigoProjectile.estados.length; i++){

					double dx = inimigoProjectile.coordenada_x[i] - player.coordenada_x;
					double dy = inimigoProjectile.coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.raio + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2==false){

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					}
					 else if (dist < (player.raio+15.1 + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2==true){
						player.estado = ACTIVE;
						player.powerUp2.estado2=false;
					}
				}

				/* colisões player, player com escudo(powerUp2) - inimigos */
				for(int i =0;i<listaInimigo1.lista.size();i++){

					double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.coordenada_x;
					double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.raio + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8  && player.powerUp2.estado2==false){

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					} else if (dist < (player.raio+15.1 + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8 && player.powerUp2.estado2==true){
						player.estado = ACTIVE;
						player.powerUp2.estado2=false;
					}
				}
				/* colisão player - powerUp1*/

					double dxpu1 = player.projectile.powerUp1.coordenada_x - player.coordenada_x;
					double dypu1 = player.projectile.powerUp1.coordenada_y - player.coordenada_y;
					double distpu1 = Math.sqrt(dxpu1 * dxpu1 + dypu1 * dypu1);

					if(distpu1 < (player.raio + player.projectile.powerUp1.raio) * 0.8){

						player.projectile.powerUp1.estadop1=0;
						player.projectile.powerUp1.estado=true;
			      player.projectile.powerUp1.coordenada_y =	GameLib.HEIGHT;
					}
					/* colisão player - powerUp2*/

					double dxpu2 = player.powerUp2.coordenada_x - player.coordenada_x;
					double dypu2 = player.powerUp2.coordenada_y - player.coordenada_y;
					double distpu2 = Math.sqrt(dxpu2 * dxpu2 + dypu2 * dypu2);

					if(distpu2 < (player.raio + player.powerUp2.raio) * 0.8){

						player.powerUp2.estadop2=0;
						player.powerUp2.estado2=true;
			      player.powerUp2.coordenada_y =	GameLib.HEIGHT;
					}

				for(int i = 0; i < inimigo2.estados.length; i++){

					double dx = inimigo2.coordenada_x[i] - player.coordenada_x;
					double dy = inimigo2.coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.raio + inimigo2.raio) * 0.8&&player.powerUp2.estado2==false){

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;

					} else if (dist < (player.raio+15.1 + inimigo2.raio) * 0.8 && player.powerUp2.estado2==true){
							player.estado = ACTIVE;
							player.powerUp2.estado2=false;
					}
				}



			for(int i = 0; i < inimigo3.estados.length; i++){

				double dx = inimigo3.coordenada_x[i] - player.coordenada_x;
				double dy = inimigo3.coordenada_y[i] - player.coordenada_y;
				double dist = Math.sqrt(dx * dx + dy * dy);

				if(dist < (player.raio + inimigo3.raio) * 0.8){

					player.estado = EXPLODING;
					player.explosion_start = currentTime;
					player.explosion_end = currentTime + 2000;

				} else if (dist < (player.raio+15.1 + inimigo3.raio) * 0.8 && player.powerUp2.estado2==true){
						player.estado = ACTIVE;
						player.powerUp2.estado2=false;
				}

			}
		}

			/* colisões projeteis (player) - inimigos */
			if(player.projectile.powerUp1.estado==false){
				for(int k = 0; k < player.projectile.estadosProjetil.length; k++){

					for(int i =0;i<listaInimigo1.lista.size();i++){

						if(listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE){

							double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
							double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if(dist < listaInimigo1.getListInimigo1().get(i).getRaio()){

								listaInimigo1.getListInimigo1().get(i).setEstado(EXPLODING);
								listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
								listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime+500);
							}
						}
					}


					for(int i = 0; i < inimigo2.estados.length; i++){

						if(inimigo2.estados[i] == ACTIVE){

							double dx = inimigo2.coordenada_x[i] - player.projectile.coordenada_x[k];
							double dy = inimigo2.coordenada_y[i] - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if(dist < inimigo2.raio){

								inimigo2.estados[i] = EXPLODING;
								inimigo2.explosion_start[i] = currentTime;
								inimigo2.explosion_end[i] = currentTime + 500;
							}
						}
					}


					for(int i = 0; i < inimigo3.estados.length; i++){

						if(inimigo3.estados[i] == ACTIVE){

							double dx = inimigo3.coordenada_x[i] - player.projectile.coordenada_x[k];
							double dy = inimigo3.coordenada_y[i] - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if(dist < inimigo3.raio){

								inimigo3.estados[i] = EXPLODING;
								inimigo3.explosion_start[i] = currentTime;
								inimigo3.explosion_end[i] = currentTime + 500;
							}
						}
					}
				}
		} else if(player.projectile.powerUp1.estado==true){

					for(int k = 0; k < player.projectile.estadosProjetil.length; k++){

						for(int i =0;i<listaInimigo1.lista.size();i++){

							if(listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE){

								double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
								double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
								double dist = Math.sqrt(dx * dx + dy * dy);

								if(dist < player.projectile.powerUp1.raio-2 + listaInimigo1.getListInimigo1().get(i).getRaio()*0.8){

									listaInimigo1.getListInimigo1().get(i).setEstado(EXPLODING);
									listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
									listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime+500);
								}
							}
						}


						for(int i = 0; i < inimigo2.estados.length; i++){

							if(inimigo2.estados[i] == ACTIVE){

								double dx = inimigo2.coordenada_x[i] - player.projectile.coordenada_x[k];
								double dy = inimigo2.coordenada_y[i] - player.projectile.coordenada_y[k];
								double dist = Math.sqrt(dx * dx + dy * dy);

								if(dist < (player.projectile.powerUp1.raio-2 + inimigo2.raio) * 0.8){

									inimigo2.estados[i] = EXPLODING;
									inimigo2.explosion_start[i] = currentTime;
									inimigo2.explosion_end[i] = currentTime + 500;
								}
							}
						}

						for(int i = 0; i < inimigo3.estados.length; i++){

							if(inimigo3.estados[i] == ACTIVE){

								double dx = inimigo3.coordenada_x[i] - player.projectile.coordenada_x[k];
								double dy = inimigo3.coordenada_y[i] - player.projectile.coordenada_y[k];
								double dist = Math.sqrt(dx * dx + dy * dy);

								if(dist < (player.projectile.powerUp1.raio-2 + inimigo3.raio) * 0.8){

									inimigo3.estados[i] = EXPLODING;
									inimigo3.explosion_start[i] = currentTime;
									inimigo3.explosion_end[i] = currentTime + 500;
								}
							}
						}
					}
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

			for(int i =0;i<listaInimigo1.lista.size();i++){
				if(listaInimigo1.getListInimigo1().get(i).getEstado() == EXPLODING){

					if(currentTime > listaInimigo1.getListInimigo1().get(i).getExplosionE()){

						listaInimigo1.getListInimigo1().get(i).setEstado(INACTIVE);
						listaInimigo1.getListInimigo1().remove(i);
						if(listaInimigo1.lista.size()<5)
							listaInimigo1.addLista(currentTime, 1);
					}
				}
			}
			for(int i =0;i<listaInimigo1.lista.size();i++){
				if(listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE){

					/* verificando se inimigo saiu da tela */
					if(listaInimigo1.getListInimigo1().get(i).getCoordenaday() > GameLib.HEIGHT + 10) {

						listaInimigo1.getListInimigo1().get(i).setEstado(INACTIVE);
						listaInimigo1.getListInimigo1().remove(i);
							if(listaInimigo1.lista.size()<5)
								listaInimigo1.addLista(currentTime, 1);
					}
					else {

						listaInimigo1.getListInimigo1().get(i).setCoordenadax(listaInimigo1.getListInimigo1().get(i).getCoordenadax()+listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.cos(	listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta);
						listaInimigo1.getListInimigo1().get(i).setCoordenaday(listaInimigo1.getListInimigo1().get(i).getCoordenaday()+listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.sin(listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta * (-1.0));
						listaInimigo1.getListInimigo1().get(i).setAngulo(listaInimigo1.getListInimigo1().get(i).getAngulo()+listaInimigo1.getListInimigo1().get(i).getVelocidadeR() * delta);

						if(currentTime > listaInimigo1.getListInimigo1().get(i).getnextShot() && listaInimigo1.getListInimigo1().get(i).getCoordenaday() < player.coordenada_y){

							int free = findFreeIndex(inimigoProjectile.estados);

							if(free < inimigoProjectile.estados.length){

								inimigoProjectile.coordenada_x[free] = listaInimigo1.getListInimigo1().get(i).getCoordenadax();
								inimigoProjectile.coordenada_y[free] = listaInimigo1.getListInimigo1().get(i).getCoordenaday();
								inimigoProjectile.velocidade_x[free] = Math.cos(listaInimigo1.getListInimigo1().get(i).getAngulo()) * 0.45;
								inimigoProjectile.velocidade_y[free] = Math.sin(listaInimigo1.getListInimigo1().get(i).getAngulo()) * 0.45 * (-1.0);
								inimigoProjectile.estados[free] = 1;

								listaInimigo1.getListInimigo1().get(i).setnextShot((long) (currentTime + 200 + Math.random() * 500));
							}
						}
					}
				}
			}


		/*atualiza estado powerUp1*/
		if(player.projectile.powerUp1.estadop1 == ACTIVE){

			/* verifica de powerUp1 saiu da tela */
				if(player.projectile.powerUp1.coordenada_y > GameLib.HEIGHT + 10 && player.projectile.powerUp1.estado == false) {

				 player.projectile.powerUp1.estadop1 = INACTIVE;

				player.projectile.powerUp1=new PowerUp1(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0,-10.0,0.10 + Math.random() * 0.10,3 * Math.PI / 2,0.0,
				9.0,currentTime+20000);
			}
			else {

				player.projectile.powerUp1.coordenada_x += player.projectile.powerUp1.velocidade * Math.cos(player.projectile.powerUp1.angulo) * delta;
				player.projectile.powerUp1.coordenada_y += player.projectile.powerUp1.velocidade * Math.sin(player.projectile.powerUp1.angulo) * delta * (-1.0);
				player.projectile.powerUp1.angulo += player.projectile.powerUp1.velocidadeRotacao * delta;

			}
		}
		/*atualiza estado powerUp1*/

		if(player.powerUp2.estadop2 == ACTIVE){

			/* verifica de powerUp1 saiu da tela */
				if(player.powerUp2.coordenada_y > GameLib.HEIGHT + 10 && player.powerUp2.estado2 == false) {

				 player.powerUp2.estadop2 = INACTIVE;

				player.powerUp2=new PowerUp2(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0,-10.0,0.10 + Math.random() * 0.10,3 * Math.PI / 2,0.0,
				9.0,currentTime+25000);
			}
			else {

				player.powerUp2.coordenada_x += player.powerUp2.velocidade * Math.cos(player.powerUp2.angulo) * delta;
				player.powerUp2.coordenada_y += player.powerUp2.velocidade * Math.sin(player.powerUp2.angulo) * delta * (-1.0);
				player.powerUp2.angulo += player.powerUp2.velocidadeRotacao * delta;

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

							if(inimigo3.coordenada_x[i] < GameLib.WIDTH / 2) inimigo3.velocidadeRotacao[i] = 0.005;
							else inimigo3.velocidadeRotacao[i] = -0.005;
						}

						if(inimigo3.velocidadeRotacao[i] > 0 && Math.abs(inimigo3.angulo[i] - 3 * Math.PI) < 0.05){

							inimigo3.velocidadeRotacao[i] = 0.0;
							inimigo3.angulo[i] = 4 * Math.PI;
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

			if(currentTime > listaInimigo1.getnextEnemy1()){
				while(listaInimigo1.lista.size()<5){
				listaInimigo1.addLista(currentTime, 1);
				}
			}
			/* verificando se PowerUp1 deve ser "lançado" */
			if(currentTime > player.projectile.powerUp1.nextPowerUp1){

					//System.out.println(player.projectile.powerUp1.estadop1);
					player.projectile.powerUp1.estadop1 = ACTIVE;
				//	System.out.println(player.projectile.powerUp1.estadop1);
			}
			/* verificando se PowerUp2 deve ser "lançado" */
			if(currentTime > player.powerUp2.nextPowerUp2){

					//System.out.println(player.projectile.powerUp1.estadop1);
					player.powerUp2.estadop2 = ACTIVE;
				//	System.out.println(player.projectile.powerUp1.estadop1);
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

						inimigo3.nextEnemy3 = currentTime;
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

				player.projectile.powerUp1.estado=false;

				if(currentTime > player.explosion_end){

					player.estado = ACTIVE;
				}
			}

			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/

			player.controleMovimetoPlayer(currentTime, delta, running);
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			/*******************/
			/* Desenho da cena */
			/*******************/

			PrimeiroPlano.count += PrimeiroPlano.speed * delta;
			for ( int i = 0; i < 20; i++ )
				primeiroPlano.get(i).desenha( currentTime );

			SegundoPlano.count += SegundoPlano.speed * delta;
			for ( int i = 0; i < 50; i++ ) {
				segundoPlano.get(i).desenha( currentTime );
			}

			player.desenha(currentTime);
			player.projectile.desenha();
			inimigoProjectile.desenha();
			inimigo2.desenha(currentTime);
			inimigo3.desenha(currentTime);

			for(int i =0;i<listaInimigo1.lista.size();i++)
				listaInimigo1.getListInimigo1().get(i).desenha(currentTime);

			player.projectile.powerUp1.desenha();
			player.powerUp2.desenha();

			GameLib.display();

			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

			busyWait(currentTime + 5);
		}

		System.exit(0);
	}
}
