package xadrez;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		configuracaoInicial();
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new  PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void configuracaoInicial() {
		tabuleiro.colocarPeca(new Torre(tabuleiro, Color.WHITE), new Posicionamento(2, 1));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Color.WHITE), new Posicionamento(0,3));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Color.BLACK), new Posicionamento(7,3));
	}
	
}
