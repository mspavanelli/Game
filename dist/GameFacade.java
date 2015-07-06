import java.util.*;
public class GameFacade{
		//Declaração de atributos da classe
		ListInimigo1 listaInimigo1;
		ListInimigo2 listaInimigo2;
		ListInimigo3 listaInimigo3;
		Player player;
		InimigoProjectile inimigoProjectile;
		ArrayList<PrimeiroPlano> primeiroPlano;
		ArrayList<SegundoPlano> segundoPlano;
		//metodos auxíliares	
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
		//construtor da classe
		public GameFacade(ListInimigo1 listaInimigo1, ListInimigo2 listaInimigo2, ListInimigo3 listaInimigo3, Player player, InimigoProjectile inimigoProjectile, ArrayList<PrimeiroPlano> primeiroPlano, ArrayList<SegundoPlano> segundoPlano){
				this.listaInimigo1=listaInimigo1;
				this.listaInimigo2=listaInimigo2;
				this.listaInimigo3=listaInimigo3;
				this.player=player;
				this.inimigoProjectile=inimigoProjectile;
				this.primeiroPlano=primeiroPlano;
				this.segundoPlano=segundoPlano;
		}
		
		//Nos métodos abaixo os estados sao definidos pelos inteiros: 0(inativo),1(ativo),2(explodindo)
		
