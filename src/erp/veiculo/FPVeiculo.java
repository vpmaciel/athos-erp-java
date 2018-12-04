package erp.veiculo;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import arquitetura.gui.GUI;
import arquitetura.gui.GUIConfiguracao;

@SuppressWarnings("serial")
public final class FPVeiculo extends JFrame implements GUI {

	private PPVeiculo PPVeiculo;

	public FPVeiculo() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		iniciarLayout();
		iniciarGUI();
		iniciarControlador();
	}

	@Override
	public void atualizarTable() {

	}

	@Override
	public GUIConfiguracao getGUIConfiguracao() {
		return null;
	}

	public PPVeiculo getPanelPesquisaVeiculo() {
		return PPVeiculo;
	}

	@Override
	public void iniciarFocoControlador() {

	}

	@Override
	public void iniciarGUI() {
		setTitle("VEÍCULO");
		setIconImage(arquitetura.gui.Imagem.getLogoTipoImage());
		PPVeiculo = new PPVeiculo();
		setContentPane(PPVeiculo);
	}

	@Override
	public void iniciarGUIControlador() {

	}

	@Override
	public void iniciarControlador() {
		PPVeiculo.iniciarHandle();
	}

	@Override
	public void iniciarLayout() {
		setLayout(null);
		setPreferredSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setSize(new Dimension(800, 600));
		setMaximumSize(new Dimension(800, 600));
	}

	@Override
	public void iniciarTabela() {

	}

	@Override
	public void limparGUI() {

	}

	@Override
	public void reiniciarGUI() {

	}
}
