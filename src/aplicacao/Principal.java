package aplicacao;

import tabuleiro.Posicionamento;
import tabuleiro.Tabuleiro;
import xadrez.PartidaDeXadrez;

public class Principal {
	public static void main(String[] args) {
		
		PartidaDeXadrez partidaXadrez = new PartidaDeXadrez();
		UI.imprimeTabuleiro(partidaXadrez.getPecas());
		
	}
}
