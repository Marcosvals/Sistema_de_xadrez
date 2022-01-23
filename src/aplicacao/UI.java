package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Color;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicionamentoXadrez;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicionamentoXadrez lerPosicionamentoXadrez(Scanner sc) {
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new PosicionamentoXadrez(coluna,linha);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Erro instanciando PosicionamentoXadrez. Valores validos são de a1 até h8");
		}
	}
	
	public static void imprimePartida(PartidaDeXadrez partidaXadrez, List<PecaDeXadrez> capturada) {
		imprimeTabuleiro(partidaXadrez.pegarPecas());
		System.out.println();
		imprimePecaCapturada(capturada);
		System.out.println();
		System.out.println("Turno : " + partidaXadrez.getTurno());
		System.out.println("Aguardando jogador: " + partidaXadrez.getJogadorAtual());
	}
	
	public static void imprimeTabuleiro(PecaDeXadrez[][] pecas) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for(int j=0;j<pecas.length; j++) {
				imprimePeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void imprimeTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for(int j=0;j<pecas.length; j++) {
				imprimePeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	private static void imprimePeca(PecaDeXadrez peca, boolean fundo) {
		if(fundo) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (peca.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void imprimePecaCapturada(List<PecaDeXadrez> capturada) {
		List<PecaDeXadrez> white = capturada.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<PecaDeXadrez> black = capturada.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas:");
		System.out.println("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.println("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
		
	}
	
}
