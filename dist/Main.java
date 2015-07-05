import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	public static void atualizaEstadoInimigos() {}

	public static void verificaLancamentoNovosInimigos( ListaInimigoX listaInimigoX, long currentTime ) {

	}

	public static void colisaoProjeteisComPlayer() {}

	public static void testeInimigosGenericos( ListaInimigoX listaInimigo ) {

	}

	/* Método principal */

	public static void main(String[] args) {

		boolean running = true;

		long delta;
		long currentTime = System.currentTimeMillis();

		Player player = new Player(new PowerUp2(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07, 3 * Math.PI / 2, 0.0, 9.0, currentTime + 25000),
		new PlayerProjectile(new int[10], new double[10], new double[10], new double[10], new double[10],
		new PowerUp1(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07,
		 3 * Math.PI / 2, 0.0, 9.0, currentTime + 20000)),
		ACTIVE, GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 0.25, 0.25, 12.0, 0, 0, currentTime);

		ListInimigo1 listaInimigo1 = new ListInimigo1(new LinkedList<Inimigo1>(), currentTime + 2000);

		ListInimigo2 listaInimigo2 =new ListInimigo2(new LinkedList<Inimigo2>());
		ListInimigo3 listaInimigo3 = new ListInimigo3(new LinkedList<Inimigo3>());
		
		listaInimigo2.addLista(currentTime,1);
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

			/***************************/
			/* Verificação de colisões */
			/***************************/

			if (player.estado == ACTIVE) {

				/* Laços que podem ser simplificados */

				/* colisões player, player com escudo - projeteis (inimigo) */

				for (int i = 0; i < inimigoProjectile.estados.length; i++) {

					double dx = inimigoProjectile.coordenada_x[i] - player.coordenada_x;
					double dy = inimigoProjectile.coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if (dist < (player.raio + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2 == false) {

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					}
					else if (dist < (player.raio + 15.1 + inimigoProjectile.raio) * 0.8 && player.powerUp2.estado2 == true) {
						player.estado = ACTIVE;
						player.powerUp2.estado2 = false;
						inimigoProjectile.estados[i] = INACTIVE;
						inimigoProjectile.coordenada_y[i] = GameLib.HEIGHT;

					}
				}

				/* colisões player, player com escudo(powerUp2) - inimigos */
				for (int i = 0; i < listaInimigo1.lista.size(); i++) {

					double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.coordenada_x;
					double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if (dist < (player.raio + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8 && player.powerUp2.estado2 == false) {
						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					}
					else if (dist < (player.raio + 15.1 + listaInimigo1.getListInimigo1().get(i).getRaio()) * 0.8 && player.powerUp2.estado2 == true) {
						player.estado = ACTIVE;
						player.powerUp2.estado2 = false;
					}
				}
				/* colisão player - powerUp1 */

				double dxpu1 = player.projectile.powerUp1.coordenada_x - player.coordenada_x;
				double dypu1 = player.projectile.powerUp1.coordenada_y - player.coordenada_y;
				double distpu1 = Math.sqrt(dxpu1 * dxpu1 + dypu1 * dypu1);

				if (distpu1 < (player.raio + player.projectile.powerUp1.raio) * 0.8) {
					player.projectile.powerUp1.estadop1 = 0;
					player.projectile.powerUp1.estado = true;
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
				for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

					double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.coordenada_x;
					double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if(dist < (player.raio + listaInimigo2.lista.get(0).raio) * 0.8){

						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;
					}
					else if (dist < (player.raio + 15.1 + listaInimigo2.lista.get(0).raio) * 0.8 && player.powerUp2.estado2 == true) {
						player.estado = ACTIVE;
						player.powerUp2.estado2 = false;
					}
				}


				for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

					double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.coordenada_x;
					double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.coordenada_y;
					double dist = Math.sqrt(dx * dx + dy * dy);

					if (dist < (player.raio + listaInimigo3.lista.get(0).raio) * 0.8) {
						player.estado = EXPLODING;
						player.explosion_start = currentTime;
						player.explosion_end = currentTime + 2000;

					}
					else if (dist < (player.raio + 15.1 + listaInimigo3.lista.get(0).raio) * 0.8 && player.powerUp2.estado2 == true) {
						player.estado = ACTIVE;
						player.powerUp2.estado2 = false;
					}

				}
			}

			/* colisões projeteis (player) - inimigos */
			if (player.projectile.powerUp1.estado == false) {
				for (int k = 0; k < player.projectile.estadosProjetil.length; k++) {

					for (int i = 0; i < listaInimigo1.lista.size(); i++) {

						if (listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE) {

							double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
							double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if (dist < listaInimigo1.getListInimigo1().get(i).getRaio()) {
								listaInimigo1.getListInimigo1().get(i).setEstado(EXPLODING);
								listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
								listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime + 500);
							}
						}
					}

					for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

						if(listaInimigo2.lista.get(0).estado[i] == ACTIVE){

							double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];;
							double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];;
							double dist = Math.sqrt(dx * dx + dy * dy);

							if(dist < listaInimigo2.lista.get(0).raio){

								listaInimigo2.lista.get(0).estado[i] = EXPLODING;
								listaInimigo2.lista.get(0).explosion_start[i] = currentTime;
								listaInimigo2.lista.get(0).explosion_end[i] = currentTime + 500;
							}
						}
					}

					for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

						if (listaInimigo3.lista.get(0).estado[i] == ACTIVE) {

							double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];
							double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if (dist < listaInimigo3.lista.get(0).raio) {
								listaInimigo3.lista.get(0).estado[i] = EXPLODING;
								listaInimigo3.lista.get(0).explosion_start[i] = currentTime;
								listaInimigo3.lista.get(0).explosion_end[i] = currentTime + 500;
							}
						}
					}
				}
			}
			else if (player.projectile.powerUp1.estado == true) {

				for (int k = 0; k < player.projectile.estadosProjetil.length; k++) {

					for (int i = 0; i < listaInimigo1.lista.size(); i++) {

						if (listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE) {

							double dx = listaInimigo1.getListInimigo1().get(i).getCoordenadax() - player.projectile.coordenada_x[k];
							double dy = listaInimigo1.getListInimigo1().get(i).getCoordenaday() - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if (dist < player.projectile.powerUp1.raio - 2 + listaInimigo1.getListInimigo1().get(i).getRaio() * 0.8) {
								listaInimigo1.getListInimigo1().get(i).setEstado(EXPLODING);
								listaInimigo1.getListInimigo1().get(i).setExplosionS(currentTime);
								listaInimigo1.getListInimigo1().get(i).setExplosionE(currentTime + 500);
							}
						}
					}

					for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){

						if(listaInimigo2.lista.get(0).estado[i] == ACTIVE){

							double dx = listaInimigo2.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];;
							double dy = listaInimigo2.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];;
							double dist = Math.sqrt(dx * dx + dy * dy);

							if(dist < (player.projectile.powerUp1.raio - 2 + listaInimigo2.lista.get(0).raio) * 0.8){

								listaInimigo2.lista.get(0).estado[i] = EXPLODING;
								listaInimigo2.lista.get(0).explosion_start[i] = currentTime;
								listaInimigo2.lista.get(0).explosion_end[i] = currentTime + 500;
							}
						}
					}

					for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

						if (listaInimigo3.lista.get(0).estado[i] == ACTIVE) {

							double dx = listaInimigo3.lista.get(0).coordenada_x[i] - player.projectile.coordenada_x[k];
							double dy = listaInimigo3.lista.get(0).coordenada_y[i] - player.projectile.coordenada_y[k];
							double dist = Math.sqrt(dx * dx + dy * dy);

							if (dist < (player.projectile.powerUp1.raio - 2 + listaInimigo3.lista.get(0).raio) * 0.8) {
								listaInimigo3.lista.get(0).estado[i] = EXPLODING;
								listaInimigo3.lista.get(0).explosion_start[i] = currentTime;
								listaInimigo3.lista.get(0).explosion_end[i] = currentTime + 500;
							}
						}
					}
				}
			}

			/***************************/
			/* Atualizações de estados */
			/***************************/

			/* projeteis (player) */

			for (int i = 0; i < player.projectile.estadosProjetil.length; i++) {
				if (player.projectile.estadosProjetil[i] == ACTIVE) {
					/* verificando se projétil saiu da tela */
					if (player.projectile.coordenada_y[i] < 0) {
						player.projectile.estadosProjetil[i] = INACTIVE;
					}
					else {
						player.projectile.coordenada_x[i] += player.projectile.velocidade_x[i] * delta;
						player.projectile.coordenada_y[i] += player.projectile.velocidade_y[i] * delta;
					}
				}
			}

			/* projeteis (inimigos) */

			for (int i = 0; i < inimigoProjectile.estados.length; i++) {
				if (inimigoProjectile.estados[i] == ACTIVE) {
					/* verificando se projétil saiu da tela */
					if (inimigoProjectile.coordenada_y[i] > GameLib.HEIGHT) {
						inimigoProjectile.estados[i] = INACTIVE;
					}
					else {
						inimigoProjectile.coordenada_x[i] += inimigoProjectile.velocidade_x[i] * delta;
						inimigoProjectile.coordenada_y[i] += inimigoProjectile.velocidade_y[i] * delta;
					}
				}
			}

			/* inimigos tipo 1 */

			for (int i = 0; i < listaInimigo1.lista.size(); i++) {
				if (listaInimigo1.getListInimigo1().get(i).getEstado() == EXPLODING) {

					if (currentTime > listaInimigo1.getListInimigo1().get(i) .getExplosionE()) {

						listaInimigo1.getListInimigo1().get(i).setEstado(INACTIVE);
						listaInimigo1.getListInimigo1().remove(i);
						if (listaInimigo1.lista.size() < 5) listaInimigo1.addLista(currentTime, 1);
					}
				}
			}

			for (int i = 0; i < listaInimigo1.lista.size(); i++) {
				if (listaInimigo1.getListInimigo1().get(i).getEstado() == ACTIVE) {

					/* verificando se inimigo saiu da tela */
					if (listaInimigo1.getListInimigo1().get(i).getCoordenaday() > GameLib.HEIGHT + 10) {
						listaInimigo1.getListInimigo1().get(i) .setEstado(INACTIVE);
						listaInimigo1.getListInimigo1().remove(i);
						if (listaInimigo1.lista.size() < 5) listaInimigo1.addLista(currentTime, 1);
					}
					else {
						listaInimigo1.getListInimigo1().get(i).setCoordenadax(listaInimigo1.getListInimigo1().get(i).getCoordenadax() + listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.cos(listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta);
						listaInimigo1.getListInimigo1().get(i).setCoordenaday(listaInimigo1.getListInimigo1().get(i).getCoordenaday() + listaInimigo1.getListInimigo1().get(i).getVelocidade() * Math.sin(listaInimigo1.getListInimigo1().get(i).getAngulo()) * delta * (-1.0));
						listaInimigo1.getListInimigo1().get(i).setAngulo(listaInimigo1.getListInimigo1().get(i).getAngulo() + listaInimigo1.getListInimigo1().get(i).getVelocidadeR() * delta);

						if (currentTime > listaInimigo1.getListInimigo1().get(i).getnextShot() && listaInimigo1.getListInimigo1().get(i).getCoordenaday() < player.coordenada_y) {

							int free = GerenciamentoEstruturas.findFreeIndex(inimigoProjectile.estados);
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
			if (player.projectile.powerUp1.estadop1 == ACTIVE) {

				/* verifica de powerUp1 saiu da tela */
				if (player.projectile.powerUp1.coordenada_y > GameLib.HEIGHT + 10 && player.projectile.powerUp1.estado == false) {

					player.projectile.powerUp1.estadop1 = INACTIVE;
					player.projectile.powerUp1 = new PowerUp1(false, 0, Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 0.07 + Math.random() * 0.07, 3 * Math.PI / 2, 0.0, 9.0, currentTime + 20000);
				}
				else {
					player.projectile.powerUp1.coordenada_x += player.projectile.powerUp1.velocidade * Math.cos(player.projectile.powerUp1.angulo) * delta;
					player.projectile.powerUp1.coordenada_y += player.projectile.powerUp1.velocidade * Math.sin(player.projectile.powerUp1.angulo) * delta * (-1.0);
					player.projectile.powerUp1.angulo += player.projectile.powerUp1.velocidadeRotacao * delta;
				}
			}

			/* atualiza estado powerUp2 */

			if (player.powerUp2.estadop2 == ACTIVE) {

				/* verifica de powerUp1 saiu da tela */
				if (player.powerUp2.coordenada_y > GameLib.HEIGHT + 10 && player.powerUp2.estado2 == false) {

					player.powerUp2.estadop2 = INACTIVE;
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

				if(listaInimigo2.lista.get(0).estado[i] == EXPLODING){
					listaInimigo2.lista.get(0).verificaExplosao();
					//System.out.println(listaInimigo2.lista.get(0).verificaExplosao);
					if(currentTime > listaInimigo2.lista.get(0).explosion_end[i]){

						listaInimigo2.lista.get(0).estado[i] = INACTIVE;
						if(	listaInimigo2.lista.get(0).verificaExplosao==true){
							listaInimigo2.lista.remove(0);
							listaInimigo2.addLista(currentTime,1);

						}
					}
				}
			}

			for(int i = 0; i < listaInimigo2.lista.get(0).estado.length; i++){
				if(listaInimigo2.lista.get(0).estado[i] == ACTIVE){

					/* verificando se inimigo saiu da tela */
					if(	listaInimigo2.lista.get(0).coordenada_x[i] < -10 || listaInimigo2.lista.get(0).coordenada_x[i] > GameLib.WIDTH + 10 ) {

						listaInimigo2.lista.get(0).estado[i] = INACTIVE;
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
							int [] freeArray = GerenciamentoEstruturas.findFreeIndex(inimigoProjectile.estados, angles.length);

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

				if (listaInimigo3.lista.get(0).estado[i] == EXPLODING) {
						listaInimigo3.lista.get(0).verificaExplosao();

					if (currentTime > listaInimigo3.lista.get(0).explosion_end[i]) {

						listaInimigo3.lista.get(0).estado[i] = INACTIVE;
						if(	listaInimigo3.lista.get(0).verificaExplosao==true){
							listaInimigo3.lista.remove(0);
							listaInimigo3.addLista(currentTime,1);

						}
					}
				}
			}

			for (int i = 0; i < listaInimigo3.lista.get(0).estado.length; i++) {

				if (listaInimigo3.lista.get(0).estado[i] == ACTIVE) {

					/* verificando se inimigo saiu da tela */

					if (listaInimigo3.lista.get(0).coordenada_x[i] < -10|| listaInimigo3.lista.get(0).coordenada_x[i] > GameLib.WIDTH + 10) {

						listaInimigo3.lista.get(0).estado[i] = INACTIVE;
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
							int[] freeArray = GerenciamentoEstruturas.findFreeIndex(inimigoProjectile.estados, angles.length);

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
				player.projectile.powerUp1.estadop1 = ACTIVE;

			/* verificando se PowerUp2 deve ser "lançado" */
			if (currentTime > player.powerUp2.nextPowerUp2)
				player.powerUp2.estadop2 = ACTIVE;

			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			if(currentTime > listaInimigo2.lista.get(0).nextEnemy){

				int free = GerenciamentoEstruturas.findFreeIndex(listaInimigo2.lista.get(0).estado);

				if(free < listaInimigo2.lista.get(0).estado.length){

					listaInimigo2.lista.get(0).coordenada_x[free] = listaInimigo2.lista.get(0).spawnX;
					listaInimigo2.lista.get(0).coordenada_y[free] = -10.0;
					listaInimigo2.lista.get(0).velocidade[free] = 0.42;
					listaInimigo2.lista.get(0).angulo[free] = (3 * Math.PI) / 2;
					listaInimigo2.lista.get(0).velocidadeRotacao[free] = 0.0;
					listaInimigo2.lista.get(0).estado[free] = ACTIVE;

					listaInimigo2.lista.get(0).count++;

					if(listaInimigo2.lista.get(0).count < 10){

						listaInimigo2.lista.get(0).nextEnemy = currentTime + 120;
					}
					else {

						listaInimigo2.lista.get(0).count = 0;
						listaInimigo2.lista.get(0).spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.4 : GameLib.WIDTH * 0.6;
						listaInimigo2.lista.get(0).nextEnemy = (long) (currentTime + 7000 + Math.random() * 7000);
					}
				}
			}

			/* verificando se novos inimigos (tipo 3) devem ser "lançados" */

			// verificaLancamentoNovosInimigos( listaInimigo3, currentTime );
			// testeInimigosGenericos( listaInimigo3 );
			
			if (currentTime > listaInimigo3.lista.get(0).nextEnemy) {

				int free = GerenciamentoEstruturas.findFreeIndex(listaInimigo3.lista.get(0).estado);
				if (free < listaInimigo3.lista.get(0).estado.length) {
					listaInimigo3.lista.get(0).coordenada_x[free] = listaInimigo3.lista.get(0).spawnX;
					listaInimigo3.lista.get(0).coordenada_y[free] = -10.0;
					listaInimigo3.lista.get(0).velocidade[free] = 0.32;
					listaInimigo3.lista.get(0).angulo[free] = (3 * Math.PI) / 2;
					listaInimigo3.lista.get(0).velocidadeRotacao[free] = 0.0;
					listaInimigo3.lista.get(0).estado[free] = ACTIVE;

					listaInimigo3.lista.get(0).count++;

					if (listaInimigo3.lista.get(0).count < 10) listaInimigo3.lista.get(0).nextEnemy = currentTime;
					else {
						listaInimigo3.lista.get(0).count = 0;
						listaInimigo3.lista.get(0).spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.4 : GameLib.WIDTH * 0.6;
						listaInimigo3.lista.get(0).nextEnemy = (long) (currentTime + 8000 - Math .random() * 8000);
					}
				}
			}
			


			/* Verificando se a explosão do player já acabou. */
			/* Ao final da explosão, o player volta a ser controlável */
			if (player.estado == EXPLODING) {

				player.projectile.powerUp1.estado = false;

				if (currentTime > player.explosion_end) {

					player.estado = ACTIVE;
				}
			}

			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/

			player.controleMovimetoPlayer(currentTime, delta, running);
			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE))
				running = false;

			/*******************/
			/* Desenho da cena */
			/*******************/

			PrimeiroPlano.count += PrimeiroPlano.speed * delta;
			for (PrimeiroPlano pp : primeiroPlano) pp.desenha(currentTime);

			SegundoPlano.count += SegundoPlano.speed * delta;
			for (SegundoPlano sp : segundoPlano) sp.desenha(currentTime);

			player.desenha(currentTime);
			player.projectile.desenha();
			inimigoProjectile.desenha();
			listaInimigo2.getListInimigo2().get(0).desenha(currentTime);
			listaInimigo3.getListInimigo3().get(0).desenha(currentTime);

			for (int i = 0; i < listaInimigo1.lista.size(); i++)
				listaInimigo1.getListInimigo1().get(i).desenha(currentTime);

			player.projectile.powerUp1.desenha();
			player.powerUp2.desenha();

			GameLib.display();

			// faz uma pausa de modo que cada execução do laço do main loop * demore aproximadamente 5 ms.

			GerenciamentoEstruturas.busyWait(currentTime + 5);
		}

		System.exit(0);
	}
}
