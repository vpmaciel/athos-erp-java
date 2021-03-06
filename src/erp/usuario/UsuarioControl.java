package erp.usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import arquitetura.gui.Msg;
import erp.main.MainControl;

final class UsuarioControl {

	public class Ajuda implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			Msg.ajuda();
		}
	}

	public class Exclui implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (usuario == null || usuario.getId() == null) {
				return;
			}
			if (Msg.confirmarExcluiRegistro() != JOptionPane.YES_OPTION) {
				return;
			}
			try {
				UsuarioFac.deletarRegistro(usuario);
				getUsuarioFc().limparGui();
				usuario = new Usuario();
				Msg.sucessoExcluiRegistro();
			} catch (Exception e) {
				Msg.erroExcluiRegistro();
			}
		}
	}

	public class FechaJanela implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				getUsuarioFc().setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Frame extends WindowAdapter {

		@Override
		public void windowActivated(WindowEvent e) {
			getUsuarioFc().reiniciarGui();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			getUsuarioFc().setVisible(false);
		}

		@Override
		public void windowOpened(WindowEvent e) {
			usuario = new Usuario();
		}
	}

	public class Home implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				MainControl.mostrarFrame(MainControl.getMainFc());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Imprime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			List<Usuario> usuarios = new LinkedList<>();

			if (usuario.getId() == null) {
				Msg.avisoImprimiRegistroNaoCadastrado();
				return;
			}
			if (usuarios.add(UsuarioFac.getRegistro(usuario))) {
				UsuarioRel usuarioRel = new UsuarioRel(usuarios);
				usuarioRel.retornarRelatorio(true);
			}
		}
	}

	public class MostraFrameUsuario extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getButton() == MouseEvent.BUTTON1) {
				MainControl.mostrarFrame(MainControl.getUsuarioFc());
			} else {
				MainControl.getUsuarioFc().reiniciarGui();
			}
		}
	}

	public class Novo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			usuario = new Usuario();
			getUsuarioFc().limparGui();
			getUsuarioPc().getGuiNome().requestFocus();
		}
	}

	public class Pesquisa implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			atualizarObjeto();
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getUsuarioPp().pesquisarRegistro(usuario);
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getUsuarioFp());
				getUsuarioFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Registro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getUsuarioPp().pesquisarRegistro(new Usuario());
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getUsuarioFp());
				getUsuarioFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Relatorio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			List<Usuario> usuarios = new LinkedList<>();

			try {
				usuarios = new LinkedList<>(UsuarioFac.pesquisarRegistro(new Usuario()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			UsuarioRel usuarioRel = new UsuarioRel(usuarios);
			usuarioRel.retornarRelatorio(true);

		}
	}

	public class SaidaSistema implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				int mensagem = Msg.confirmarSairDoSistema();
				if (mensagem == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Salva implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			try {

				int mensagem = Msg.confirmarSalvarRegistro();
				if (mensagem != JOptionPane.YES_OPTION) {
					return;
				}

				if ((getUsuarioPc().getGuiNome().getText()) == null
						|| getUsuarioPc().getGuiNome().getText().length() == 0) {
					getUsuarioPc().getGuiNome().requestFocus();
					Msg.avisoCampoObrigatorio("NOME");
					return;
				}
				if (mensagem == JOptionPane.YES_OPTION) {
					atualizarObjeto();
					UsuarioFac.salvarRegistro(usuario);
					usuario = new Usuario();
					MainControl.getUsuarioFc().limparGui();
					getUsuarioPc().getGuiNome().requestFocus();
					Msg.sucessoSalvarRegistro();
				}
			} catch (Exception e) {
				Throwable throwable = e.getCause().getCause();
				String mensagem = throwable.toString();
				if (mensagem.contains("ConstraintViolationException")) {
					if (mensagem.contains("INDEX_USUARIO_NOME")) {
						Msg.avisoCampoDuplicado("NOME");
						getUsuarioPc().getGuiNome().requestFocus();
					} else {
						Msg.avisoCampoDuplicado();
					}
				}
				e.printStackTrace();
				Msg.erroSalvarRegistro();
			}
		}
	}

	private Usuario usuario;

	UsuarioControl() {
	}

	public void atualizarGui() {
		if (usuario == null) {
			return;
		}
		getUsuarioPc().getGuiNome().setText(usuario.getNome());
		getUsuarioPc().getGuiSenha().setText(usuario.getSenha());
	}

	public void atualizarObjeto() {

		usuario.setSenha(getUsuarioPc().getGuiSenha().getText());
		usuario.setNome(getUsuarioPc().getGuiNome().getText());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public UsuarioFc getUsuarioFc() {
		return MainControl.getUsuarioFc();
	}

	public UsuarioFp getUsuarioFp() {
		return MainControl.getUsuarioFp();
	}

	public UsuarioPc getUsuarioPc() {
		return MainControl.getUsuarioFc().getUsuarioPc();
	}

	public UsuarioPp getUsuarioPp() {
		return MainControl.getUsuarioFp().getUsuarioPp();
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}