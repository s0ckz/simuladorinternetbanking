package br.ufcg.edu.simulador;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufcg.edu.simulador.utils.GUIUtils;

public class Main extends JFrame {

	private static final long serialVersionUID = -8856925644144990064L;

	private JPanel jContentPane = new JPanel();
	
	private JLabel labelNumeroServidoresWeb = new JLabel("Número de servidores web:");
	private JLabel labelServidoresApp = new JLabel("Número de servidores de aplicação:");
	private JLabel labelServidoresBd = new JLabel("Número de servidores BD:");
	private JLabel labelTaxaMediaChegadaA = new JLabel("TMC clientes classe A: ");
	private JLabel labelTaxaMediaChegadaB = new JLabel("TMC clientes classe B: ");
	private JLabel labelTaxaMediaChegadaC = new JLabel("TMC clientes classe C: ");
	private JLabel labelTaxaMediaProcessamentoWebA = new JLabel("TMP dos servidores web classe A: ");
	private JLabel labelTaxaMediaProcessamentoWebB = new JLabel("TMP dos servidores web classe B: ");
	private JLabel labelTaxaMediaProcessamentoWebC = new JLabel("TMP dos servidores web classe C: ");
	private JLabel labelTaxaMediaProcessamentoBdB = new JLabel("TMP dos servidores BD classe B: ");
	private JLabel labelTaxaMediaProcessamentoBdC = new JLabel("TMP dos servidores BD classe C: ");
	private JLabel labelTaxaMediaProcessamentoAppB = new JLabel("TMP dos servidores APP classe B: ");
	private JLabel labelTaxaMediaProcessamentoAppC = new JLabel("TMP dos servidores APP classe C: ");
	private JLabel labelTaxaMediaProcessamentoBcWeb = new JLabel("TMP dos servidores BC web: ");
	private JLabel labelTaxaMediaProcessamentoBcApp = new JLabel("TMP dos servidores BC app: ");
	private JLabel labelTaxaMediaProcessamentoBcBd = new JLabel("TMP dos servidores BC bd: ");
	private JLabel labelTempoSimulacao = new JLabel("Tempo total de simulação: ");
	
	private JTextField fieldNumeroServidoresWeb = new JTextField("8");
	private JTextField fieldNumeroServidoresApp = new JTextField("6");
	private JTextField fieldNumeroServidoresBd = new JTextField("2");
	private JTextField fieldTaxaMediaChegadaA = new JTextField("150");
	private JTextField fieldTaxaMediaChegadaB = new JTextField("70");
	private JTextField fieldTaxaMediaChegadaC = new JTextField("30");
	private JTextField fieldTaxaMediaProcessamentoWebA = new JTextField("300");
	private JTextField fieldTaxaMediaProcessamentoWebB = new JTextField("150");
	private JTextField fieldTaxaMediaProcessamentoWebC = new JTextField("100");
	private JTextField fieldTaxaMediaProcessamentoBdB = new JTextField("100");
	private JTextField fieldTaxaMediaProcessamentoBdC = new JTextField("50");
	private JTextField fieldTaxaMediaProcessamentoAppB = new JTextField("150");
	private JTextField fieldTaxaMediaProcessamentoAppC = new JTextField("100");
	private JTextField fieldTaxaMediaProcessamentoBcWeb = new JTextField("1000");
	private JTextField fieldTaxaMediaProcessamentoBcApp = new JTextField("1000");
	private JTextField fieldTaxaMediaProcessamentoBcBd = new JTextField("1000");
	private JTextField fieldTempoSimulacao = new JTextField("50");

	private JButton buttonSair = new JButton("Sair");
	private JButton buttonSimular = new JButton("Simular");
	
	private SimuladorInternetBanking simulador = new SimuladorInternetBanking();
	
	public Main() {
		init();
	}

	private void init() {
		setContentPane(jContentPane);
		addLabelsToContentPane();
		addFieldsToContentPane();
		addButtonsToContentPane();
		pack();
		GUIUtils.centralizar(this);
		setTitle("Simulador de Internet Banking");
	}

