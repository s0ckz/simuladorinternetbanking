package br.ufcg.edu.simulador;

import br.ufcg.edu.simulador.entidades.BalanceadorDeCarga;
import br.ufcg.edu.simulador.entidades.ClienteClasseA;
import br.ufcg.edu.simulador.entidades.ClienteClasseB;
import br.ufcg.edu.simulador.entidades.ClienteClasseC;
import br.ufcg.edu.simulador.entidades.ServidorAPP;
import br.ufcg.edu.simulador.entidades.ServidorBD;
import br.ufcg.edu.simulador.entidades.ServidorWEB;
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

	public static void main(String[] args) {
		int numeroServidoresWeb = 8;
		int numeroServidoresApp = 6;
		int numeroServidoresBd = 2;
		double tempoMedioChegadaA = 5;
		double tempoMedioChegadaB = 15;
		double tempoMedioChegadaC = 14;
		double tempoMedioProcessamentoWebA = 5;
		double tempoMedioProcessamentoWebB = 15;
		double tempoMedioProcessamentoWebC = 20;
		double tempoMedioProcessamentoBdB = 15;
		double tempoMedioProcessamentoBdC = 40;
		double tempoMedioProcessamentoAppB = 15;
		double tempoMedioProcessamentoAppC = 40;
		double tempoMedioProcessamentoBcWeb = 1;
		double tempoMedioProcessamentoBcApp = 1;
		double tempoMedioProcessamentoBcBd = 1;
		double tempoSimulacao = 100000;
		
		Sim_system.initialise();
		Sim_system.set_seed((long) (Math.random()*1000000));
		new ClienteClasseA(CLIENTE_CLASSE_A, tempoMedioChegadaA);
		new ClienteClasseB(CLIENTE_CLASSE_B, tempoMedioChegadaB);
		new ClienteClasseC(CLIENTE_CLASSE_C, tempoMedioChegadaC);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_W_E_B, tempoMedioProcessamentoBcWeb, numeroServidoresWeb);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_A_P_P, tempoMedioProcessamentoBcApp, numeroServidoresApp);
		new BalanceadorDeCarga(BALANCEADOR_DE_CARGA_B_D, tempoMedioProcessamentoBcBd, numeroServidoresBd);
		Sim_system.link_ports(CLIENTE_CLASSE_A, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		Sim_system.link_ports(CLIENTE_CLASSE_B, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		Sim_system.link_ports(CLIENTE_CLASSE_C, SAIDA, BALANCEADOR_DE_CARGA_W_E_B, ENTRADA);
		for (int i = 1; i <= numeroServidoresWeb; i++) {
			String nome = WEB + i;
			new ServidorWEB(nome, tempoMedioProcessamentoWebA, tempoMedioProcessamentoWebB, tempoMedioProcessamentoWebC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_W_E_B, SAIDA + i, nome, ENTRADA);
			Sim_system.link_ports(WEB + i, SAIDA, BALANCEADOR_DE_CARGA_A_P_P, ENTRADA);
		}
		for (int i = 1; i <= numeroServidoresApp; i++) {
			String nome = APP + i;
			new ServidorAPP(nome, tempoMedioProcessamentoAppB, tempoMedioProcessamentoAppC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_A_P_P, SAIDA + i, nome, ENTRADA);
			Sim_system.link_ports(APP + i, SAIDA, BALANCEADOR_DE_CARGA_B_D, ENTRADA);
		}
		for (int i = 1; i <= numeroServidoresBd; i++) {
			String nome = BD + i;
			new ServidorBD(nome, tempoMedioProcessamentoBdB, tempoMedioProcessamentoBdC);
			Sim_system.link_ports(BALANCEADOR_DE_CARGA_B_D, SAIDA + i, nome, ENTRADA);
		}
        Sim_system.set_termination_condition(Sim_system.TIME_ELAPSED, tempoSimulacao, true);
        Sim_system.generate_graphs(true);
		Sim_system.run();
	}
	
}
