package br.ufcg.edu.estimador;

import br.ufcg.edu.simulador.SimuladorInternetBanking;

public class GatewaySimulador {

	public static void main(String[] args) {
		if (args.length < 17) {
			System.err.println("São necessários 17 argumentos.");
			System.exit(1);
		}
		
		int numeroServidoresWeb = Integer.parseInt(args[0]);
		int numeroServidoresApp = Integer.parseInt(args[1]);
		int numeroServidoresBd = Integer.parseInt(args[2]);
		double taxaMediaChegadaA = Double.parseDouble(args[3]);
		double taxaMediaChegadaB = Double.parseDouble(args[4]);
		double taxaMediaChegadaC = Double.parseDouble(args[5]);
		double taxaMediaProcessamentoWebA = Double.parseDouble(args[6]);
		double taxaMediaProcessamentoWebB = Double.parseDouble(args[7]);
		double taxaMediaProcessamentoWebC = Double.parseDouble(args[8]);
		double taxaMediaProcessamentoBdB = Double.parseDouble(args[9]);
		double taxaMediaProcessamentoBdC = Double.parseDouble(args[10]);
		double taxaMediaProcessamentoAppB = Double.parseDouble(args[11]);
		double taxaMediaProcessamentoAppC = Double.parseDouble(args[12]);
		double taxaMediaProcessamentoBcWeb = Double.parseDouble(args[13]);
		double taxaMediaProcessamentoBcApp = Double.parseDouble(args[14]);
		double taxaMediaProcessamentoBcBd = Double.parseDouble(args[15]);
		double tempoSimulacao = Double.parseDouble(args[16]);
		
		SimuladorInternetBanking simulador = new SimuladorInternetBanking();
		simulador.setGeracaoGraficos(false);
		simulador.setNumeroServidoresWeb(numeroServidoresWeb);
		simulador.setNumeroServidoresApp(numeroServidoresApp);
		simulador.setNumeroServidoresBd(numeroServidoresBd);
		simulador.setTaxaMediaChegadaA(taxaMediaChegadaA);
		simulador.setTaxaMediaChegadaB(taxaMediaChegadaB);
		simulador.setTaxaMediaChegadaC(taxaMediaChegadaC);
		simulador.setTaxaMediaProcessamentoWebA(taxaMediaProcessamentoWebA);
		simulador.setTaxaMediaProcessamentoWebB(taxaMediaProcessamentoWebB);
		simulador.setTaxaMediaProcessamentoWebC(taxaMediaProcessamentoWebC);
		simulador.setTaxaMediaProcessamentoBdB(taxaMediaProcessamentoBdB);
		simulador.setTaxaMediaProcessamentoBdC(taxaMediaProcessamentoBdC);
		simulador.setTaxaMediaProcessamentoAppB(taxaMediaProcessamentoAppB);
		simulador.setTaxaMediaProcessamentoAppC(taxaMediaProcessamentoAppC);
		simulador.setTaxaMediaProcessamentoBcWeb(taxaMediaProcessamentoBcWeb);
		simulador.setTaxaMediaProcessamentoBcApp(taxaMediaProcessamentoBcApp);
		simulador.setTaxaMediaProcessamentoBcBd(taxaMediaProcessamentoBcBd);
		simulador.setTempoSimulacao(tempoSimulacao);
		simulador.simular();
//		System.out.println(simulador.getUtilizacaoMedia("Web1"));
		System.out.println("Fim!");
	}

}
