package tabuleiro;

public abstract class Peca { //peca = peça//
	
	protected Posicionamento posicionamento;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicionamento = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] possivelMovimento();
		
	public boolean possivelMovimento(Posicionamento posicionamento) {
		return possivelMovimento()[posicionamento.getLinha()][posicionamento.getColuna()];
	}
	
	public boolean existeAlgumMovimentoPossível() {
		boolean[][] mat = possivelMovimento();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
