package br.ufcg.edu.simulador;

import br.ufcg.edu.simulador.entidades.BalanceadorDeCarga;
import br.ufcg.edu.simulador.entidades.Cliente;
import br.ufcg.edu.simulador.entidades.ServidorWEB;
import eduni.simjava.Sim_system;

public class SimuladorInternetBanking {
	
	public static void main(String[] args) {
		int n1 = 8;
		int n2 = 15;
		int n3 = 15;
		Sim_system.initialise();
		Sim_system.set_seed((long) (Math.random()*1000000));
		new Cliente("Cliente", 5);
		new BalanceadorDeCarga("BalanceadorDeCargaWEB", 1, n1);
		Sim_system.link_ports("Cliente", "saida", "BalanceadorDeCargaWEB", "entrada");
		for (int i = 1; i <= n1; i++) {
			String nome = "Web" + i;
			new ServidorWEB(nome, 15);
			Sim_system.link_ports("BalanceadorDeCargaWEB", "saida" + i, nome, "entrada");
		}
        Sim_system.set_termination_condition(Sim_system.TIME_ELAPSED, 1000000, true);
        Sim_system.generate_graphs(true);
		Sim_system.run();
	}
	
}
