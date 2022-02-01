package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Color color;
	private int contarMovimento;

	public PecaDeXadrez(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getContarMovimento() {
		return contarMovimento;
	}
	
	public void aumentarContagemMovimento() {
		contarMovimento++;
	}
	
	public void diminuirContagemMovimento() {
		contarMovimento--;
	}
	
	public PosicionamentoXadrez getPosicionamentoXadrez() {
		return PosicionamentoXadrez.aPartirDoPosicionamento(posicionamento);
	}
	
	protected boolean existePecaAdversaria(Posicionamento posicionamento) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicionamento);
		return p != null && p.getColor() != color;
	}

}
