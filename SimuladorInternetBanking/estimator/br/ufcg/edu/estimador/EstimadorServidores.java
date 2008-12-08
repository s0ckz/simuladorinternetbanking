package br.ufcg.edu.estimador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EstimadorServidores {

	private static int NUMERO_MAXIMO_SERVIDORES = 16;
	private static double[][] TAXAS_CHEGADA_EM_MEDIA = { { 100, 50, 30 },
			{ 300, 200, 200 }, { 400, 100, 300 } };
	private static double[] TAXAS_PROCESSAMENTO_WEB_EM_MEDIA = { 200, 150, 100 };
	private static double[] TAXAS_PROCESSAMENTO_APP_EM_MEDIA = { 0, 120, 80 };
	private static double[] TAXAS_PROCESSAMENTO_BD_EM_MEDIA = { 0, 100, 60 };
	private static double[] TAXAS_PROCESSAMENTO_BC_EM_MEDIA = { 10000, 10000,
			10000 };
	private static double TEMPO_SIMULACAO = 100;
	private static double TRANSITORIO_INICIAL = 30;

	private static String CMD = "java -Xms256M -Xmx1024M -cp \"lib/easyaccept.jar;"
			+ "lib/simjava.jar;"
			+ "lib/SimuladorInternetBanking.jar;"
			+ "lib/SJGV.jar\" br.ufcg.edu.estimador.GatewaySimulador ";

	public static void main(String[] args) throws IOException {

		boolean[] tiposTestados = new boolean[TAXAS_CHEGADA_EM_MEDIA.length];

		for (int numServidoresWeb = 1; numServidoresWeb <= NUMERO_MAXIMO_SERVIDORES; numServidoresWeb++) {
			for (int numServidoresApp = 1; numServidoresApp <= NUMERO_MAXIMO_SERVIDORES; numServidoresApp++) {
				if (numServidoresWeb + numServidoresApp < NUMERO_MAXIMO_SERVIDORES) {
					int numServidoresBd = NUMERO_MAXIMO_SERVIDORES
							- numServidoresWeb - numServidoresApp;
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
						tiposTestados[i] = true;
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
							chamarSimulador(numServidoresWeb, numServidoresApp,
									numServidoresBd, i);
						}

					}
				}
			}
		}
		System.out.println();
		for (int i = 0; i < tiposTestados.length; i++) {
			if (!tiposTestados[i])
				System.out
						.println("Não foi possível testar nenhuma configuração de servidores para o tipo "
								+ (i + 1) + "!");
		}

	}

	@SuppressWarnings("unchecked")
	private static void chamarSimulador(int numServidoresWeb,
			int numServidoresApp, int numServidoresBd, int i)
			throws IOException {
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
			if (line.equals("Fim!"))
				break;
		}
		stdout.close();
		process.destroy();
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
