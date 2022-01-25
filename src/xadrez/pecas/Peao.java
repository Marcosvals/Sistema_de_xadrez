package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
		
	}
	
	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicionamento p = new Posicionamento(0, 0);
		
		if(getColor() == Color.WHITE) {
			p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna());
			if(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() - 2, posicionamento.getColuna());
			Posicionamento p2 = new Posicionamento(posicionamento.getLinha() - 1, posicionamento.getColuna());
			if(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p) && getTabuleiro().posicionamentoExiste(p2) && !getTabuleiro().TemUmaPeca(p2) && getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() - 1);
			if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() + 1);
			if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna());
			if(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() + 2, posicionamento.getColuna());
			Posicionamento p2 = new Posicionamento(posicionamento.getLinha() + 1, posicionamento.getColuna());
			if(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p) && getTabuleiro().posicionamentoExiste(p2) && !getTabuleiro().TemUmaPeca(p2) && getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() - 1);
			if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() + 1);
			if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
