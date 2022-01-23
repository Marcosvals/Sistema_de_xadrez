package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int turno;
	private Color jogadorAtual;
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Color.WHITE;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Color getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] movimentosPossiveis(PosicionamentoXadrez posicaoOrigem){
		Posicionamento posicionamento = posicaoOrigem.posicionar();
		validarPosicaoOrigem(posicionamento);
		return tabuleiro.peca(posicionamento).possivelMovimento();
	}
	
	public PecaDeXadrez executarMovimentoXadrez(PosicionamentoXadrez posicaoOrigem,PosicionamentoXadrez posicaoDestino) {
		Posicionamento origem = posicaoOrigem.posicionar();
		Posicionamento destino = posicaoDestino.posicionar();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturadaPeca = realizarMovimento(origem, destino);
		proximoTurno();
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
			throw new ExcecaoDoXadrez("Não existe peça na posição de origem");
		}
		if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicionamento)).getColor()) {
			throw new ExcecaoDoXadrez("A peça escolhida não é sua");
		}
		if(!tabuleiro.peca(posicionamento).existeAlgumMovimentoPossível()) {
			throw new ExcecaoDoXadrez("Não existe movimentos possiveis para a peça escolhida");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void validarPosicaoDestino(Posicionamento origem, Posicionamento destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new ExcecaoDoXadrez("A peça escolhida não pode se mover para a posição de destino");
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