	private void addButtonsToContentPane() {
		
		buttonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonSimular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simular();
			}
		});
		
		jContentPane.add(buttonSair, GUIUtils.criarGBCPreenchimentoHorizontal(0, 17));
		jContentPane.add(buttonSimular, GUIUtils.criarGBCPreenchimentoHorizontal(1, 17));
	}

	private void simular() {
		simulador.setNumeroServidoresWeb(Integer.parseInt(fieldNumeroServidoresWeb.getText()));
		simulador.setNumeroServidoresApp(Integer.parseInt(fieldNumeroServidoresApp.getText()));
		simulador.setNumeroServidoresBd(Integer.parseInt(fieldNumeroServidoresBd.getText()));
		simulador.setTaxaMediaChegadaA(Double.parseDouble(fieldTaxaMediaChegadaA.getText()));
		simulador.setTaxaMediaChegadaB(Double.parseDouble(fieldTaxaMediaChegadaB.getText()));
		simulador.setTaxaMediaChegadaC(Double.parseDouble(fieldTaxaMediaChegadaC.getText()));
		simulador.setTaxaMediaProcessamentoWebA(Double.parseDouble(fieldTaxaMediaProcessamentoWebA.getText()));
		simulador.setTaxaMediaProcessamentoWebB(Double.parseDouble(fieldTaxaMediaProcessamentoWebB.getText()));
		simulador.setTaxaMediaProcessamentoWebC(Double.parseDouble(fieldTaxaMediaProcessamentoWebC.getText()));
		simulador.setTaxaMediaProcessamentoBdB(Double.parseDouble(fieldTaxaMediaProcessamentoBdB.getText()));
		simulador.setTaxaMediaProcessamentoBdC(Double.parseDouble(fieldTaxaMediaProcessamentoBdC.getText()));
		simulador.setTaxaMediaProcessamentoAppB(Double.parseDouble(fieldTaxaMediaProcessamentoAppB.getText()));
		simulador.setTaxaMediaProcessamentoAppC(Double.parseDouble(fieldTaxaMediaProcessamentoAppC.getText()));
		simulador.setTaxaMediaProcessamentoBcWeb(Double.parseDouble(fieldTaxaMediaProcessamentoBcWeb.getText()));
		simulador.setTaxaMediaProcessamentoBcApp(Double.parseDouble(fieldTaxaMediaProcessamentoBcApp.getText()));
		simulador.setTaxaMediaProcessamentoBcBd(Double.parseDouble(fieldTaxaMediaProcessamentoBcBd.getText()));
		simulador.setTempoSimulacao(Double.parseDouble(fieldTempoSimulacao.getText()));
		simulador.simular();
	}

	private void addFieldsToContentPane() {
		jContentPane.add(fieldNumeroServidoresWeb, GUIUtils.criarGBCPreenchimentoHorizontal(1, 0));
		jContentPane.add(fieldNumeroServidoresApp, GUIUtils.criarGBCPreenchimentoHorizontal(1, 1));
		jContentPane.add(fieldNumeroServidoresBd, GUIUtils.criarGBCPreenchimentoHorizontal(1, 2));
		jContentPane.add(fieldTaxaMediaChegadaA, GUIUtils.criarGBCPreenchimentoHorizontal(1, 3));
		jContentPane.add(fieldTaxaMediaChegadaB, GUIUtils.criarGBCPreenchimentoHorizontal(1, 4));
		jContentPane.add(fieldTaxaMediaChegadaC, GUIUtils.criarGBCPreenchimentoHorizontal(1, 5));
		jContentPane.add(fieldTaxaMediaProcessamentoWebA, GUIUtils.criarGBCPreenchimentoHorizontal(1, 6));
		jContentPane.add(fieldTaxaMediaProcessamentoWebB, GUIUtils.criarGBCPreenchimentoHorizontal(1, 7));
		jContentPane.add(fieldTaxaMediaProcessamentoWebC, GUIUtils.criarGBCPreenchimentoHorizontal(1, 8));
		jContentPane.add(fieldTaxaMediaProcessamentoBdB, GUIUtils.criarGBCPreenchimentoHorizontal(1, 9));
		jContentPane.add(fieldTaxaMediaProcessamentoBdC, GUIUtils.criarGBCPreenchimentoHorizontal(1, 10));
		jContentPane.add(fieldTaxaMediaProcessamentoAppB, GUIUtils.criarGBCPreenchimentoHorizontal(1, 11));
		jContentPane.add(fieldTaxaMediaProcessamentoAppC, GUIUtils.criarGBCPreenchimentoHorizontal(1, 12));
		jContentPane.add(fieldTaxaMediaProcessamentoBcWeb, GUIUtils.criarGBCPreenchimentoHorizontal(1, 13));
		jContentPane.add(fieldTaxaMediaProcessamentoBcApp, GUIUtils.criarGBCPreenchimentoHorizontal(1, 14));
		jContentPane.add(fieldTaxaMediaProcessamentoBcBd, GUIUtils.criarGBCPreenchimentoHorizontal(1, 15));
		jContentPane.add(fieldTempoSimulacao, GUIUtils.criarGBCPreenchimentoHorizontal(1, 16));
	}

	private void addLabelsToContentPane() {
		jContentPane.setLayout(new GridBagLayout());
		jContentPane.add(labelNumeroServidoresWeb, GUIUtils.criarGBCPadrao(0, 0));
		jContentPane.add(labelServidoresApp, GUIUtils.criarGBCPadrao(0, 1));
		jContentPane.add(labelServidoresBd, GUIUtils.criarGBCPadrao(0, 2));
		jContentPane.add(labelTaxaMediaChegadaA, GUIUtils.criarGBCPadrao(0, 3));
		jContentPane.add(labelTaxaMediaChegadaB, GUIUtils.criarGBCPadrao(0, 4));
		jContentPane.add(labelTaxaMediaChegadaC, GUIUtils.criarGBCPadrao(0, 5));
		jContentPane.add(labelTaxaMediaProcessamentoWebA, GUIUtils.criarGBCPadrao(0, 6));
		jContentPane.add(labelTaxaMediaProcessamentoWebB, GUIUtils.criarGBCPadrao(0, 7));
		jContentPane.add(labelTaxaMediaProcessamentoWebC, GUIUtils.criarGBCPadrao(0, 8));
		jContentPane.add(labelTaxaMediaProcessamentoBdB, GUIUtils.criarGBCPadrao(0, 9));
		jContentPane.add(labelTaxaMediaProcessamentoBdC, GUIUtils.criarGBCPadrao(0, 10));
		jContentPane.add(labelTaxaMediaProcessamentoAppB, GUIUtils.criarGBCPadrao(0, 11));
		jContentPane.add(labelTaxaMediaProcessamentoAppC, GUIUtils.criarGBCPadrao(0, 12));
		jContentPane.add(labelTaxaMediaProcessamentoBcWeb, GUIUtils.criarGBCPadrao(0, 13));
		jContentPane.add(labelTaxaMediaProcessamentoBcApp, GUIUtils.criarGBCPadrao(0, 14));
		jContentPane.add(labelTaxaMediaProcessamentoBcBd, GUIUtils.criarGBCPadrao(0, 15));
		jContentPane.add(labelTempoSimulacao, GUIUtils.criarGBCPadrao(0, 16));
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
}
