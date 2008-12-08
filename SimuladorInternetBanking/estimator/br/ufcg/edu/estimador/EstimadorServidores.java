package br.ufcg.edu.estimador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EstimadorServidores {

	private static int NUMERO_MAXIMO_SERVIDORES = 16;
	private static double[][] TAXAS_CHEGADA_EM_MEDIA = { { 100, 50, 30 },
			{ 300, 200, 200 }, { 400, 100, 300 } };
	private static double[] TAXAS_PROCESSAMENTO_WEB_EM_MEDIA = { 50, 25, 20 };
	private static double[] TAXAS_PROCESSAMENTO_APP_EM_MEDIA = { 20, 15 };
	private static double[] TAXAS_PROCESSAMENTO_BD_EM_MEDIA = { 18, 12 };
	private static double[] TAXAS_PROCESSAMENTO_BC_EM_MEDIA = { 10000, 10000,
			10000 };
	private static double TEMPO_SIMULACAO = 100;
	private static double TRANSITORIO_INICIAL = 30;

	private static String CMD = "java -Xms256M -Xmx1024M -cp \"lib/easyaccept.jar;"
			+ "lib/simjava.jar;" + "lib/SimuladorInternetBanking.jar;"
			+ "lib/SJGV.jar\" br.ufcg.edu.estimador.GatewaySimulador ";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		for (int numServidoresWeb = 1; numServidoresWeb <= NUMERO_MAXIMO_SERVIDORES; numServidoresWeb++) {
			for (int numServidoresApp = 1; numServidoresApp <= NUMERO_MAXIMO_SERVIDORES; numServidoresApp++) {
				if (numServidoresWeb + numServidoresApp < NUMERO_MAXIMO_SERVIDORES) {
					int numServidoresBd = NUMERO_MAXIMO_SERVIDORES
							- numServidoresWeb - numServidoresApp;
					System.out.println("Web: " + numServidoresWeb + " App: "
							+ numServidoresApp + " Bd: " + numServidoresBd);
					for (int i = 0; i < TAXAS_CHEGADA_EM_MEDIA.length; i++) {
						System.out.println("Testando taxa de chegada tipo " + (i + 1));
						String argumentos = getArgumentos(
								numServidoresWeb,
								numServidoresApp,
								numServidoresBd,
								TAXAS_CHEGADA_EM_MEDIA[i][0],
								TAXAS_CHEGADA_EM_MEDIA[i][1],
								TAXAS_CHEGADA_EM_MEDIA[i][2],
								TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[0],
								TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[1],
								TAXAS_PROCESSAMENTO_WEB_EM_MEDIA[2],
								TAXAS_PROCESSAMENTO_BD_EM_MEDIA[0],
								TAXAS_PROCESSAMENTO_BD_EM_MEDIA[1],
								TAXAS_PROCESSAMENTO_APP_EM_MEDIA[0],
								TAXAS_PROCESSAMENTO_APP_EM_MEDIA[1],
								TAXAS_PROCESSAMENTO_BC_EM_MEDIA[0],
								TAXAS_PROCESSAMENTO_BC_EM_MEDIA[1],
								TAXAS_PROCESSAMENTO_BC_EM_MEDIA[2],
								TEMPO_SIMULACAO
								);
						System.out.println(CMD + argumentos);
						Process process = Runtime.getRuntime().exec(CMD + argumentos);
						BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String line = null;
						while ((line = stdout.readLine()) != null) {
							System.out.println(line);
							if (line.equals("Fim!"))
								break;
						}
						stdout.close();
						process.destroy();
					}

				}
			}
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
