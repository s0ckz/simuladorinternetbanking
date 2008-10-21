package br.ufcg.edu.simulador.entidades;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_stat;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.ContinuousGenerator;
import eduni.simjava.distributions.Sim_negexp_obj;

public class BalanceadorDeCarga extends Sim_entity {
	
    private Sim_stat stat;
	
	private Sim_port entrada;
	
	private Sim_port[] saidas;
	
	private ContinuousGenerator geradorAtrasos;

	private int numeroServidores, servidorAtual = -1;

	public BalanceadorDeCarga(String nome, double media, int numeroServidores) {
		super(nome);
		geradorAtrasos = new Sim_negexp_obj("geradorAtrasos", media);
		add_generator(geradorAtrasos);
		entrada = new Sim_port("entrada");
		add_port(entrada);
		stat = new Sim_stat();
		stat.add_measure(Sim_stat.UTILISATION);
		stat.add_measure(Sim_stat.QUEUE_LENGTH);
		stat.add_measure(Sim_stat.THROUGHPUT);
		stat.add_measure(Sim_stat.WAITING_TIME);
		stat.add_measure(Sim_stat.SERVICE_TIME);
		stat.add_measure(Sim_stat.RESIDENCE_TIME);
		stat.add_measure(Sim_stat.ARRIVAL_RATE);
		set_stat(stat);
		this.numeroServidores = numeroServidores;
		saidas = new Sim_port[numeroServidores];
		for (int i = 0; i < numeroServidores; i++) {
			saidas[i] = new Sim_port("saida" + (i + 1));
			add_port(saidas[i]);
		}
	}
	
    public void body() {
    	while (Sim_system.running()) {
            Sim_event e = new Sim_event();
            sim_get_next(e);
            servidorAtual = (servidorAtual + 1) % numeroServidores;
			sim_schedule(saidas[servidorAtual], 0.0, 0, e.get_data());
            sim_process(geradorAtrasos.sample());
            sim_completed(e);
    	}
    }


}
