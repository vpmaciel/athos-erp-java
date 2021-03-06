package erp.login;

import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import arquitetura.Sis;
import arquitetura.gui.ConfiguracaoGui;
import arquitetura.gui.FocoEvento;
import arquitetura.gui.Gui;
import arquitetura.gui.Imagem;
import erp.usuario.Usuario;

@SuppressWarnings("serial")
public final class LoginFc extends JFrame implements Gui {

	private static Usuario usuario;

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		LoginFc.usuario = usuario;
	}

	private LoginControl loginControl;

	private LoginPc loginPc;

	public LoginFc() {
		iniciarLayout();
		iniciarGui();
		iniciarFocoControlador();
		iniciarGuiControlador();
		iniciarControlador();
	}

	@Override
	public void atualizarTable() {

	}

	@Override
	public ConfiguracaoGui getConfiguracaoGui() {
		return null;
	}

	public LoginControl getLoginCont() {
		return loginControl;
	}

	public LoginPc getLoginPc() {
		return loginPc;
	}

	@Override
	public void iniciarControlador() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		loginControl = new LoginControl();
		addWindowListener(loginControl.new Frame());
		loginPc.getTextFieldSenha().addActionListener(loginControl.new ButtonEntrar());
		loginPc.getTextFieldSenha().addKeyListener(loginControl.new ButtonEntrarTeclado());
		loginPc.getButtonEntrar().addActionListener(loginControl.new ButtonEntrar());

	}

	@Override
	public void iniciarFocoControlador() {
		@SuppressWarnings("unused")
		FocoEvento focoEvento = new FocoEvento(this);
	}

	@Override
	public void iniciarGui() {
		setIconImage(Imagem.getLogoTipoImage());
		loginPc = new LoginPc();
		loginPc.setOpaque(true); // content panes must be opaque

		final JScrollPane scrollPane = new JScrollPane(loginPc);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (!(evt.getNewValue() instanceof JComponent)) {
							return;
						}
						JComponent focused = (JComponent) evt.getNewValue();
						if (loginPc.isAncestorOf(focused)) {
							loginPc.scrollRectToVisible(focused.getBounds());
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

	}

	@Override
	public void iniciarLayout() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setPreferredSize(Sis.getTamanhoJanela());
		setMinimumSize(Sis.getTamanhoJanela());
		setSize(Sis.getTamanhoJanela());
		setMaximumSize(Sis.getTamanhoJanela());
	}

	@Override
	public void iniciarTabela() {
	}

	@Override
	public void limparGui() {

	}

	@Override
	public void reiniciarGui() {
	}
}