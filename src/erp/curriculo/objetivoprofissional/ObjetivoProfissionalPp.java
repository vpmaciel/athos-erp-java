package erp.curriculo.objetivoprofissional;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import arquitetura.gui.Tabela;

@SuppressWarnings("serial")
public final class ObjetivoProfissionalPp extends JPanel {

	List<ObjetivoProfissional> objetivoProfissionals = null;
	private final ObjetivoProfissionalTm objetivoProfissionalTm;
	private final JTable table;

	public ObjetivoProfissionalPp() {
		setBorder(BorderFactory.createTitledBorder("CURSO"));

		objetivoProfissionals = new LinkedList<>();
		objetivoProfissionalTm = new ObjetivoProfissionalTm(objetivoProfissionals);

		table = new JTable();
		table.setModel(objetivoProfissionalTm);
		for (int c = 0; c < table.getColumnCount(); ++c) {
			table.setDefaultRenderer(table.getColumnClass(c), Tabela.getDefaultTableCellRenderer());
		}
		Tabela.configurarLarguraColunasTabela(table, ObjetivoProfissionalTm.largura);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.setRowSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);
		table.setColumnSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(new Rectangle(0, 0, 720, 420));
		scrollPane.setViewportView(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		super.setLayout(new BorderLayout());
		super.add(scrollPane, "Center");
	}

	public void atualizarGui(List<ObjetivoProfissional> objetivoProfissionals) {
		objetivoProfissionalTm.setObjetivoProfissionalList(objetivoProfissionals);
		objetivoProfissionalTm.fireTableDataChanged();
	}

	public ObjetivoProfissionalTm getCaracteristicaTableModel() {
		return objetivoProfissionalTm;
	}

	public void iniciarControlador() {
		ObjetivoProfissionalSel listener = new ObjetivoProfissionalSel(table);
		table.getSelectionModel().addListSelectionListener(listener);
	}

	public int pesquisarRegistroCaracteristica(ObjetivoProfissional ObjetivoProfissional) {
		objetivoProfissionals = new LinkedList<>();
		try {
			objetivoProfissionals = new LinkedList<>(ObjetivoProfissionalFac.pesquisarRegistro(ObjetivoProfissional));
		} catch (Exception e) {
			System.out.println(e);
		}
		atualizarGui(objetivoProfissionals);
		return objetivoProfissionals.size();
	}
}