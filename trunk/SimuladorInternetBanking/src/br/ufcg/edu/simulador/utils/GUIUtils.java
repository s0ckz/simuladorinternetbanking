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
 * @author Leandro Jos�
 * @author Rodrigo Bruno
 * 
 */
public class GUIUtils {

	/**
	 * M�todo est�tico que define Dimens�es para janelas.
	 */
	private static Dimension screen = Toolkit.getDefaultToolkit()
			.getScreenSize();

	/**
	 * M�todo est�tico que define comportamento com rela��o a posi��o para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPadrao(int x, int y) {
		return criarGBCPadrao(x, y, 1, 1);
	}

	/**
	 * M�todo est�tico que define comportamento com rela��o a posi��o para
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
	 * M�todo est�tico que define uma fonte padr�o para as janelas.
	 * 
	 * @return Retorna a fonte padr�o.
	 */
	public static Font getFontePadrao() {
		return new Font("Verdana", Font.BOLD, 12);
	}

	/**
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoHorizontal(int x,
			int y) {
		return criarGBCPreenchimentoHorizontal(x, y, 1, 1);
	}

	/**
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
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
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoTotal(int x, int y) {
		return criarGBCPreenchimentoTotal(x, y, 1, 1);
	}

	/**
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
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
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
	 * janelas.
	 */
	public static GridBagConstraints criarGBCPreenchimentoVertical(int x, int y) {
		return criarGBCPreenchimentoVertical(x, y, 1, 1);
	}

	/**
	 * M�todo est�tico que define comportamento com rela��o a preenchimento para
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
	 * M�todo est�tico que define layout padr�o para janelas.
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
	 * M�todo est�tico que centraliza janelas na tela.
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
	 * M�todo est�tico que cria uma janela para exibi��o de mensagens de aviso.
	 * 
	 * @param c
	 *            Componente da janela.
	 * @param e
	 *            exce��o que se deseja mostrar na janela.
	 */
	public static void mostrarMensagem(Component c, NumberFormatException e) {
		mostrarMensagem(c, "O n�mero informado est� em formato inv�lido!");
	}

	/**
	 * M�todo est�tico que cria uma janela para exibi��o de mensagens de aviso.
	 * 
	 * @param c
	 *            Componente da janela.
	 * @param e
	 *            exce��o que se deseja mostrar na janela.
	 */
	public static void mostrarMensagem(Component c, Exception e) {
		mostrarMensagem(c, e.getMessage());
	}

	/**
	 * M�todo est�tico que cria uma janela para exibi��o de mensagens de aviso.
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
