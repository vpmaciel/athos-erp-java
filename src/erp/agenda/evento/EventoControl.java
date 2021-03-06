package erp.agenda.evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import arquitetura.gui.Msg;
import erp.agenda.evento.tipoevento.TipoEvento;
import erp.agenda.evento.tipoevento.TipoEventoComp;
import erp.agenda.evento.tipoevento.TipoEventoFac;
import erp.main.MainControl;

final class EventoControl {

	public class Ajuda implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			Msg.ajuda();
		}
	}

	public class Exclui implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if (evento == null || evento.getId() == null) {
				return;
			}
			if (Msg.confirmarExcluiRegistro() != JOptionPane.YES_OPTION) {
				return;
			}
			try {
				EventoFac.deletarRegistro(evento);
				getEventoFc().limparGui();
				evento = new Evento();
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
				getEventoFc().setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Frame extends WindowAdapter {

		@Override
		public void windowActivated(WindowEvent e) {
			getEventoFc().reiniciarGui();
		}

		@Override
		public void windowClosing(WindowEvent e) {
			getEventoFc().setVisible(false);
		}

		@Override
		public void windowOpened(WindowEvent e) {
			evento = new Evento();
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
			List<Evento> eventos = new LinkedList<>();

			if (evento.getId() == null) {
				Msg.avisoImprimiRegistroNaoCadastrado();
				return;
			}
			if (eventos.add(EventoFac.getRegistro(evento))) {
				EventoRel eventoRel = new EventoRel(eventos);
				eventoRel.retornarRelatorio(true);
			}

		}
	}

	public class Novo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			List<TipoEvento> tipoEventoList = (List<TipoEvento>) TipoEventoFac.getRegistro();
			Collections.sort(tipoEventoList, new TipoEventoComp().new Nome());

			getEventoPc().getTipoEventoGui().removeAllItems();
			getEventoPc().getTipoEventoGui().addItem(new TipoEvento());

			for (TipoEvento v : tipoEventoList) {
				getEventoPc().getTipoEventoGui().addItem(v);
			}

			evento = new Evento();
			getEventoFc().limparGui();
			getEventoPc().getGuiDescricao().requestFocus();
		}
	}

	public class Pesquisa implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			atualizarObjeto();
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getEventoPp().pesquisarRegistro(evento);
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getEventoFp());
				getEventoFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Registro implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			long totalPesquisaRegistro = 0;
			totalPesquisaRegistro = getEventoPp().pesquisarRegistro(new Evento());
			Msg.avisoRegistroEncontrado(totalPesquisaRegistro);

			if (totalPesquisaRegistro > 0) {
				MainControl.mostrarFrame(getEventoFp());
				getEventoFp().setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
		}
	}

	public class Relatorio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			List<Evento> eventos = new LinkedList<>();

			try {
				eventos = new LinkedList<>(EventoFac.pesquisarRegistro(new Evento()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			EventoRel eventoRel = new EventoRel(eventos);
			eventoRel.retornarRelatorio(true);

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
				String placa = getEventoPc().getGuiDescricao().getText();
				if (placa == null || placa.length() == 0) {
					getEventoPc().getGuiDescricao().requestFocus();
					Msg.avisoCampoObrigatorio("Data");
					return;
				}

				if (mensagem == JOptionPane.YES_OPTION) {
					atualizarObjeto();
					EventoFac.salvarRegistro(evento);
					evento = new Evento();
					MainControl.getAgendaEventoFc().limparGui();
					getEventoPc().getGuiDescricao().requestFocus();
					Msg.sucessoSalvarRegistro();
				}
			} catch (Exception e) {
				Msg.erroSalvarRegistro();
			}
		}
	}

	private static Evento evento;

	public void atualizarGui() {
		if (evento == null) {
			return;
		}
		getEventoPc().getTipoEventoGui().setSelectedItem(evento.getTipoEvento());
		getEventoPc().getDataGui().setText(evento.getData());
		getEventoPc().getGuiDescricao().setText(evento.getDescricao());
		getEventoPc().getGuiHoraInicio().setText(evento.getHoraInicio());
		getEventoPc().getGuiHoraTermino().setText(evento.getHoraTermino());
	}

	public void atualizarObjeto() {
		evento.setDescricao(getEventoPc().getGuiDescricao().getText());
		evento.setHoraTermino(getEventoPc().getGuiHoraTermino().getText());
		evento.setHoraInicio(getEventoPc().getGuiHoraInicio().getText());
		evento.setData(getEventoPc().getDataGui().getText());
		evento.setTipoEvento((TipoEvento) getEventoPc().getTipoEventoGui().getSelectedItem());
	}

	public Evento getEvento() {
		return evento;
	}

	public EventoFc getEventoFc() {
		return MainControl.getAgendaEventoFc();
	}

	public EventoFp getEventoFp() {
		return MainControl.getAgendaEventoFp();
	}

	public EventoPc getEventoPc() {
		return MainControl.getAgendaEventoFc().getEventoPc();
	}

	public EventoPp getEventoPp() {
		return MainControl.getAgendaEventoFp().getEventoPp();
	}

	public void setEvento(Evento evento) {
		EventoControl.evento = evento;
	}
}
