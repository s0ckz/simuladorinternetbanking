package br.ufcg.edu.simulador;

import java.io.File;

import br.ufcg.edu.simulador.entidades.BalanceadorDeCarga;
import br.ufcg.edu.simulador.entidades.ClienteClasseA;
import br.ufcg.edu.simulador.entidades.ClienteClasseB;
import br.ufcg.edu.simulador.entidades.ClienteClasseC;
import br.ufcg.edu.simulador.entidades.ServidorAPP;
import br.ufcg.edu.simulador.entidades.ServidorBD;
import br.ufcg.edu.simulador.entidades.ServidorWEB;
import eduni.simjava.SJGV;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;


public class SimuladorInternetBanking {
	
	private static final double ZERO_RATE = 0.00000001;
	private static final String BD = "BD";
	private static final String APP = "APP";
	private static final String WEB = "Web";
	private static final String ENTRADA = "entrada";
	private static final String SAIDA = "saida";
	private static final String BALANCEADOR_DE_CARGA_B_D = "BalanceadorDeCargaBD";
	private static final String BALANCEADOR_DE_CARGA_A_P_P = "BalanceadorDeCargaAPP";
	private static final String BALANCEADOR_DE_CARGA_W_E_B = "BalanceadorDeCargaWEB";
	private static final String CLIENTE_CLASSE_C = "ClienteClasseC";
	private static final String CLIENTE_CLASSE_B = "ClienteClasseB";
	private static final String CLIENTE_CLASSE_A = "ClienteClasseA";
	
	private int numeroServidoresWeb = 8;
	private int numeroServidoresApp = 6;
	private int numeroServidoresBd = 2;
	private double taxaMediaChegadaA = 150;
	private double taxaMediaChegadaB = 70;
	private double taxaMediaChegadaC = 30;
	private double taxaMediaProcessamentoWebA = 300;
	private double taxaMediaProcessamentoWebB = 150;
	private double taxaMediaProcessamentoWebC = 100;
	private double taxaMediaProcessamentoBdB = 100;
	private double taxaMediaProcessamentoBdC = 50;
	private double taxaMediaProcessamentoAppB = 150;
	private double taxaMediaProcessamentoAppC = 100;
	private double taxaMediaProcessamentoBcWeb = 1000;
	private double taxaMediaProcessamentoBcApp = 1000;
	private double taxaMediaProcessamentoBcBd = 1000;
	private double tempoSimulacao = 50;
	
	private long semente = (long) (Math.random()*1000000);
	
	private boolean geracaoGraficos = true;

	public void simular() {
		Sim_system.initialise();
		Sim_system.set_seed(semente);
		
		if (taxaMediaChegadaA > ZERO_RATE)
			new ClienteClasseA(CLIENTE_CLASSE_A, taxaMediaChegadaA);
		
		if (taxaMediaChegadaB > ZERO_RATE)
			new ClienteClasseB(CLIENTE_CLASSE_B, taxaMediaChegadaB);
		
		if (taxaMediaChegadaC > ZERO_RATE)
			new ClienteClasseC(CLIENTE_CLASSE_C, taxaMediaChegadaC);
		
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_W_E_B, taxaMediaProcessamentoBcWeb, numeroServidoresWeb);
		
