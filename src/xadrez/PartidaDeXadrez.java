package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int turno;
	private Color jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecaNoTabuleiro = new ArrayList<>();
	private List<Peca> pecaCapturada = new ArrayList<>();
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(testeCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturadaPeca);
			throw new ExcecaoDoXadrez("Você não pode se colocar em check");
		}
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		return (PecaDeXadrez)capturadaPeca;
	}
	
	private Peca realizarMovimento(Posicionamento origem, Posicionamento destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem);
		p.aumentarContagemMovimento();
		Peca capturadaPeca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if(capturadaPeca != null) {
			pecaNoTabuleiro.remove(capturadaPeca);
			pecaCapturada.add(capturadaPeca);
		}
		
		return capturadaPeca;
	}
	
	private void desfazerMovimento(Posicionamento origem, Posicionamento destino, Peca pecaCapturada2) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.diminuirContagemMovimento();
		tabuleiro.colocarPeca(p, origem);
		
		if(pecaCapturada2 != null) {
			tabuleiro.colocarPeca(pecaCapturada2, destino);
			this.pecaCapturada.remove(pecaCapturada2);
			pecaNoTabuleiro.add(pecaCapturada2);
		}
		
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
	
	private Color oponente(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;			
	}
	
	private PecaDeXadrez rei(Color color) {
		List<Peca> lista = pecaNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getColor() == color).collect(Collectors.toList());
		for(Peca p : lista) {
			if(p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor" + color + " no tabuleiro");
	}
	
	private boolean testeCheck(Color color) {
		Posicionamento posicaoRei = rei(color).getPosicionamentoXadrez().posicionar();
		List<Peca> pecaOponente =  pecaNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getColor() == oponente(color)).collect(Collectors.toList());
		for(Peca p : pecaOponente) {
			boolean[][] mat = p.possivelMovimento();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeCheckMate(Color color) {
		if(!testeCheck(color)) {
			return false;
		}
		List<Peca> lista = pecaNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getColor() == color).collect(Collectors.toList());
		for(Peca p : lista) {
			boolean[][] mat = p.possivelMovimento();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicionamento origem = ((PecaDeXadrez)p).getPosicionamentoXadrez().posicionar();
						Posicionamento destino = new Posicionamento(i, j);
						Peca pecaCapturada = realizarMovimento(origem, destino);
						boolean testaCheck = testeCheck(color);
						desfazerMovimento(origem, destino, pecaCapturada);
						if(!testaCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
		
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca,new PosicionamentoXadrez(coluna, linha).posicionar());
		pecaNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Color.WHITE));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Color.WHITE));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Color.WHITE));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Color.WHITE));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Color.WHITE));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.WHITE));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Color.WHITE));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Color.WHITE));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Color.WHITE));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Color.BLACK));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Color.BLACK));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Color.BLACK));
	}
	
}
