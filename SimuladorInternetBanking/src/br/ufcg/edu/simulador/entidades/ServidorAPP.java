package br.ufcg.edu.simulador.entidades;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.ContinuousGenerator;
import eduni.simjava.distributions.Sim_negexp_obj;

public class ServidorAPP extends Sim_entity {
	
    private Sim_stat stat;
	
	private Sim_port entrada, saida;
	
	private ContinuousGenerator geradorAtrasosB, geradorAtrasosC;

	public ServidorAPP(String nome, double taxaB, double taxaC) {
		super(nome);
		geradorAtrasosB = new Sim_negexp_obj("geradorAtrasosB", 1 / taxaB);
		geradorAtrasosC = new Sim_negexp_obj("geradorAtrasosC", 1 / taxaC);
		add_generator(geradorAtrasosB);
		add_generator(geradorAtrasosC);
		entrada = new Sim_port("entrada");
		add_port(entrada);
		saida = new Sim_port("saida");
		add_port(saida);
		stat = new Sim_stat();
		stat.add_measure(Sim_stat.UTILISATION);
//		stat.add_measure(Sim_stat.QUEUE_LENGTH);
//		stat.add_measure(Sim_stat.THROUGHPUT);
//		stat.add_measure(Sim_stat.WAITING_TIME);
//		stat.add_measure(Sim_stat.SERVICE_TIME);
		stat.add_measure(Sim_stat.RESIDENCE_TIME);
//		stat.add_measure(Sim_stat.ARRIVAL_RATE);
		set_stat(stat);
	}
	
    public void body() {
    	while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(e);
            //TODO: Ver o que fazer aqui, pois no final da simula��o, chegam eventos desconhecidos.
            Object dados = e.get_data();
			if (dados != null) {
            	if (dados instanceof ClienteClasseB) {
            		sim_process(geradorAtrasosB.sample());
            		sim_schedule(saida, 0.0, 0, dados);
            	} else if (dados instanceof ClienteClasseC) {
            		sim_process(geradorAtrasosC.sample());
            		sim_schedule(saida, 0.0, 0, dados);
            	}
            } else
            	sim_process(0);
            sim_completed(e);
    	}
    }

}
