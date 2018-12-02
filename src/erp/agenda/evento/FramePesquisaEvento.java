package erp.agenda.evento;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import arquitetura.gui.Gui;
import arquitetura.gui.GuiGerenteEventos;
import arquitetura.gui.Imagem;

@SuppressWarnings("serial")
public final class FramePesquisaEvento extends JFrame implements Gui {

	private PanelPesquisaEvento PanelPesquisaEvento;

	public FramePesquisaEvento() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		iniciarLayout();
		iniciarGui();
		iniciarGerenteEventos();
	}

	@Override
	public void atualizarTable() {

	}

	@Override
	public GuiGerenteEventos getGuiGerenteEventos() {
		return null;
	}

	public PanelPesquisaEvento getPanelPesquisaEvento() {
		return PanelPesquisaEvento;
	}

	@Override
	public void iniciarFocusTabListener() {

	}

	@Override
	public void iniciarGui() {
		setTitle("EVENTO");
		setIconImage(Imagem.getLogoTipoImage());
		PanelPesquisaEvento = new PanelPesquisaEvento();
		setContentPane(PanelPesquisaEvento);
	}

	@Override
	public void iniciarGuiGerenteEventos() {

	}

	@Override
	public void iniciarGerenteEventos() {
		PanelPesquisaEvento.iniciarHandle();
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
	public void iniciarTable() {

	}

	@Override
	public void limparGui() {

	}

	@Override
	public void reiniciarBox() {

	}
}
