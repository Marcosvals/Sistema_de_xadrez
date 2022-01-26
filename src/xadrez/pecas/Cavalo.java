package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez {
	public Cavalo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}
	
	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicionamento posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicionamento p = new Posicionamento(0,0);

		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() - 2);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		

		p.setValues(posicionamento.getLinha() - 2, posicionamento.getColuna() - 1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posicionamento.getLinha() - 2, posicionamento.getColuna() + 1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() + 2);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() + 2);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posicionamento.getLinha() + 2 , posicionamento.getColuna() + 1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		p.setValues(posicionamento.getLinha() + 2, posicionamento.getColuna() -1);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() - 2);
		if(getTabuleiro().posicionamentoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		return mat;
	}
}
