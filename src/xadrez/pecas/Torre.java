package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "T";
	}
	
	@Override
	public boolean[][] possivelMovimento() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicionamento p = new Posicionamento(0, 0);
		
		//above
		p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna());
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//left
		p.setValues(posicionamento.getLinha(), posicionamento.getColuna() - 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//right
		p.setValues(posicionamento.getLinha(), posicionamento.getColuna() + 1);
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//below
		p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna());
		while(getTabuleiro().posicionamentoExiste(p) && !getTabuleiro().TemUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if(getTabuleiro().posicionamentoExiste(p) && ExistePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
	
}
