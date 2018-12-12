package erp.cliente;

import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import arquitetura.gui.FocusTabListener;
import arquitetura.gui.GUI;
import arquitetura.gui.ConfiguracaoGUI;
import arquitetura.gui.Imagem;

@SuppressWarnings("serial")
public final class ClienteFc extends JFrame implements GUI {

	private ClienteCont clienteCont;
	private ConfiguracaoGUI configuracaoGUI;
	private ClientePc clientePc;

	public ClienteFc() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		iniciarLayout();
		iniciarGUI();
		iniciarFocoControlador();
		iniciarGUIControlador();
		iniciarControlador();
	}

	@Override
	public void atualizarTable() {

	}

	public ClienteCont getClienteHandle() {
		return clienteCont;
	}

	@Override
	public ConfiguracaoGUI getGUIConfiguracao() {
		return configuracaoGUI;
	}

	public ClientePc getClientePc() {
		return clientePc;
	}

	@Override
	public void iniciarFocoControlador() {
		@SuppressWarnings("unused")
		FocusTabListener focusTabListener = new FocusTabListener(this);
	}

	@Override
	public void iniciarGUI() {
		setTitle("CLIENTE");
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

		add(scrollPane);
		setContentPane(scrollPane);
		pack();
	}

	@Override
	public void iniciarGUIControlador() {
		configuracaoGUI = new ConfiguracaoGUI(this);
	}

	@Override
	public void iniciarControlador() {
		clienteCont = new ClienteCont();
		addWindowListener(clienteCont.new Frame());
		clientePc.getLabelEmpresa().addMouseListener(clienteCont.new MostraFrame());
		clientePc.getLabelBanco().addMouseListener(clienteCont.new MostraFrame());
		clientePc.getTB().getExcluirBTN().addActionListener(clienteCont.new Exclui());
		clientePc.getTB().getNovoBTN().addActionListener(clienteCont.new Novo());
		clientePc.getTB().getPesquisarBTN().addActionListener(clienteCont.new Pesquisa());
		clientePc.getTB().getImprimirBTN().addActionListener(clienteCont.new Imprime());
		clientePc.getTB().getRelatorioBTN().addActionListener(clienteCont.new Relatorio());
		clientePc.getTB().getSalvarBTN().addActionListener(clienteCont.new Salva());
		clientePc.getTB().getFecharBTN().addActionListener(clienteCont.new FechaJanela());
		clientePc.getTB().getSairBTN().addActionListener(clienteCont.new SaidaSistema());
		clientePc.getTB().getAjudaBTN().addActionListener(clienteCont.new Ajuda());
		clientePc.getTB().getHomeBTN().addActionListener(clienteCont.new Home());
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
		configuracaoGUI.limparGui();
	}

	@Override
	public void reiniciarGUI() {
		clientePc.reiniciarGUI();
	}
}