package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
	
	private PartidaDeXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Color color, PartidaDeXadrez partidaXadrez) {
		super(tabuleiro, color);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicionamento posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testarTorreRoque(Posicionamento posicionamento) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicionamento);
		return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContarMovimento() == 0;	
	}

	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicionamento p = new Posicionamento(0,0);
		
		//acima
		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna());
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna());
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//esquerda
		p.setValues(posicionamento.getLinha(), posicionamento.getColuna() -1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setValues(posicionamento.getLinha(), posicionamento.getColuna() +1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste
		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() -1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		p.setValues(posicionamento.getLinha() - 1 , posicionamento.getColuna() + 1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		//sudoeste
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() -1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() + 1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Movimento especial roque
		if(getContarMovimento() == 0 && !partidaXadrez.getCheck()) {
			//Movimento especial roque do lado do rei
			Posicionamento posT1 = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() + 3);
			if(testarTorreRoque(posT1)) {
				Posicionamento p1 =  new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() + 1);
				Posicionamento p2 =  new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() + 2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicionamento.getLinha()][posicionamento.getColuna() + 2] = true;
				}
			}
			//Movimento especial roque do lado da rainha
			Posicionamento posT2 = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 4);
			if(testarTorreRoque(posT2)) {
				Posicionamento p1 =  new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 1);
				Posicionamento p2 =  new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 2);
				Posicionamento p3 =  new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null &&  getTabuleiro().peca(p3) == null) {
					mat[posicionamento.getLinha()][posicionamento.getColuna() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