		if (existemClientesClasseBouC()) {
			new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_A_P_P, taxaMediaProcessamentoBcApp, numeroServidoresApp);
			new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_B_D, taxaMediaProcessamentoBcBd, numeroServidoresBd);
		}
		
		if (taxaMediaChegadaA > ZERO_RATE)
			Sim_system.link_ports(CLIENTE_CLASSE_A, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		
		if (taxaMediaChegadaB > ZERO_RATE)
			Sim_system.link_ports(CLIENTE_CLASSE_B, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		
		if (taxaMediaChegadaC > ZERO_RATE)
			Sim_system.link_ports(CLIENTE_CLASSE_C, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		
		for (int i = 1; i <= numeroServidoresWeb; i++) {
			String nome = WEB + i;
			new ServidorWEB(nome, taxaMediaProcessamentoWebA, taxaMediaProcessamentoWebB, taxaMediaProcessamentoWebC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_W_E_B, SAIDA + i, nome, ENTRADA);
			if (existemClientesClasseBouC())
				Sim_system.link_ports(WEB + i, SAIDA, BALANCEADOR_DE_CARGA_A_P_P, ENTRADA);
		}
		for (int i = 1; i <= numeroServidoresApp; i++) {
			String nome = APP + i;
			new ServidorAPP(nome, taxaMediaProcessamentoAppB, taxaMediaProcessamentoAppC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_A_P_P, SAIDA + i, nome, ENTRADA);
			Sim_system.link_ports(APP + i, SAIDA, BALANCEADOR_DE_CARGA_B_D, ENTRADA);
		}
		for (int i = 1; i <= numeroServidoresBd; i++) {
			String nome = BD + i;
			new ServidorBD(nome, taxaMediaProcessamentoBdB, taxaMediaProcessamentoBdC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_B_D, SAIDA + i, nome, ENTRADA);
		}
        Sim_system.set_termination_condition(Sim_system.TIME_ELAPSED, tempoSimulacao, true);
        Sim_system.generate_graphs(isGeracaoGraficos());
		Sim_system.run();
		if (isGeracaoGraficos()) {
			if (new File("sim_graphs.sjg").exists())
				SJGV.main(new String[] { "sim_graphs.sjg" });
			else
				SJGV.main(new String[] {});
		}
	}
	
	public void setNumeroServidoresWeb(int numeroServidoresWeb) {
		this.numeroServidoresWeb = numeroServidoresWeb;
	}

	public void setNumeroServidoresApp(int numeroServidoresApp) {
		this.numeroServidoresApp = numeroServidoresApp;
	}

	public void setNumeroServidoresBd(int numeroServidoresBd) {
		this.numeroServidoresBd = numeroServidoresBd;
	}

	public void setTaxaMediaChegadaA(double taxaMediaChegadaA) {
		if (taxaMediaChegadaA == 0)
			taxaMediaChegadaA = ZERO_RATE;
		this.taxaMediaChegadaA = taxaMediaChegadaA;
	}

	public void setTaxaMediaChegadaB(double taxaMediaChegadaB) {
		if (taxaMediaChegadaB == 0)
			taxaMediaChegadaB = ZERO_RATE;
		this.taxaMediaChegadaB = taxaMediaChegadaB;
	}

	public void setTaxaMediaChegadaC(double taxaMediaChegadaC) {
		if (taxaMediaChegadaC == 0)
			taxaMediaChegadaC = ZERO_RATE;
		this.taxaMediaChegadaC = taxaMediaChegadaC;
	}

	public void setTaxaMediaProcessamentoWebA(double taxaMediaProcessamentoWebA) {
		if (taxaMediaProcessamentoWebA == 0)
			taxaMediaProcessamentoWebA = ZERO_RATE;
		this.taxaMediaProcessamentoWebA = taxaMediaProcessamentoWebA;
	}

	public void setTaxaMediaProcessamentoWebB(double taxaMediaProcessamentoWebB) {
		if (taxaMediaProcessamentoWebB == 0)
			taxaMediaProcessamentoWebB = ZERO_RATE;
		this.taxaMediaProcessamentoWebB = taxaMediaProcessamentoWebB;
	}

	public void setTaxaMediaProcessamentoWebC(double taxaMediaProcessamentoWebC) {
		if (taxaMediaProcessamentoWebC == 0)
			taxaMediaProcessamentoWebC = ZERO_RATE;
		this.taxaMediaProcessamentoWebC = taxaMediaProcessamentoWebC;
	}

	public void setTaxaMediaProcessamentoBdB(double taxaMediaProcessamentoBdB) {
		if (taxaMediaProcessamentoBdB == 0)
			taxaMediaProcessamentoBdB = ZERO_RATE;
		this.taxaMediaProcessamentoBdB = taxaMediaProcessamentoBdB;
	}

	public void setTaxaMediaProcessamentoBdC(double taxaMediaProcessamentoBdC) {
		if (taxaMediaProcessamentoBdC == 0)
			taxaMediaProcessamentoBdC = ZERO_RATE;
		this.taxaMediaProcessamentoBdC = taxaMediaProcessamentoBdC;
	}

	public void setTaxaMediaProcessamentoAppB(double taxaMediaProcessamentoAppB) {
		if (taxaMediaProcessamentoAppB == 0)
			taxaMediaProcessamentoAppB = ZERO_RATE;
		this.taxaMediaProcessamentoAppB = taxaMediaProcessamentoAppB;
	}

	public void setTaxaMediaProcessamentoAppC(double taxaMediaProcessamentoAppC) {
		if (taxaMediaProcessamentoAppC == 0)
			taxaMediaProcessamentoAppC = ZERO_RATE;
		this.taxaMediaProcessamentoAppC = taxaMediaProcessamentoAppC;
	}

	public void setTaxaMediaProcessamentoBcWeb(double taxaMediaProcessamentoBcWeb) {
		if (taxaMediaProcessamentoBcWeb == 0)
			taxaMediaProcessamentoBcWeb = ZERO_RATE;
		this.taxaMediaProcessamentoBcWeb = taxaMediaProcessamentoBcWeb;
	}

	public void setTaxaMediaProcessamentoBcApp(double taxaMediaProcessamentoBcApp) {
		if (taxaMediaProcessamentoBcApp == 0)
			taxaMediaProcessamentoBcApp = ZERO_RATE;
		this.taxaMediaProcessamentoBcApp = taxaMediaProcessamentoBcApp;
	}

	public void setTaxaMediaProcessamentoBcBd(double taxaMediaProcessamentoBcBd) {
		if (taxaMediaProcessamentoBcBd == 0)
			taxaMediaProcessamentoBcBd = ZERO_RATE;
		this.taxaMediaProcessamentoBcBd = taxaMediaProcessamentoBcBd;
	}

	public void setTempoSimulacao(double tempoSimulacao) {
		this.tempoSimulacao = tempoSimulacao;
	}

	public void setSemente(long semente) {
		this.semente = semente;
	}
	
	public double getUtilizacaoMedia(String name) {
		return getSimStat(name).average(Sim_stat.UTILISATION);
	}
	
	public double getVazaoMedia(String name) {
		return getSimStat(name).average(Sim_stat.THROUGHPUT);
	}
	
	public double getTaxaMediaChegada(String name) {
		return getSimStat(name).average(Sim_stat.ARRIVAL_RATE);
	}
	
	public double getTamanhoMedioFila(String name) {
		return getSimStat(name).average(Sim_stat.QUEUE_LENGTH);
	}
	
	public double getTempoMedioResposta(String name) {
		return getSimStat(name).average(Sim_stat.RESIDENCE_TIME);
	}
	
	public double getTempoMedioServico(String name) {
		return getSimStat(name).average(Sim_stat.SERVICE_TIME);
	}
	
	public double getTempoMedioEspera(String name) {
		return getSimStat(name).average(Sim_stat.WAITING_TIME);
	}

	public int getNumeroClientesChegaram(String name) {
		return getSimStat(name).count(Sim_stat.ARRIVAL_RATE);
	}

	public int getNumeroClientesProcessados(String name) {
		return getSimStat(name).count(Sim_stat.THROUGHPUT);
	}

	public boolean isGeracaoGraficos() {
		return geracaoGraficos;
	}

	public void setGeracaoGraficos(boolean generatingGraphs) {
		this.geracaoGraficos = generatingGraphs;
	}	
	
	private Sim_stat getSimStat(String name) {
		return Sim_system.get_entity(name).get_stat();
	}
	

	private boolean existemClientesClasseBouC() {
		return taxaMediaChegadaB > ZERO_RATE || taxaMediaChegadaC > ZERO_RATE;
	}
	
}
