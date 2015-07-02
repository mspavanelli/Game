public class Mediadora {

	Player p;
	Inimigo1 i1;
	Inimigo2 i2;
	InimigoProjectile ip;

	public FiscalizaColisao( Player p, Inimigo1 i1, Inimigo2 i2, InimigoProjectile ip ) {
		this.p = p;
		this.i1 = i1;
		this.i2 = i2;
		this.ip = ip;
	}

	public boolean playerComProjetil() {
		
	}
	
	public boolean playerComInimigo() {}
	
	public boolean inimigoComProjetil() {}
}