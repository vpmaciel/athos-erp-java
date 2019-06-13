package erp.cliente;

import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import arquitetura.AOP;
import arquitetura.gui.ConfiguracaoGui;
import arquitetura.gui.FocoEvento;
import arquitetura.gui.Gui;
import arquitetura.gui.Imagem;

@SuppressWarnings("serial")
public final class ClienteFc extends JFrame implements Gui {

	private ClienteCont clienteCont;
	private ClientePc clientePc;
	private ConfiguracaoGui configuracaoGui;

	public ClienteFc() {
		iniciarLayout();
		iniciarGui();
		iniciarFocoControlador();
		iniciarGuiControlador();
		iniciarControlador();
	}

	@Override
	public void atualizarTable() {

	}

	public ClienteCont getClienteCont() {
		return clienteCont;
	}

	public ClientePc getClientePc() {
		return clientePc;
	}

	@Override
	public ConfiguracaoGui getConfiguracaoGui() {
		return configuracaoGui;
	}

	@Override
	public void iniciarControlador() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		clienteCont = new ClienteCont();
		addWindowListener(clienteCont.new Frame());
		clientePc.getLabelEmpresa().addMouseListener(clienteCont.new MostraFrame());
		clientePc.getLabelBanco().addMouseListener(clienteCont.new MostraFrame());
		clientePc.getTB().getExcluirBtn().addActionListener(clienteCont.new Exclui());
		clientePc.getTB().getNovoBtn().addActionListener(clienteCont.new Novo());
		clientePc.getTB().getPesquisarBtn().addActionListener(clienteCont.new Pesquisa());
		clientePc.getTB().getImprimirBtn().addActionListener(clienteCont.new Imprime());
		clientePc.getTB().getRelatorioBtn().addActionListener(clienteCont.new Relatorio());
		clientePc.getTB().getSalvarBtn().addActionListener(clienteCont.new Salva());
		clientePc.getTB().getFecharBtn().addActionListener(clienteCont.new FechaJanela());
		clientePc.getTB().getSairBtn().addActionListener(clienteCont.new SaidaSistema());
		clientePc.getTB().getAjudaBtn().addActionListener(clienteCont.new Ajuda());
		clientePc.getTB().getHomeBtn().addActionListener(clienteCont.new Home());
		clientePc.getTB().getRegistrosBtn().addActionListener(clienteCont.new Registro());
	}

	@Override
	public void iniciarFocoControlador() {
		@SuppressWarnings("unused")
		FocoEvento focoEvento = new FocoEvento(this);
	}

	@Override
	public void iniciarGui() {
		setIconImage(Imagem.getLogoTipoImage());

		clientePc = new ClientePc();
		clientePc.setOpaque(true); // content panes must be opaque

		final JScrollPane scrollPane = new JScrollPane(clientePc);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (!(evt.getNewValue() instanceof JComponent)) {
							return;
						}
						JComponent focused = (JComponent) evt.getNewValue();
						if (clientePc.isAncestorOf(focused)) {
							clientePc.scrollRectToVisible(focused.getBounds());
						}
					}
				});
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane);
		setContentPane(scrollPane);
		pack();
	}

	@Override
	public void iniciarGuiControlador() {
		configuracaoGui = new ConfiguracaoGui(this);
	}

	@Override
	public void iniciarLayout() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setPreferredSize(AOP.getTamanhoJanela());
		setMinimumSize(AOP.getTamanhoJanela());
		setSize(AOP.getTamanhoJanela());
		setMaximumSize(AOP.getTamanhoJanela());
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
		clientePc.reiniciarGui();
	}
}
