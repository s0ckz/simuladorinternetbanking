package br.ufcg.edu.estimador;

import java.util.Collection;
import java.util.LinkedList;

import br.ufcg.edu.simulador.SimuladorInternetBanking;

public class GeradorSaidaGrafico {

	private static void imprimeResultados(SimuladorInternetBanking simulador, String[] nomes, int[] numeroServidores) {
		int i = 0;
		Collection<Double> listaUtilizacao = new LinkedList<Double>();
		Collection<Double> listaResposta = new LinkedList<Double>();
		for (String nome : nomes) {
			double utilizacao = 0.0;
			double resposta = 0.0;
			for (int j = 1; j <= numeroServidores[i]; j++) {
				utilizacao += simulador.getUtilizacaoMedia(nome + j);
				resposta += simulador.getTempoMedioResposta(nome + j);
			}
			utilizacao /= numeroServidores[i];
			resposta /= numeroServidores[i];
			listaUtilizacao.add(utilizacao);
			listaResposta.add(resposta);
			i++;
		}
		System.out.println(listaUtilizacao.toString().replaceAll("[\\[\\]]", "").replaceAll(",\\s?", "\t").replace(".", ","));
		System.out.println(listaResposta.toString().replaceAll("[\\[\\]]", "").replaceAll(",\\s?", "\t").replace(".", ","));
	}
	
	public static void main(String[] args) {
		double total = 648.0;
		SimuladorInternetBanking simulador = new SimuladorInternetBanking();
		simulador.setTaxaMediaChegadaA(0.45 * total);
		simulador.setTaxaMediaChegadaB(0.30 * total);
		simulador.setTaxaMediaChegadaC(0.25 * total);
		simulador.setGeracaoGraficos(false);
		simulador.simular();
		imprimeResultados(simulador, new String[] { "Web", "APP", "BD" }, new int[] {7, 6, 8});
	}

}
