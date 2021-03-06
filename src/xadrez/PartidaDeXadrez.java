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
	private PecaDeXadrez enPassantVulneravel;
	private PecaDeXadrez promovida;
	
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
	
	public PecaDeXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}
	
	public PecaDeXadrez getPromovida() {
		return promovida;
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
			throw new ExcecaoDoXadrez("Voc? n?o pode se colocar em check");
		}
		
		PecaDeXadrez pecaMoveu = (PecaDeXadrez)tabuleiro.peca(destino);
		
		//Movimento especial promo??o
		promovida = null;
		if(pecaMoveu instanceof Peao) {
			if((pecaMoveu.getColor() == Color.WHITE && destino.getLinha() == 0) || (pecaMoveu.getColor() == Color.BLACK && destino.getLinha() == 7)) {
				promovida = (PecaDeXadrez)tabuleiro.peca(destino);
				promovida = substituirPecaProvomida("D");
			}
		}
		
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		// Movimento Especial en passant'
		if(pecaMoveu instanceof Peao &&(destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulneravel = pecaMoveu;
		}
		else {
			enPassantVulneravel = null;
		}
		
		return (PecaDeXadrez)capturadaPeca;
	}
	
	public PecaDeXadrez substituirPecaProvomida(String tipo) {
		if(promovida == null) {
			throw new IllegalStateException("N?o h? pe?a para ser promovida");
		}
		if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
			return promovida;
		}
		
		Posicionamento pos = promovida.getPosicionamentoXadrez().posicionar();
		Peca p = tabuleiro.removerPeca(pos);
		pecaNoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(tipo, promovida.getColor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecaNoTabuleiro.add(novaPeca);
		return novaPeca;
	}
	
	private PecaDeXadrez novaPeca(String tipo, Color color) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, color);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, color);
		if(tipo.equals("T")) return new Torre(tabuleiro, color);
		return new Rainha(tabuleiro, color);
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
		
		//Movimento especial roque do lado do rei//
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicionamento origemT = new Posicionamento(origem.getLinha(), origem.getColuna() + 3);
			Posicionamento destinoT = new Posicionamento(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContagemMovimento();
		}
		
		//Movimento especial roque do lado da rainha//
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicionamento origemT = new Posicionamento(origem.getLinha(), origem.getColuna() - 4);
			Posicionamento destinoT = new Posicionamento(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContagemMovimento();
		}
		
		//Movimento Especial en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && capturadaPeca == null) {
				Posicionamento posicaoPeao;
				if(p.getColor() == Color.WHITE) {
					posicaoPeao = new Posicionamento(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicionamento(destino.getLinha() - 1, destino.getColuna());
				}
				capturadaPeca = tabuleiro.removerPeca(posicaoPeao);
				pecaCapturada.add(capturadaPeca);
				pecaNoTabuleiro.remove(capturadaPeca);
			}
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
		
		//Movimento especial roque do lado do rei//
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicionamento origemT = new Posicionamento(origem.getLinha(), origem.getColuna() + 3);
			Posicionamento destinoT = new Posicionamento(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContagemMovimento();
		}
				
		//Movimento especial roque do lado da rainha//
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicionamento origemT = new Posicionamento(origem.getLinha(), origem.getColuna() - 4);
			Posicionamento destinoT = new Posicionamento(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContagemMovimento();
		}
		
		//Movimento Especial en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada2 == enPassantVulneravel) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Posicionamento posicaoPeao;
				if(p.getColor() == Color.WHITE) {
					posicaoPeao = new Posicionamento(3, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicionamento(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}
		
	}
	
	private void validarPosicaoOrigem(Posicionamento posicionamento) {
		if(!tabuleiro.TemUmaPeca(posicionamento)) {
			throw new ExcecaoDoXadrez("N?o existe pe?a na posi??o de origem");
		}
		if(jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicionamento)).getColor()) {
			throw new ExcecaoDoXadrez("A pe?a escolhida n?o ? sua");
		}
		if(!tabuleiro.peca(posicionamento).existeAlgumMovimentoPoss?vel()) {
			throw new ExcecaoDoXadrez("N?o existe movimentos possiveis para a pe?a escolhida");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void validarPosicaoDestino(Posicionamento origem, Posicionamento destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new ExcecaoDoXadrez("A pe?a escolhida n?o pode se mover para a posi??o de destino");
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
		throw new IllegalStateException("N?o existe o rei da cor" + color + " no tabuleiro");
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
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Color.WHITE, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Color.WHITE));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Color.WHITE));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Color.WHITE));
        colocarNovaPeca('a', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Color.WHITE, this));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Color.WHITE, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Color.BLACK));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Color.BLACK));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Color.BLACK));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Color.BLACK));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Color.BLACK));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Color.BLACK));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Color.BLACK, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Color.BLACK, this));
	}
	
}
