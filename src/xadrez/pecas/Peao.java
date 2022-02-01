package xadrez.pecas;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {
	
	private PartidaDeXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Color color, PartidaDeXadrez partidaXadrez) {
		super(tabuleiro, color);
		this.partidaXadrez = partidaXadrez;
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
			if(getTabuleiro().posicionamentoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() - 1, posicionamento.getColuna() + 1);
			if(getTabuleiro().posicionamentoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimento Especial en passant brancas
			if(posicionamento.getLinha() == 3) {
				Posicionamento esquerda = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 1);
				if(getTabuleiro().posicionamentoExiste(esquerda) && existePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha()- 1][esquerda.getColuna()] = true;
				}
				Posicionamento direita = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() + 1);
				if(getTabuleiro().posicionamentoExiste(direita) && existePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha()- 1][direita.getColuna()] = true;
				}
				
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
			if(getTabuleiro().posicionamentoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValues(posicionamento.getLinha() + 1, posicionamento.getColuna() + 1);
			if(getTabuleiro().posicionamentoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// Movimento especial en passant pretas
			if(posicionamento.getLinha() == 4) {
				Posicionamento esquerda = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() - 1);
				if(getTabuleiro().posicionamentoExiste(esquerda) && existePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicionamento direita = new Posicionamento(posicionamento.getLinha(), posicionamento.getColuna() + 1);
				if(getTabuleiro().posicionamentoExiste(direita) && existePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
				
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
