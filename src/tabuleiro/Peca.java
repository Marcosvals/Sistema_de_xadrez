package tabuleiro;

public class Peca { //peca = pe�a//
	
	protected Posicionamento posicionamento;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicionamento = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	
	
}
