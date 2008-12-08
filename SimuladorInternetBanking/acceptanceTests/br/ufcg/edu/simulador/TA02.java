package br.ufcg.edu.simulador;

import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class TA02 {

	public static void main(String[] args) {
		List<String> files = new ArrayList<String>();
		files.add("acceptanceTests/tests/TA02.txt");
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(
				new SimuladorInternetBanking(), files);
		eaFacade.executeTests();
		System.out.println(eaFacade.getCompleteResults());
	}

}
