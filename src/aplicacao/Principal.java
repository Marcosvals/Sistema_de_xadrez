package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicionamentoXadrez;

public class Principal {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaXadrez = new PartidaDeXadrez();
		
		while(true) {
			try {
				UI.limparTela();
				UI.imprimeTabuleiro(partidaXadrez.pegarPecas());
				System.out.println();
				System.out.print("Origem: ");
				PosicionamentoXadrez origem = UI.lerPosicionamentoXadrez(sc);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicionamentoXadrez destino = UI.lerPosicionamentoXadrez(sc);
				
				PecaDeXadrez capturadaPeca = partidaXadrez.executarMovimentoXadrez(origem, destino);
			}
			catch(ExcecaoDoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}
