public class Mediadora {

	Player p;
	PlayerProjectile pp;
	Inimigo1 i1;
	Inimigo2 i2;
	Inimigo3 i3;
	InimigoProjectile ip;

	public FiscalizaColisao( Player p, PlayerProjectile pp; ,Inimigo1 i1, Inimigo2 i2, Inimigo3 i3; ,InimigoProjectile ip ) {
		this.p = p;
		this.pp = pp;
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		this.ip = ip;
	}

	public boolean playerComProjetil() {

	}
	
	public boolean playerComInimigo() {

	}
	
	public boolean inimigoComProjetil() {

	}
}