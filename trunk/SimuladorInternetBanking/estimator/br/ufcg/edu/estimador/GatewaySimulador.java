package br.ufcg.edu.estimador;

import java.io.File;

import br.ufcg.edu.simulador.SimuladorInternetBanking;

public class GatewaySimulador {

	public static void main(String[] args) {
		if (args.length < 17) {
			System.err.println("São necessários 18 argumentos.");
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
		double transitorioInicial = Double.parseDouble(args[17]);
		
		SimuladorInternetBanking simulador = new SimuladorInternetBanking();
		simulador.setGeracaoGraficos(true);
		simulador.setVisualizarGraficos(false);
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
		simulador.setTransitorioInicial(transitorioInicial);
		simulador.simular();
		new File("graficos").mkdir();
		new File("sim_graphs.sjg").renameTo(new File("graficos/sim_graphs_W" + numeroServidoresWeb + "_A" + numeroServidoresApp + "_B" + numeroServidoresBd + "_CA" + taxaMediaChegadaA + "_CB" + taxaMediaChegadaB + "_CC" + taxaMediaChegadaC + ".sjg"));
		
		
		double mediaRespostaServidoresWeb = 0.0;
		double utilizacaoServidoresWeb = 0.0;
		for (int i = 1; i <= numeroServidoresWeb; i++) {
			mediaRespostaServidoresWeb += simulador.getTempoMedioResposta("Web" + i);
			utilizacaoServidoresWeb += simulador.getUtilizacaoMedia("Web" + i);;
		}
		mediaRespostaServidoresWeb /= numeroServidoresWeb;
		utilizacaoServidoresWeb /= numeroServidoresWeb;
		
		double mediaRespostaServidoresApp = 0.0;
		double utilizacaoServidoresApp = 0.0;
		for (int i = 1; i <= numeroServidoresApp; i++) {
			mediaRespostaServidoresApp += simulador.getTempoMedioResposta("APP" + i);
			utilizacaoServidoresApp += simulador.getUtilizacaoMedia("APP" + i);;
		}
		mediaRespostaServidoresApp /= numeroServidoresApp;
		utilizacaoServidoresApp /= numeroServidoresApp;
		
		double mediaRespostaServidoresBd = 0.0;
		double utilizacaoServidoresBd = 0.0;
		for (int i = 1; i <= numeroServidoresBd; i++) {
			mediaRespostaServidoresBd += simulador.getTempoMedioResposta("BD" + i);
			utilizacaoServidoresBd += simulador.getUtilizacaoMedia("BD" + i);;
		}
		mediaRespostaServidoresBd /= numeroServidoresBd;
		utilizacaoServidoresBd /= numeroServidoresBd;
		System.out.println("Fim!");
		System.out.println(mediaRespostaServidoresWeb + " " + mediaRespostaServidoresApp + " " +
				mediaRespostaServidoresBd + " " +
				utilizacaoServidoresWeb + " " + utilizacaoServidoresApp + " " +
				utilizacaoServidoresBd);
	}

}
