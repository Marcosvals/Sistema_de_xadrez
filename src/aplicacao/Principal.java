package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicionamentoXadrez;

public class Principal {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturada = new ArrayList<>();
		
		while(true) {
			try {
				UI.limparTela();
				UI.imprimePartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicionamentoXadrez origem = UI.lerPosicionamentoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimeTabuleiro(partidaXadrez.pegarPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicionamentoXadrez destino = UI.lerPosicionamentoXadrez(sc);
				
				PecaDeXadrez capturadaPeca = partidaXadrez.executarMovimentoXadrez(origem, destino);
				
				if(capturadaPeca != null) {
					capturada.add(capturadaPeca);
				}
				
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
