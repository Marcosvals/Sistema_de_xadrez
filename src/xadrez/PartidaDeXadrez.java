package xadrez;

import tabuleiro.Peca;
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
	
	public PecaDeXadrez[][] pegarPecas(){
		PecaDeXadrez[][] mat = new  PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public PecaDeXadrez executarMovimentoXadrez(PosicionamentoXadrez posicaoOrigem,PosicionamentoXadrez posicaoDestino) {
		Posicionamento origem = posicaoOrigem.posicionar();
		Posicionamento destino = posicaoDestino.posicionar();
		validarPosicaoOrigem(origem);
		Peca capturadaPeca = realizarMovimento(origem, destino);
		return (PecaDeXadrez)capturadaPeca;
	}
	
	private Peca realizarMovimento(Posicionamento origem, Posicionamento destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturadaPeca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return capturadaPeca;
	}
	
	private void validarPosicaoOrigem(Posicionamento posicionamento) {
		if(!tabuleiro.TemUmaPeca(posicionamento)) {
			throw new ExcecaoDoXadrez("N�o existe pe�a na posi��o de origem");
		}
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca,new PosicionamentoXadrez(coluna, linha).posicionar());
	}
	
	private void configuracaoInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Color.WHITE));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Color.BLACK));
	}
	
}
