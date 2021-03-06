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
		
		while(!partidaXadrez.getCheckMate()) {
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
				
				if(partidaXadrez.getPromovida() != null) {
					System.out.print("Insira a pe?a para promo??o(B/C/T/D): ");
					String tipo = sc.nextLine().toUpperCase();
					while(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
						System.out.print("Valor invalido! Insira a pe?a para promo??o(B/C/T/D): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.substituirPecaProvomida(tipo);
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
		UI.limparTela();
		UI.imprimePartida(partidaXadrez, capturada);
	}
}
