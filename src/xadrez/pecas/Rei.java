package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicionamento posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();
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
		
		
		
		return mat;
	}
}
