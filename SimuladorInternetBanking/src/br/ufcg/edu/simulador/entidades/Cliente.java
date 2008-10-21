package br.ufcg.edu.simulador.entidades;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import eduni.simjava.distributions.ContinuousGenerator;
import eduni.simjava.distributions.Sim_negexp_obj;

public abstract class Cliente extends Sim_entity {

	private Sim_port saida;

	private ContinuousGenerator geradorClientes;

	public Cliente(String nome, double media) {
		super(nome);
		geradorClientes = new Sim_negexp_obj("geradorClientes", media);
		add_generator(geradorClientes);
		saida = new Sim_port("saida");
		add_port(saida);
	}

	public void body() {
		while (Sim_system.running()) {;
			sim_schedule(saida, 0.0, 0, this);
			sim_pause(geradorClientes.sample());
		}
	}

}
