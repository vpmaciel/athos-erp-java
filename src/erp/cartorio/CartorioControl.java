package erp.cartorio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import arquitetura.gui.Msg;
import arquitetura.validacao.Mascara;
import erp.main.MainControl;

final class CartorioControl {

	public class Ajuda implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			Msg.ajuda();
		}
	}

	public class Exclui implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (cartorio == null || cartorio.getId() == null) {
				Msg.erroExcluiRegistro();
				return;
			}
			if (Msg.confirmarExcluiRegistro() != JOptionPane.YES_OPTION) {
				return;
			}
			try {
				CartorioFac.deletarRegistro(cartorio);
				getCartorioFc().limparGui();
				cartorio = new Cartorio();
				Msg.sucessoExcluiRegistro();
			} catch (Exception e) {
				Msg.erroExcluiRegistro();
			}
		}
	}

	public class FechaJanela implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			getCartorioFc().setVisible(false);
		}
	}

	public class Frame extends WindowAdapter {

		@Override
		public void windowActivated(WindowEvent e) {
			getCartorioFc().reiniciarGui();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			getCartorioFc().setVisible(false);
		}

		@Override
		public void windowOpened(WindowEvent e) {
			cartorio = new Cartorio();
			getCartorioPc().getNomeGuiFantasia().requestFocus();
		}
	}

	public class Home implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			MainControl.mostrarFrame(MainControl.getMainFc());
		}
	}

	public class Imprime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			List<Cartorio> cartorios = new LinkedList<>();

			if (cartorio.getId() == null) {
				Msg.avisoImprimiRegistroNaoCadastrado();
				return;
			}
			if (cartorios.add(CartorioFac.getRegistro(cartorio))) {
				CartorioRel cartorioRel = new CartorioRel(cartorios);
				cartorioRel.retornarRelatorio(true);
			}

		}
	}

	public class Novo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			cartorio = new Cartorio();
			getCartorioFc().limparGui();
			getCartorioPc().getNomeGuiFantasia().requestFocus();
		}
	}

	public class Pesquisa implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			atualizarObjeto();
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getCartorioPp().pesquisarRegistro(cartorio);
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getCartorioFp());
				getCartorioFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Registro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getCartorioPp().pesquisarRegistro(new Cartorio());
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getCartorioFp());
				getCartorioFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Relatorio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			List<Cartorio> cartorios = new LinkedList<>();

			try {
				cartorios = new LinkedList<>(CartorioFac.pesquisarRegistro(new Cartorio()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			CartorioRel cartorioRel = new CartorioRel(cartorios);
			cartorioRel.retornarRelatorio(true);

		}
	}

	public class SaidaSistema implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (Msg.confirmarSairDoSistema() == JOptionPane.YES_OPTION) {
				System.exit(0);
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

				if ((getCartorioPc().getNomeGuiFantasia().getText()) == null
						|| getCartorioPc().getNomeGuiFantasia().getText().length() == 0) {
					getCartorioPc().getNomeGuiFantasia().requestFocus();
					Msg.avisoCampoObrigatorio("NOME FANTASIA");
					return;
				}
				if (mensagem == JOptionPane.YES_OPTION) {
					atualizarObjeto();
					CartorioFac.salvarRegistro(cartorio);
					cartorio = new Cartorio();
					getCartorioFc().limparGui();
					getCartorioPc().getNomeGuiFantasia().requestFocus();
					Msg.sucessoSalvarRegistro();
				}
			} catch (Exception e) {
				Throwable throwable = e.getCause().getCause();
				String mensagem = throwable.toString();
				if (mensagem.contains("ConstraintViolationException")) {
					if (mensagem.contains("INDEX_CARTORIO_CNPJ")) {
						Msg.avisoCampoDuplicado("CNPJ");
						getCartorioPc().getGuiCnpj().requestFocus();
					} else {
						Msg.avisoCampoDuplicado();
					}
				}
				e.printStackTrace();
				Msg.erroSalvarRegistro();
			}
		}
	}

	private Cartorio cartorio;

	public CartorioControl() {

	}

	public void atualizarGui() {
		if (cartorio == null) {
			return;
		}
		getCartorioPc().getGuiCnpj().setText(cartorio.getCnpj());
		getCartorioPc().getGuiComarca().setText(cartorio.getComarca());
		getCartorioPc().getGuiDistrito().setText(cartorio.getDistrito());
		getCartorioPc().getGuiMunicipio().setText(cartorio.getMunicipio());
		getCartorioPc().getNomeGuiFantasia().setText(cartorio.getNomeFantasia());
		getCartorioPc().getGuiRazaoSocial().setText(cartorio.getRazaoSocial());
		getCartorioPc().getGuiSubstituto().setText(cartorio.getSubstituto());
		getCartorioPc().getGuiTitular().setText(cartorio.getTitular());
		getCartorioPc().getGuiEmail().setText(cartorio.getEmail());
		getCartorioPc().getGuiFax().setText(cartorio.getFax());
		getCartorioPc().getGuiFone1().setText(cartorio.getFone1());
		getCartorioPc().getGuiFone2().setText(cartorio.getFone2());
		getCartorioPc().getGuiSite().setText(cartorio.getSite());
		getCartorioPc().getGuiBairro().setText(cartorio.getEnderecoBairro());
		getCartorioPc().getGuiCep().setText(cartorio.getEnderecoCep());
		getCartorioPc().getGuiCidade().setText(cartorio.getEnderecoCidade());
		getCartorioPc().getGuiComplemento().setText(cartorio.getEnderecoComplemento());
		getCartorioPc().getGuiEstado().setText(cartorio.getEnderecoEstado());
		getCartorioPc().getGuiLogradouro().setText(cartorio.getEnderecoLogradouro());
		getCartorioPc().getGuiPais().setText(cartorio.getEnderecoPais());
	}

	public void atualizarObjeto() {
		cartorio.setCnpj(getCartorioPc().getGuiCnpj().getText());
		cartorio.setComarca(getCartorioPc().getGuiComarca().getText());
		cartorio.setDistrito(getCartorioPc().getGuiDistrito().getText());
		cartorio.setMunicipio(getCartorioPc().getGuiMunicipio().getText());
		cartorio.setNomeFantasia(getCartorioPc().getNomeGuiFantasia().getText());
		cartorio.setRazaoSocial(getCartorioPc().getGuiRazaoSocial().getText());
		cartorio.setSubstituto(getCartorioPc().getGuiSubstituto().getText());
		cartorio.setTitular(getCartorioPc().getGuiTitular().getText());
		cartorio.setEmail(getCartorioPc().getGuiEmail().getText());
		cartorio.setFax(getCartorioPc().getGuiFax().getText());
		cartorio.setFone1(getCartorioPc().getGuiFone1().getText());
		cartorio.setFone2(getCartorioPc().getGuiFone2().getText());
		cartorio.setSite(getCartorioPc().getGuiSite().getText());
		cartorio.setEnderecoBairro(getCartorioPc().getGuiBairro().getText());
		cartorio.setEnderecoCep(getCartorioPc().getGuiCep().getText());
		cartorio.setEnderecoCidade(getCartorioPc().getGuiCidade().getText());
		cartorio.setEnderecoComplemento(getCartorioPc().getGuiComplemento().getText());
		cartorio.setEnderecoEstado(getCartorioPc().getGuiEstado().getText());
		cartorio.setEnderecoLogradouro(getCartorioPc().getGuiLogradouro().getText());
		cartorio.setEnderecoPais(getCartorioPc().getGuiPais().getText());

		if (getCartorioPc().getGuiCnpj().getText().equals(Mascara.getCnpjVazio())) {
			cartorio.setCnpj(null);
		}

		if (getCartorioPc().getGuiRazaoSocial().getText().length() == 0) {
			cartorio.setRazaoSocial(null);
		}

		if (getCartorioPc().getNomeGuiFantasia().getText().length() == 0) {
			cartorio.setNomeFantasia(null);
		}
	}

	public Cartorio getCartorio() {
		return cartorio;
	}

	public CartorioFc getCartorioFc() {
		return MainControl.getCartorioFc();
	}

	public CartorioFp getCartorioFp() {
		return MainControl.getCartorioFp();
	}

	public CartorioPc getCartorioPc() {
		return MainControl.getCartorioFc().getCartorioPc();
	}

	public CartorioPp getCartorioPp() {
		return MainControl.getCartorioFp().getCartorioPp();
	}

	public void setCartorio(Cartorio cartorio) {
		this.cartorio = cartorio;
	}
}