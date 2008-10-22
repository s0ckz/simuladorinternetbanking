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
import eduni.simjava.Sim_system;


public class SimuladorInternetBanking {
	
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
	private double taxaMediaChegadaA = 5;
	private double taxaMediaChegadaB = 15;
	private double taxaMediaChegadaC = 14;
	private double taxaMediaProcessamentoWebA = 5;
	private double taxaMediaProcessamentoWebB = 15;
	private double taxaMediaProcessamentoWebC = 20;
	private double taxaMediaProcessamentoBdB = 15;
	private double taxaMediaProcessamentoBdC = 40;
	private double taxaMediaProcessamentoAppB = 15;
	private double taxaMediaProcessamentoAppC = 40;
	private double taxaMediaProcessamentoBcWeb = 100;
	private double taxaMediaProcessamentoBcApp = 100;
	private double taxaMediaProcessamentoBcBd = 100;
	private double tempoSimulacao = 100000;

	public void simular() {
		Sim_system.initialise();
		Sim_system.set_seed((long) (Math.random()*1000000));
		new ClienteClasseA(CLIENTE_CLASSE_A, taxaMediaChegadaA);
		new ClienteClasseB(CLIENTE_CLASSE_B, taxaMediaChegadaB);
		new ClienteClasseC(CLIENTE_CLASSE_C, taxaMediaChegadaC);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_W_E_B, taxaMediaProcessamentoBcWeb, numeroServidoresWeb);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_A_P_P, taxaMediaProcessamentoBcApp, numeroServidoresApp);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_B_D, taxaMediaProcessamentoBcBd, numeroServidoresBd);
		Sim_system.link_ports(CLIENTE_CLASSE_A, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		Sim_system.link_ports(CLIENTE_CLASSE_B, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		Sim_system.link_ports(CLIENTE_CLASSE_C, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		for (int i = 1; i <= numeroServidoresWeb; i++) {
			String nome = WEB + i;
			new ServidorWEB(nome, taxaMediaProcessamentoWebA, taxaMediaProcessamentoWebB, taxaMediaProcessamentoWebC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_W_E_B, SAIDA + i, nome, ENTRADA);
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
        Sim_system.generate_graphs(true);
		Sim_system.run();
		if (new File("sim_graphs.sjg").exists())
			SJGV.main(new String[] { "sim_graphs.sjg" });
		else
			SJGV.main(new String[] {});
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
			taxaMediaChegadaA = 0.00000001;
		this.taxaMediaChegadaA = taxaMediaChegadaA;
	}

	public void setTaxaMediaChegadaB(double taxaMediaChegadaB) {
		if (taxaMediaChegadaB == 0)
			taxaMediaChegadaB = 0.00000001;
		this.taxaMediaChegadaB = taxaMediaChegadaB;
	}

	public void setTaxaMediaChegadaC(double taxaMediaChegadaC) {
		if (taxaMediaChegadaC == 0)
			taxaMediaChegadaC = 0.00000001;
		this.taxaMediaChegadaC = taxaMediaChegadaC;
	}

	public void setTaxaMediaProcessamentoWebA(double taxaMediaProcessamentoWebA) {
		if (taxaMediaProcessamentoWebA == 0)
			taxaMediaProcessamentoWebA = 0.00000001;
		this.taxaMediaProcessamentoWebA = taxaMediaProcessamentoWebA;
	}

	public void setTaxaMediaProcessamentoWebB(double taxaMediaProcessamentoWebB) {
		if (taxaMediaProcessamentoWebB == 0)
			taxaMediaProcessamentoWebB = 0.00000001;
		this.taxaMediaProcessamentoWebB = taxaMediaProcessamentoWebB;
	}

	public void setTaxaMediaProcessamentoWebC(double taxaMediaProcessamentoWebC) {
		if (taxaMediaProcessamentoWebC == 0)
			taxaMediaProcessamentoWebC = 0.00000001;
		this.taxaMediaProcessamentoWebC = taxaMediaProcessamentoWebC;
	}

	public void setTaxaMediaProcessamentoBdB(double taxaMediaProcessamentoBdB) {
		if (taxaMediaProcessamentoBdB == 0)
			taxaMediaProcessamentoBdB = 0.00000001;
		this.taxaMediaProcessamentoBdB = taxaMediaProcessamentoBdB;
	}

	public void setTaxaMediaProcessamentoBdC(double taxaMediaProcessamentoBdC) {
		if (taxaMediaProcessamentoBdC == 0)
			taxaMediaProcessamentoBdC = 0.00000001;
		this.taxaMediaProcessamentoBdC = taxaMediaProcessamentoBdC;
	}

	public void setTaxaMediaProcessamentoAppB(double taxaMediaProcessamentoAppB) {
		if (taxaMediaProcessamentoAppB == 0)
			taxaMediaProcessamentoAppB = 0.00000001;
		this.taxaMediaProcessamentoAppB = taxaMediaProcessamentoAppB;
	}

	public void setTaxaMediaProcessamentoAppC(double taxaMediaProcessamentoAppC) {
		if (taxaMediaProcessamentoAppC == 0)
			taxaMediaProcessamentoAppC = 0.00000001;
		this.taxaMediaProcessamentoAppC = taxaMediaProcessamentoAppC;
	}

	public void setTaxaMediaProcessamentoBcWeb(double taxaMediaProcessamentoBcWeb) {
		if (taxaMediaProcessamentoBcWeb == 0)
			taxaMediaProcessamentoBcWeb = 0.00000001;
		this.taxaMediaProcessamentoBcWeb = taxaMediaProcessamentoBcWeb;
	}

	public void setTaxaMediaProcessamentoBcApp(double taxaMediaProcessamentoBcApp) {
		if (taxaMediaProcessamentoBcApp == 0)
			taxaMediaProcessamentoBcApp = 0.00000001;
		this.taxaMediaProcessamentoBcApp = taxaMediaProcessamentoBcApp;
	}

	public void setTaxaMediaProcessamentoBcBd(double taxaMediaProcessamentoBcBd) {
		if (taxaMediaProcessamentoBcBd == 0)
			taxaMediaProcessamentoBcBd = 0.00000001;
		this.taxaMediaProcessamentoBcBd = taxaMediaProcessamentoBcBd;
	}

	public void setTempoSimulacao(double tempoSimulacao) {
		this.tempoSimulacao = tempoSimulacao;
	}

}
