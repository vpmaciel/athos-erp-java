package erp.agenda.evento;

import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import arquitetura.Sis;
import arquitetura.gui.ConfiguracaoGui;
import arquitetura.gui.EntradaMaiuscula;
import arquitetura.gui.FocoEvento;
import arquitetura.gui.Gui;
import arquitetura.gui.registro.ToolBar;
import arquitetura.util.SpringUtilities;
import arquitetura.validacao.Mascara;
import erp.agenda.evento.tipoevento.TipoEvento;
import erp.agenda.evento.tipoevento.TipoEventoComp;
import erp.agenda.evento.tipoevento.TipoEventoFac;
import erp.main.MainControl;

@SuppressWarnings("serial")
public final class EventoPc extends JPanel implements Gui {

	private JComboBox<TipoEvento> boxTipoEvento;
	private ConfiguracaoGui configuracaoGui;
	private JFormattedTextField fieldData;
	private JTextField fieldDescricao;
	private JFormattedTextField fieldHoraInicio;
	private JFormattedTextField fieldHoraTermino;
	private JLabel labelTipoEvento;
	private ToolBar toolBar;

	public EventoPc() {
		iniciarLayout();
		iniciarGui();
		iniciarFocoControlador();
		iniciarGuiControlador();
	}

	@Override
	public void atualizarTable() {

	}

	@Override
	public ConfiguracaoGui getConfiguracaoGui() {
		return configuracaoGui;
	}

	public JTextField getDataGui() {
		return fieldData;
	}

	public JTextField getGuiDescricao() {
		return fieldDescricao;
	}

	public JFormattedTextField getGuiHoraInicio() {
		return fieldHoraInicio;
	}

	public JFormattedTextField getGuiHoraTermino() {
		return fieldHoraTermino;
	}

	public JLabel getLabelTipoEvento() {
		return labelTipoEvento;
	}

	public JComboBox<TipoEvento> getTipoEventoGui() {
		return boxTipoEvento;
	}

	public ToolBar getTB() {
		return toolBar;
	}

	@Override
	public void iniciarControlador() {

	}

	@Override
	public void iniciarFocoControlador() {
		@SuppressWarnings("unused")
		FocoEvento focoEvento = new FocoEvento(this);
	}

	@Override
	public void iniciarGui() {

		toolBar = new ToolBar();

		add(toolBar.getTB());

		labelTipoEvento = new JLabel("EVENTO");
		labelTipoEvento.setCursor(Sis.getNovaJanelaCursor());
		add(labelTipoEvento);

		boxTipoEvento = new JComboBox<TipoEvento>();
		boxTipoEvento.setMaximumRowCount(5);

		List<TipoEvento> tipoEventosList = (List<TipoEvento>) TipoEventoFac.getRegistro();
		Collections.sort(tipoEventosList, new TipoEventoComp().new Nome());

		for (TipoEvento t : tipoEventosList) {
			boxTipoEvento.addItem(t);
		}

		add(boxTipoEvento);

		add(new JLabel("DESCRIÇÃO"));

		fieldDescricao = new JTextField();
		fieldDescricao.setDocument(new EntradaMaiuscula(50));
		add(fieldDescricao);

		add(new JLabel("DATA"));

		fieldData = new JFormattedTextField(Mascara.getData());
		add(fieldData);

		add(new JLabel("HORÁRIO DE INÍCIO"));

		fieldHoraInicio = new JFormattedTextField(Mascara.getHora());
		add(fieldHoraInicio);

		add(new JLabel("HORÁRIO DE TÉRMINO"));

		fieldHoraTermino = new JFormattedTextField(Mascara.getHora());
		add(fieldHoraTermino);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(this, 11, 1, // rows, cols
				5, 5, // initX, initY
				5, 5); // xPad, yPad
		setOpaque(true); // content panes must be opaque
	}

	@Override
	public void iniciarGuiControlador() {
		configuracaoGui = new ConfiguracaoGui(this);
	}

	@Override
	public void iniciarLayout() {
		setBorder(Sis.getBordaPainel());
		setLayout(new SpringLayout());
	}

	@Override
	public void iniciarTabela() {

	}

	@Override
	public void limparGui() {
		configuracaoGui.limparGui();
	}

	@Override

	public void reiniciarGui() {
		TipoEvento tipoEvento = null;
		List<TipoEvento> tipoEventoList = (List<TipoEvento>) TipoEventoFac.getRegistro();
		Collections.sort(tipoEventoList, new TipoEventoComp().new Nome());
		boxTipoEvento.removeAllItems();

		for (TipoEvento t : tipoEventoList) {
			this.boxTipoEvento.addItem(t);
		}

		if (!MainControl.getAgendaEventoFc().isShowing()
				&& MainControl.getAgendaEventoFc().getEventoCont().getEvento() != null) {

			tipoEvento = MainControl.getAgendaEventoFc().getEventoCont().getEvento().getTipoEvento();
			boxTipoEvento.setSelectedItem(tipoEvento);
		}
	}
}