		public void verificaColisoes(long currentTime){

						/***************************/
						/* Verificação de colisões */
						/***************************/
						
						if (player.estado == 1) {

							/* colisões player, player com escudo - projeteis (inimigo) */

							for (int i = 0; i < inimigoProjectile.estados.length; i++) {

								double dx = inimigoProjectile.coordenada_x[i] - player.coordenada_x;
								double dy = inimigoProjectile.coordenada_y[i] - player.coordenada_y;
								double dist = Math.sqrt(dx * dx + dy * dy);
								// se ocorrer a colisão e o escudo(powerUp2) estiver desativado
								if (dist < (player.raio + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2 == false) {
									player.estado = 2;
									player.explosion_start = currentTime;
									player.explosion_end = currentTime + 2000;
								}
								//se ocorrer a colisão e o escudo(raio do player é aumentado em 15.1) estiver ativo
								else if (dist < (player.raio + 15.1 + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2 == true) {
									//player continua ativo
									player.estado = 1;
									//escudo desativado
									player.powerUp2.estado2 = false;
									//projétil inimigo desativado
									inimigoProjectile.estados[i] = 0;
									//projetil inimigo sai da tela
									inimigoProjectile.coordenada_y[i] = GameLib.HEIGHT;

								}
							}

							/* colisões player, player com escudo(powerUp2) - inimigos */
							//colisão com o inimigo do tipo 1
							for (int i = 0; i < listaInimigo1.lista.size(); i++) {

								double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.coordenada_x;
								double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.coordenada_y;
								double dist = Math.sqrt(dx * dx + dy * dy);

								if (dist < (player.raio + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8 && player.powerUp2.estado2 == false) {
									player.estado = 2;
									player.explosion_start = currentTime;
									player.explosion_end = currentTime + 2000;
								}
								//se o inimigo colidir com o escudo
								else if (dist < (player.raio + 15.1 + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8 && player.powerUp2.estado2 == true) {
									//player continua ativo
									player.estado = 1;
									//escudo desativado
									player.powerUp2.estado2 = false;
								}
							}
							/* colisão player - powerUp1 */

							double dxpu1 = player.projectile.powerUp1.coordenada_x - player.coordenada_x;
							double dypu1 = player.projectile.powerUp1.coordenada_y - player.coordenada_y;
							double distpu1 = Math.sqrt(dxpu1 * dxpu1 + dypu1 * dypu1);
							//se colidir como powerUp1
							if (distpu1 < (player.raio + player.projectile.powerUp1.raio) * 0.8) {
								//o ícone do powerUp1 é desativado
								player.projectile.powerUp1.estadop1 = 0;
								//o powerUp1 é ativado
								player.projectile.powerUp1.estado = true;
								//O ícone sai da tela
								player.projectile.powerUp1.coordenada_y = GameLib.HEIGHT;
							}
							/* colisão player - powerUp2 */

							double dxpu2 = player.powerUp2.coordenada_x - player.coordenada_x;
							double dypu2 = player.powerUp2.coordenada_y - player.coordenada_y;
							double distpu2 = Math.sqrt(dxpu2 * dxpu2 + dypu2 * dypu2);

							if (distpu2 < (player.raio + player.powerUp2.raio) * 0.8) {

								player.powerUp2.estadop2 = 0;
								player.powerUp2.estado2 = true;
								player.powerUp2.coordenada_y = GameLib.HEIGHT;
							}
							
							//Colisão com o inimigo do tipo 2
							for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

								double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.coordenada_x;
								double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.coordenada_y;
								double dist = Math.sqrt(dx * dx + dy * dy);

								if(dist < (player.raio + listaInimigo2.lista.get(0).raio) * 0.8){

									player.estado = 2;
									player.explosion_start = currentTime;
									player.explosion_end = currentTime + 2000;
								}
								else if (dist < (player.raio + 15.1 + listaInimigo2.lista.get(0).raio) * 0.8 && player.powerUp2.estado2 == true) {
									player.estado = 1;
									player.powerUp2.estado2 = false;
								}
							}

							//Colisão com o inimigo do tipo 3
							for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

								double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.coordenada_x;
								double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.coordenada_y;
								double dist = Math.sqrt(dx * dx + dy * dy);

								if (dist < (player.raio + listaInimigo3.lista.get(0).raio) * 0.8) {
									player.estado = 2;
									player.explosion_start = currentTime;
									player.explosion_end = currentTime + 2000;

								}
								else if (dist < (player.raio + 15.1 + listaInimigo3.lista.get(0).raio) * 0.8 && player.powerUp2.estado2 == true) {
									player.estado = 1;
									player.powerUp2.estado2 = false;
								}

							}
						}

						/* colisões projeteis (player) - inimigos */
						//se o powerUp1(tiro grosso) estiver desativado
						if (player.projectile.powerUp1.estado == false) {
							for (int k = 0; k < player.projectile.estadosProjetil.length; k++) {
								//colisão com o inimigo de tipo 1	
								for (int i = 0; i < listaInimigo1.lista.size(); i++) {

									if (listaInimigo1.getListInimigo1().get(i).getEstado() == 1) {

										double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
										double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
										double dist = Math.sqrt(dx * dx + dy * dy);

										if (dist < listaInimigo1.getListInimigo1().get(i).getRaio()) {
											listaInimigo1.getListInimigo1().get(i).setEstado(2);
											listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
											listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime + 500);
										}
									}
								}
								//Colisão com o inimigo de tipo 2
								for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

									if(listaInimigo2.lista.get(0).estado[i] == 1){

										double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];;
										double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];;
										double dist = Math.sqrt(dx * dx + dy * dy);

										if(dist < listaInimigo2.lista.get(0).raio){

											listaInimigo2.lista.get(0).estado[i] = 2;
											listaInimigo2.lista.get(0).explosion_start[i] = currentTime;
											listaInimigo2.lista.get(0).explosion_end[i] = currentTime + 500;
										}
									}
								}
								//colisão com o inimigo de tipo 3
								for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

									if (listaInimigo3.lista.get(0).estado[i] == 1) {

										double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];
										double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];
										double dist = Math.sqrt(dx * dx + dy * dy);

