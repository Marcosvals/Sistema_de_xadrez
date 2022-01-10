package aplicacao;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicionamentoXadrez;

public class Principal {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaXadrez = new PartidaDeXadrez();
		
		while(true) {
			UI.imprimeTabuleiro(partidaXadrez.pegarPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicionamentoXadrez origem = UI.lerPosicionamentoXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicionamentoXadrez destino = UI.lerPosicionamentoXadrez(sc);
			
			PecaDeXadrez capturadaPeca = partidaXadrez.executarMovimentoXadrez(origem, destino);
		}
	}
}
