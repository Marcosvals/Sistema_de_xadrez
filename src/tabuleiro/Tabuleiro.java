package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicionamento posicionamento) {
		return pecas[posicionamento.getLinha()][posicionamento.getColuna()];
	}
	
	public void colocarPeca(Peca peca, Posicionamento posicionamento) {
		pecas[posicionamento.getLinha()][posicionamento.getColuna()] = peca;
		peca.posicionamento = posicionamento;
	}
}
