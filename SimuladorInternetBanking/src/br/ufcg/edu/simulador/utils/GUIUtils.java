package br.ufcg.edu.simulador.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe de utilidades para layout.
 * 
 * @author Isaac Leal
 * @author Leandro José
 * @author Rodrigo Bruno
 * 
 */
public class GUIUtils {

	/**
	 * Método estático que define Dimensões para janelas.
	 */
	private static Dimension screen = Toolkit.getDefaultToolkit()
			.getScreenSize();

	/**
	 * Método estático que define comportamento com relação a posição para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPadrao(int x, int y) {
		return criarGBCPadrao(x, y, 1, 1);
	}

	/**
	 * Método estático que define comportamento com relação a posição para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPadrao(int x, int y,
			int gridWidth, int gridHeight) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		return gridBagConstraints;
	}

	/**
	 * Método estático que define uma fonte padrão para as janelas.
	 * 
	 * @return Retorna a fonte padrão.
	 */
	public static Font getFontePadrao() {
		return new Font("Verdana", Font.BOLD, 12);
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoHorizontal(int x,
			int y) {
		return criarGBCPreenchimentoHorizontal(x, y, 1, 1);
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoHorizontal(int x,
			int y, int gridWeight, int gridHeight) {
		GridBagConstraints gridBagConstraints = criarGBCPadrao(x, y,
				gridWeight, gridHeight);
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		return gridBagConstraints;
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoTotal(int x, int y) {
		return criarGBCPreenchimentoTotal(x, y, 1, 1);
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoTotal(int x, int y,
			int gridWeight, int gridHeight) {
		GridBagConstraints gridBagConstraints = criarGBCPadrao(x, y,
				gridWeight, gridHeight);
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		return gridBagConstraints;
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoVertical(int x, int y) {
		return criarGBCPreenchimentoVertical(x, y, 1, 1);
	}

	/**
	 * Método estático que define comportamento com relação a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoVertical(int x,
			int y, int gridWeight, int gridHeight) {
		GridBagConstraints gridBagConstraints = criarGBCPadrao(x, y,
				gridWeight, gridHeight);
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		return gridBagConstraints;
	}

	/**
	 * Método estático que define layout padrão para janelas.
	 */
	public static void setWindowsLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
	}

	/**
	 * Método estático que centraliza janelas na tela.
	 * 
	 * @param layout
	 *            layout a ser centralizada.
	 */
	public static void centralizar(Window layout) {
		int width = (int) layout.getSize().getWidth();
		int height = (int) layout.getSize().getHeight();
		int x = (int) (screen.getWidth() - width) / 2;
		int y = (int) (screen.getHeight() - height) / 2;
		layout.setBounds(x, y, width, height);
	}

	/**
	 * Método estático que cria uma janela para exibição de mensagens de aviso.
	 * 
	 * @param c
	 *            Componente da janela.
	 * @param e
	 *            exceção que se deseja mostrar na janela.
	 */
	public static void mostrarMensagem(Component c, NumberFormatException e) {
		mostrarMensagem(c, "O número informado está em formato inválido!");
	}

	/**
	 * Método estático que cria uma janela para exibição de mensagens de aviso.
	 * 
	 * @param c
	 *            Componente da janela.
	 * @param e
	 *            exceção que se deseja mostrar na janela.
	 */
	public static void mostrarMensagem(Component c, Exception e) {
		mostrarMensagem(c, e.getMessage());
	}

	/**
	 * Método estático que cria uma janela para exibição de mensagens de aviso.
	 * 
	 * @param c
	 *            Componente da janela.
	 * @param s
	 *            Mensagem a ser exibida.
	 * 
	 */
	public static void mostrarMensagem(Component c, String s) {
		JOptionPane.showMessageDialog(c, s);

	}

	public static boolean confirmar(Component c, String mensagem) {
		return JOptionPane.showConfirmDialog(c, mensagem, "PDAMH - Pergunta", JOptionPane.YES_NO_OPTION) == 0;
	}

	public static int confirmarComCancelar(Component c, String mensagem) {
		return JOptionPane.showConfirmDialog(c, mensagem);
	}

}
