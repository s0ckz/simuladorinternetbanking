package br.ufcg.edu.estimador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EstimadorServidores {

	private static int NUMERO_MAXIMO_SERVIDORES = 24;
	private static double[][] TAXAS_CHEGADA_EM_MEDIA = { { 81, 54, 45 },
			{ 162, 108, 90 }, { 324, 216, 180 } };
	private static double[] TAXAS_PROCESSAMENTO_WEB_EM_MEDIA = { 200, 150, 100 };
	private static double[] TAXAS_PROCESSAMENTO_APP_EM_MEDIA = { 0, 120, 80 };
	private static double[] TAXAS_PROCESSAMENTO_BD_EM_MEDIA = { 0, 100, 60 };
	private static double[] TAXAS_PROCESSAMENTO_BC_EM_MEDIA = { 10000, 10000,
			10000 };
	private static double TEMPO_SIMULACAO = 150;
	private static double TRANSITORIO_INICIAL = 15;

	private static String CMD = "java -Xms256M -Xmx1024M -cp \"lib/easyaccept.jar;"
			+ "lib/simjava.jar;"
			+ "lib/SimuladorInternetBanking.jar;"
			+ "lib/SJGV.jar\" br.ufcg.edu.estimador.GatewaySimulador ";

	public static void main(String[] args) throws IOException {
		
		int minServidoresApp = 1;
		int minServidoresBd = 1;
		
		boolean atendido = false;

		web: for (int numServidoresWeb = 1; numServidoresWeb <= NUMERO_MAXIMO_SERVIDORES; numServidoresWeb++) {
			app: for (int numServidoresApp = minServidoresApp; numServidoresApp <= NUMERO_MAXIMO_SERVIDORES; numServidoresApp++) {
				if (numServidoresWeb + numServidoresApp < NUMERO_MAXIMO_SERVIDORES) {
					bd: for (int numServidoresBd = minServidoresBd; numServidoresBd < NUMERO_MAXIMO_SERVIDORES; numServidoresBd++) {
						if (numServidoresApp + numServidoresBd + numServidoresWeb > NUMERO_MAXIMO_SERVIDORES)
							continue;
						boolean combinacaoPossivel = true;
						for (int i = 0; i < TAXAS_CHEGADA_EM_MEDIA.length; i++) {
	
							try {
								entradaInvalida(TAXAS_CHEGADA_EM_MEDIA[i],
										TAXAS_PROCESSAMENTO_WEB_EM_MEDIA,
										numServidoresWeb);
								entradaInvalida(TAXAS_CHEGADA_EM_MEDIA[i],
										TAXAS_PROCESSAMENTO_BD_EM_MEDIA,
										numServidoresBd);
								entradaInvalida(TAXAS_CHEGADA_EM_MEDIA[i],
										TAXAS_PROCESSAMENTO_APP_EM_MEDIA,
										numServidoresApp);
							} catch (Exception e) {
								combinacaoPossivel &= false;
								continue;
							}
							combinacaoPossivel &= true;
						}
						if (combinacaoPossivel) {
							System.out.println("Web: " + numServidoresWeb
									+ " App: " + numServidoresApp + " Bd: "
									+ numServidoresBd
									+ " é uma combinação possível!");
							for (int i = 0; i < TAXAS_CHEGADA_EM_MEDIA.length; i++) {
								System.out
										.println("Testando tipo " + (i + 1) + "!");
								double[] medidas = chamarSimulador(numServidoresWeb, numServidoresApp,
										numServidoresBd, i);
								if (medidas[0] + medidas[1] + medidas[2] > 3.0) {
									double gargalo = Math.max(medidas[0], medidas[1]);
									gargalo = Math.max(gargalo, medidas[2]);
									
									if (gargalo == medidas[0]) {
										System.out.println("Tempo de resposta não foi satisfatório para o servidor web!");
										continue web;
									} else if (gargalo == medidas[1]) {
										System.out.println("Tempo de resposta não foi satisfatório para o servidor de aplicações!");
										minServidoresApp = numServidoresApp + 1;
										continue app;
									} else {
										System.out.println("Tempo de resposta não foi satisfatório para o servidor de bd!");
										minServidoresBd = numServidoresBd + 1;
										continue bd;
									}
								} else if (medidas[3] >= 0.7) {
									System.out.println("Utilização do servidor web não foi satisfatória!");
									continue web;
								} else if (medidas[4] >= 0.7) {
									System.out.println("Utilização do servidor de aplicações não foi satisfatória!");
									minServidoresApp = numServidoresApp + 1;
									continue app;
								}else if (medidas[5] >= 0.7) {
									System.out.println("Utilização do servidor de bd não foi satisfatória!");
									minServidoresBd = numServidoresBd + 1;
									continue bd;
								}
							}
							System.out.println("Finito!");
							atendido = true;
							break web;
						}
					}
				}
			}
		}
		if (!atendido)
			System.out.println("Não é possível atender a essas entradas com " + NUMERO_MAXIMO_SERVIDORES + " servidores.");

	}

	@SuppressWarnings("unchecked")
	private static double[] chamarSimulador(int numServidoresWeb,
			int numServidoresApp, int numServidoresBd, int i)
			throws IOException {
		double[] medias = new double[6];
		String argumentos = getArgumentos(numServidoresWeb, numServidoresApp,
				numServidoresBd, TAXAS_CHEGADA_EM_MEDIA[i][0],
				TAXAS_CHEGADA_EM_MEDIA[i][1], TAXAS_CHEGADA_EM_MEDIA[i][2],
				TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[0],
				TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[1],
				TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[2],
				TAXAS_PROCESSAMENTO_BD_EM_MEDIA[1],
				TAXAS_PROCESSAMENTO_BD_EM_MEDIA[2],
				TAXAS_PROCESSAMENTO_APP_EM_MEDIA[1],
				TAXAS_PROCESSAMENTO_APP_EM_MEDIA[2],
				TAXAS_PROCESSAMENTO_BC_EM_MEDIA[0],
				TAXAS_PROCESSAMENTO_BC_EM_MEDIA[1],
				TAXAS_PROCESSAMENTO_BC_EM_MEDIA[2], TEMPO_SIMULACAO, TRANSITORIO_INICIAL);
		Process process = Runtime.getRuntime().exec(CMD + argumentos);
		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = null;
		while ((line = stdout.readLine()) != null) {
			System.out.println(line);
			if (line.equals("Fim!")) {
				String[] mediasStr = stdout.readLine().split(" ");
				medias[0] = Double.parseDouble(mediasStr[0]);
				medias[1] = Double.parseDouble(mediasStr[1]);
				medias[2] = Double.parseDouble(mediasStr[2]);
				medias[3] = Double.parseDouble(mediasStr[3]);
				medias[4] = Double.parseDouble(mediasStr[4]);
				medias[5] = Double.parseDouble(mediasStr[5]);
				break;
			}
		}
		stdout.close();
		process.destroy();
		return medias;
	}

	private static void entradaInvalida(double[] chegada,
			double[] processamento, int numServidores) throws Exception {
		for (int i = 0; i < chegada.length; i++) {
			if (processamento[i] != 0
					&& chegada[i] >= numServidores * processamento[i])
				throw new Exception();
		}
	}

	private static <T extends Number> String getArgumentos(T... numeros) {
		if (numeros.length == 0)
			return "";
		else if (numeros.length == 1)
			return numeros + "";
		StringBuilder sb = new StringBuilder(20);
		sb.append(numeros[0]);
		for (int i = 1; i < numeros.length; i++) {
			sb.append(" " + numeros[i]);
		}
		return sb.toString();
	}

}
