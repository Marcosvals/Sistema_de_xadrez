package xadrez;

import tabuleiro.Posicionamento;

public class PosicionamentoXadrez {
	
	private char coluna;
	private int linha;
	
	
	public PosicionamentoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcecaoDoXadrez("Erro instanciando PosicionamentoXadrez. Valores validos são de a1 até h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}


	public char getColuna() {
		return coluna;
	}


	public int getLinha() {
		return linha;
	}
	
	protected Posicionamento posicionar() {
		return new Posicionamento(8 - linha, coluna - 'a');
	}
	
	protected static PosicionamentoXadrez aPartirDoPosicionamento(Posicionamento posicionamento) {
		return new PosicionamentoXadrez((char)('a' - posicionamento.getColuna()), 8 - posicionamento.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
}
