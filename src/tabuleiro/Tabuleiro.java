package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new ExcecaoDoTabuleiro("Erro criando tabuleiro: é necessario que haja pelo menos 1 linha e 1 coluna ");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if(!posicionamentoExiste(linha, coluna)) {
			throw new ExcecaoDoTabuleiro("A posição não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicionamento posicionamento) {
		if(!posicionamentoExiste(posicionamento)) {
			throw new ExcecaoDoTabuleiro("A posição não existe no tabuleiro");
		}
		return pecas[posicionamento.getLinha()][posicionamento.getColuna()];
	}
	
	public void colocarPeca(Peca peca, Posicionamento posicionamento) {
		if(TemUmaPeca(posicionamento)) {
			throw new ExcecaoDoTabuleiro("Ja existe uma peça nessa posição: " + posicionamento);
		}
		pecas[posicionamento.getLinha()][posicionamento.getColuna()] = peca;
		peca.posicionamento = posicionamento;
	}
	
	public Peca removerPeca(Posicionamento posicionamento) {
		if(!posicionamentoExiste(posicionamento)) {
			throw new ExcecaoDoTabuleiro("A posição não existe no tabuleiro");
		}
		if(peca(posicionamento) == null) {
			return null;
		}
		Peca aux = peca(posicionamento);
		aux.posicionamento = null;
		pecas[posicionamento.getLinha()][posicionamento.getColuna()] = null;
		return aux;
	}
	
	private boolean posicionamentoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicionamentoExiste(Posicionamento posicionamento) {
		return posicionamentoExiste(posicionamento.getLinha(), posicionamento.getColuna());
	}
	
	public boolean TemUmaPeca(Posicionamento posicionamento) {
		if(!posicionamentoExiste(posicionamento)) {
			throw new ExcecaoDoTabuleiro("A posição não existe no tabuleiro");
		}
		return peca(posicionamento) != null;
	}
}
