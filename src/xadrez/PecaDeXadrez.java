package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Color color;

	public PecaDeXadrez(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean ExistePecaAdversaria(Posicionamento posicionamento) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicionamento);
		return p != null && p.getColor() != color;
	}

}