										if (dist < listaInimigo3.lista.get(0).raio) {
											listaInimigo3.lista.get(0).estado[i] = 2;
											listaInimigo3.lista.get(0).explosion_start[i] = currentTime;
											listaInimigo3.lista.get(0).explosion_end[i] = currentTime + 500;
										}
									}
								}
							}
						}
						//se o tiro grosso estiver ativo
						else if (player.projectile.powerUp1.estado == true) {

							for (int k = 0; k < player.projectile.estadosProjetil.length; k++) {
								//colisao com o inimigo de tipo 1
								for (int i = 0; i < listaInimigo1.lista.size(); i++) {

									if (listaInimigo1.getListInimigo1().get(i).getEstado() == 1) {

										double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
										double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
										double dist = Math.sqrt(dx * dx + dy * dy);
										//o novo raio do tiro é igual ao raio do ícone do powerUp1 -2, almentando o seu alcance
										if (dist < player.projectile.powerUp1.raio - 2 + listaInimigo1.getListInimigo1().get(i).getRaio() * 0.8) {
											listaInimigo1.getListInimigo1().get(i).setEstado(2);
											listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
											listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime + 500);
										}
									}
								}
								//colisao com o inimigo de tipo 2
								for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

									if(listaInimigo2.lista.get(0).estado[i] == 1){

										double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];;
										double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];;
										double dist = Math.sqrt(dx * dx + dy * dy);

										if(dist < (player.projectile.powerUp1.raio - 2 + listaInimigo2.lista.get(0).raio) * 0.8){

											listaInimigo2.lista.get(0).estado[i] = 2;
											listaInimigo2.lista.get(0).explosion_start[i] = currentTime;
											listaInimigo2.lista.get(0).explosion_end[i] = currentTime + 500;
										}
									}
								}
								//colisao com o inimigo de tipo 3
								for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

									if (listaInimigo3.lista.get(0).estado[i] == 1) {

										double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];
										double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];
										double dist = Math.sqrt(dx * dx + dy * dy);

										if (dist < (player.projectile.powerUp1.raio - 2 + listaInimigo3.lista.get(0).raio) * 0.8) {
											listaInimigo3.lista.get(0).estado[i] = 2;
											listaInimigo3.lista.get(0).explosion_start[i] = currentTime;
											listaInimigo3.lista.get(0).explosion_end[i] = currentTime + 500;
										}
									}
								}
							}
						}

		}
		public void atualizaEstado(long currentTime, long delta){
			/***************************/
			/* Atualizações de estados */
			/***************************/

			/* projeteis (player) */

			for (int i = 0; i < player.projectile.estadosProjetil.length; i++) {
				if (player.projectile.estadosProjetil[i] == 1) {
					/* verificando se projétil saiu da tela */
					if (player.projectile.coordenada_y[i] < 0) {
						player.projectile.estadosProjetil[i] = 0;
					}
					else {
						player.projectile.coordenada_x[i] += player.projectile.velocidade_x[i] * delta;
						player.projectile.coordenada_y[i] += player.projectile.velocidade_y[i] * delta;
					}
				}
			}

			/* projeteis (inimigos) */

			for (int i = 0; i < inimigoProjectile.estados.length; i++) {
				if (inimigoProjectile.estados[i] == 1) {
					/* verificando se projétil saiu da tela */
					if (inimigoProjectile.coordenada_y[i] > GameLib.HEIGHT) {
						inimigoProjectile.estados[i] = 0;
					}
					else {
						inimigoProjectile.coordenada_x[i] += inimigoProjectile.velocidade_x[i] * delta;
						inimigoProjectile.coordenada_y[i] += inimigoProjectile.velocidade_y[i] * delta;
					}
				}
			}

			/* inimigos tipo 1 */

			for (int i = 0; i < listaInimigo1.lista.size(); i++) {
				if (listaInimigo1.getListInimigo1().get(i).getEstado() == 2) {

					if (currentTime > listaInimigo1.getListInimigo1().get(i) .getExplosionE()) {

						listaInimigo1.getListInimigo1().get(i).setEstado(0);
						listaInimigo1.getListInimigo1().remove(i);
						if (listaInimigo1.lista.size() < 5) listaInimigo1.addLista(currentTime, 1);
					}
				}
			}

			for (int i = 0; i < listaInimigo1.lista.size(); i++) {
				if (listaInimigo1.getListInimigo1().get(i).getEstado() == 1) {

					/* verificando se inimigo saiu da tela */
					if (listaInimigo1.getListInimigo1().get(i).getCoordenaday() > GameLib.HEIGHT + 10) {
						listaInimigo1.getListInimigo1().get(i) .setEstado(0);
						listaInimigo1.getListInimigo1().remove(i);
						if (listaInimigo1.lista.size() < 5) listaInimigo1.addLista(currentTime, 1);
					}
					else {
						listaInimigo1.getListInimigo1().get(i).setCoordenadax(listaInimigo1.getListInimigo1().get(i).getCoordenadax() + listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.cos(listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta);
						listaInimigo1.getListInimigo1().get(i).setCoordenaday(listaInimigo1.getListInimigo1().get(i).getCoordenaday() + listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.sin(listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta * (-1.0));
						listaInimigo1.getListInimigo1().get(i).setAngulo(listaInimigo1.getListInimigo1().get(i).getAngulo() + listaInimigo1.getListInimigo1().get(i).getVelocidadeR() * delta);

						if (currentTime > listaInimigo1.getListInimigo1().get(i).getnextShot() && listaInimigo1.getListInimigo1().get(i).getCoordenaday() < player.coordenada_y) {

							int free = findFreeIndex(inimigoProjectile.estados);
							if (free < inimigoProjectile.estados.length) {

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

			/* atualiza estado powerUp1 */
			if (player.projectile.powerUp1.estadop1 == 1) {

				/* verifica de powerUp1 saiu da tela */
				if (player.projectile.powerUp1.coordenada_y > GameLib.HEIGHT + 10 && player.projectile.powerUp1.estado == false) {

					player.projectile.powerUp1.estadop1 = 0;
					player.projectile.powerUp1 = new PowerUp1(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07, 3 * Math.PI / 2, 0.0, 9.0, currentTime + 20000);
				}
				else {
					player.projectile.powerUp1.coordenada_x += player.projectile.powerUp1.velocidade * Math.cos(player.projectile.powerUp1.angulo) * delta;
					player.projectile.powerUp1.coordenada_y += player.projectile.powerUp1.velocidade * Math.sin(player.projectile.powerUp1.angulo) * delta * (-1.0);
					player.projectile.powerUp1.angulo += player.projectile.powerUp1.velocidadeRotacao * delta;
				}
			}

			/* atualiza estado powerUp1 */

			if (player.powerUp2.estadop2 == 1) {

				/* verifica de powerUp1 saiu da tela */
				if (player.powerUp2.coordenada_y > GameLib.HEIGHT + 10 && player.powerUp2.estado2 == false) {

					player.powerUp2.estadop2 = 0;
					player.powerUp2 = new PowerUp2(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07, 3 * Math.PI / 2, 0.0, 9.0, currentTime + 25000);
				}
				else {
					player.powerUp2.coordenada_x += player.powerUp2.velocidade * Math.cos(player.powerUp2.angulo) * delta;
					player.powerUp2.coordenada_y += player.powerUp2.velocidade * Math.sin(player.powerUp2.angulo) * delta * (-1.0);
					player.powerUp2.angulo += player.powerUp2.velocidadeRotacao * delta;
				}
			}

			/* inimigos tipo 2 */

			for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

				if(listaInimigo2.lista.get(0).estado[i] == 2){
					listaInimigo2.lista.get(0).verificaExplosao();
					//System.out.println(listaInimigo2.lista.get(0).verificaExplosao);
					if(currentTime > listaInimigo2.lista.get(0).explosion_end[i]){

						listaInimigo2.lista.get(0).estado[i] = 0;
						if(	listaInimigo2.lista.get(0).verificaExplosao==true){
							listaInimigo2.lista.remove(0);
							listaInimigo2.addLista(currentTime,1);

						}
					}
				}
			}

			for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){
				if(listaInimigo2.lista.get(0).estado[i] == 1){

					/* verificando se inimigo saiu da tela */
					if(	listaInimigo2.lista.get(0).coordenada_x[i] < -10 || listaInimigo2.lista.get(0).coordenada_x[i] > GameLib.WIDTH + 10 ) {

						listaInimigo2.lista.get(0).estado[i] = 0;
						listaInimigo2.lista.get(0).verificaSaidaTela();
						if(listaInimigo2.lista.get(0).saiudaTela==true){
							listaInimigo2.lista.remove(0);
							listaInimigo2.addLista(currentTime,1);
						}
					}
					else {

						boolean shootNow = false;
						double previousY = listaInimigo2.lista.get(0).coordenada_y[i];

						listaInimigo2.lista.get(0).coordenada_x[i] += listaInimigo2.lista.get(0).velocidade[i] * Math.cos(listaInimigo2.lista.get(0).angulo[i]) * delta;
						listaInimigo2.lista.get(0).coordenada_y[i] += listaInimigo2.lista.get(0).velocidade[i] * Math.sin(listaInimigo2.lista.get(0).angulo[i]) * delta * (-1.0);
						listaInimigo2.lista.get(0).angulo[i] += listaInimigo2.lista.get(0).velocidadeRotacao[i] * delta;

						double threshold = GameLib.HEIGHT * 0.30;

						if(previousY < threshold && listaInimigo2.lista.get(0).coordenada_y[i] >= threshold) {

							if(listaInimigo2.lista.get(0).coordenada_x[i] < GameLib.WIDTH / 2) listaInimigo2.lista.get(0).velocidadeRotacao[i] = 0.003;
							else listaInimigo2.lista.get(0).velocidadeRotacao[i] = -0.003;
						}

						if(listaInimigo2.lista.get(0).velocidadeRotacao[i] > 0 && Math.abs(listaInimigo2.lista.get(0).angulo[i] - 3 * Math.PI) < 0.05){

							listaInimigo2.lista.get(0).velocidadeRotacao[i] = 0.0;
							listaInimigo2.lista.get(0).angulo[i] = 3 * Math.PI;
							shootNow = true;
						}

						if(listaInimigo2.lista.get(0).velocidadeRotacao[i] < 0 && Math.abs(listaInimigo2.lista.get(0).angulo[i]) < 0.05){

							listaInimigo2.lista.get(0).velocidadeRotacao[i] = 0.0;
							listaInimigo2.lista.get(0).angulo[i] = 0.0;
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

									inimigoProjectile.coordenada_x[free] = listaInimigo2.lista.get(0).coordenada_x[i];
									inimigoProjectile.coordenada_y[free] = listaInimigo2.lista.get(0).coordenada_y[i];
									inimigoProjectile.velocidade_x[free] = vx * 0.30;
									inimigoProjectile.velocidade_y[free] = vy * 0.30;
									inimigoProjectile.estados[free] = 1;
								}
							}
						}
					}
				}
			}

			/* inimigos tipo 3 */

			for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

				if (listaInimigo3.lista.get(0).estado[i] == 2) {
						listaInimigo3.lista.get(0).verificaExplosao();

					if (currentTime > listaInimigo3.lista.get(0).explosion_end[i]) {

						listaInimigo3.lista.get(0).estado[i] = 0;
						if(	listaInimigo3.lista.get(0).verificaExplosao==true){
							listaInimigo3.lista.remove(0);
							listaInimigo3.addLista(currentTime,1);

						}
					}
				}
			}
			for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

				if (listaInimigo3.lista.get(0).estado[i] == 1) {

					/* verificando se inimigo saiu da tela */

					if (listaInimigo3.lista.get(0).coordenada_x[i] < -10|| listaInimigo3.lista.get(0).coordenada_x[i] > GameLib.WIDTH + 10) {

						listaInimigo3.lista.get(0).estado[i] = 0;
							listaInimigo3.lista.get(0).verificaSaidaTela();
						if(listaInimigo3.lista.get(0).saiudaTela==true){
							listaInimigo3.lista.remove(0);
							listaInimigo3.addLista(currentTime,1);
						}
					}
					else {
						boolean shootNow = false;
						double previousY = listaInimigo3.lista.get(0).coordenada_y[i];

						listaInimigo3.lista.get(0).coordenada_x[i] += listaInimigo3.lista.get(0).velocidade[i]
								* Math.cos(listaInimigo3.lista.get(0).angulo[i]) * delta;
						listaInimigo3.lista.get(0).coordenada_y[i] += listaInimigo3.lista.get(0).velocidade[i]
								* Math.sin(listaInimigo3.lista.get(0).angulo[i]) * delta * (-1.0);
						listaInimigo3.lista.get(0).angulo[i] += listaInimigo3.lista.get(0).velocidadeRotacao[i]
								* delta;

						double threshold = GameLib.HEIGHT * 0.30;

						if (previousY < threshold
								&& listaInimigo3.lista.get(0).coordenada_y[i] >= threshold) {

							if (listaInimigo3.lista.get(0).coordenada_x[i] < GameLib.WIDTH / 2)
								listaInimigo3.lista.get(0).velocidadeRotacao[i] = 0.005;
							else
								listaInimigo3.lista.get(0).velocidadeRotacao[i] = -0.005;
						}

						if (listaInimigo3.lista.get(0).velocidadeRotacao[i] > 0
								&& Math.abs(listaInimigo3.lista.get(0).angulo[i] - 3 * Math.PI) < 0.05) {

							listaInimigo3.lista.get(0).velocidadeRotacao[i] = 0.0;
							listaInimigo3.lista.get(0).angulo[i] = 4 * Math.PI;
							shootNow = true;
						}

						if (listaInimigo3.lista.get(0).velocidadeRotacao[i] < 0
								&& Math.abs(listaInimigo3.lista.get(0).angulo[i]) < 0.05) {

							listaInimigo3.lista.get(0).velocidadeRotacao[i] = 0.0;
							listaInimigo3.lista.get(0).angulo[i] = 0.0;
							shootNow = true;
						}

						if (shootNow) {

							double[] angles = { Math.PI / 2, Math.PI / 2, Math.PI / 2 };
							int[] freeArray = findFreeIndex(inimigoProjectile.estados, angles.length);

							for (int k = 0; k < freeArray.length; k++) {

								int free = freeArray[k];

								if (free < inimigoProjectile.estados.length) {

									double a = angles[k] + Math.random() * Math.PI / 4 - Math.PI / 8;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									inimigoProjectile.coordenada_x[free] = listaInimigo3.lista.get(0).coordenada_x[i];
									inimigoProjectile.coordenada_y[free] = listaInimigo3.lista.get(0).coordenada_y[i];
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
			if (currentTime > listaInimigo1.getnextEnemy1()) {
				while (listaInimigo1.lista.size() < 5) {
					listaInimigo1.addLista(currentTime, 1);
				}
			}

			/* verificando se PowerUp1 deve ser "lançado" */
			if (currentTime > player.projectile.powerUp1.nextPowerUp1)
				player.projectile.powerUp1.estadop1 = 1;

			/* verificando se PowerUp2 deve ser "lançado" */
			if (currentTime > player.powerUp2.nextPowerUp2)
				player.powerUp2.estadop2 = 1;

			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			if(currentTime > listaInimigo2.lista.get(0).nextEnemy2){

				int free = findFreeIndex(listaInimigo2.lista.get(0).estado);

				if(free < listaInimigo2.lista.get(0).estado.length){

					listaInimigo2.lista.get(0).coordenada_x[free] = listaInimigo2.lista.get(0).spawnX;
					listaInimigo2.lista.get(0).coordenada_y[free] = -10.0;
					listaInimigo2.lista.get(0).velocidade[free] = 0.42;
					listaInimigo2.lista.get(0).angulo[free] = (3 * Math.PI) / 2;
					listaInimigo2.lista.get(0).velocidadeRotacao[free] = 0.0;
					listaInimigo2.lista.get(0).estado[free] = 1;

					listaInimigo2.lista.get(0).count++;

					if(listaInimigo2.lista.get(0).count < 10){

						listaInimigo2.lista.get(0).nextEnemy2 = currentTime + 120;
					}
					else {

						listaInimigo2.lista.get(0).count = 0;
						listaInimigo2.lista.get(0).spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.4 : GameLib.WIDTH * 0.6;
						listaInimigo2.lista.get(0).nextEnemy2 = (long) (currentTime + 7000 + Math.random() * 7000);
					}
				}
			}

			/* verificando se novos inimigos (tipo 3) devem ser "lançados" */

			if (currentTime > listaInimigo3.lista.get(0).nextEnemy3) {

				int free = findFreeIndex(listaInimigo3.lista.get(0).estado);
				if (free < listaInimigo3.lista.get(0).estado.length) {
					listaInimigo3.lista.get(0).coordenada_x[free] = listaInimigo3.lista.get(0).spawnX;
					listaInimigo3.lista.get(0).coordenada_y[free] = -10.0;
					listaInimigo3.lista.get(0).velocidade[free] = 0.32;
					listaInimigo3.lista.get(0).angulo[free] = (3 * Math.PI) / 2;
					listaInimigo3.lista.get(0).velocidadeRotacao[free] = 0.0;
					listaInimigo3.lista.get(0).estado[free] = 1;

					listaInimigo3.lista.get(0).count++;

					if (listaInimigo3.lista.get(0).count < 10) listaInimigo3.lista.get(0).nextEnemy3 = currentTime;
					else {
						listaInimigo3.lista.get(0).count = 0;
						listaInimigo3.lista.get(0).spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.4 : GameLib.WIDTH * 0.6;
						listaInimigo3.lista.get(0).nextEnemy3 = (long) (currentTime + 8000 - Math .random() * 8000);
					}
				}
			}

			/* Verificando se a explosão do player já acabou. */
			/* Ao final da explosão, o player volta a ser controlável */
			if (player.estado == 2) {

				player.projectile.powerUp1.estado = false;

				if (currentTime > player.explosion_end) {

					player.estado = 1;
				}
			}

		}
		//metodo que permite o controle do player
		public boolean verificaControlePlayer(boolean running, long currentTime, long delta){

			player.controleMovimetoPlayer(currentTime, delta, running);
			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
				return false;
			return true;
		}
		//metodo que desenha a cena
		public void desenhaCena(long currentTime, long delta){
			//desenha o primeiro plano
			PrimeiroPlano.count += PrimeiroPlano.speed * delta;
			for (PrimeiroPlano pp : primeiroPlano) pp.desenha(currentTime);
			//desenha o segundo plano
			SegundoPlano.count += SegundoPlano.speed * delta;
			for (SegundoPlano sp : segundoPlano) sp.desenha(currentTime);
			
			//desenha o player
			player.desenha(currentTime);
			//desenha o projétil do player
			player.projectile.desenha();
			//desenha os projeteis dos inimigos
			inimigoProjectile.desenha();
			//desenho dos inimigos
			listaInimigo2.getListInimigo2().get(0).desenha(currentTime);
			listaInimigo3.getListInimigo3().get(0).desenha(currentTime);
			for (int i = 0; i < listaInimigo1.lista.size(); i++)
				listaInimigo1.getListInimigo1().get(i).desenha(currentTime);
			//desenho dos icones dos powerUp's
			player.projectile.powerUp1.desenha();
			player.powerUp2.desenha();


		}


}
