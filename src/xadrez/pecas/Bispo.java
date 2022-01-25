package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {
	public Bispo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicionamento p = new Posicionamento(0, 0);
		
		//noroeste
		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() - 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() - 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() + 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() - 1, p.getColuna() + 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() + 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1 , p.getColuna() + 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() - 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValues(p.getLinha() + 1, p.getColuna() - 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}

}
