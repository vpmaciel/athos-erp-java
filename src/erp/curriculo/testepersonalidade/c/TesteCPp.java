package erp.curriculo.testepersonalidade.c;

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
public final class TesteCPp extends JPanel {

	private final JTable table;
	List<TesteC> testeCs = null;
	private final TesteCTm testeCTm;

	public TesteCPp() {
		setBorder(BorderFactory.createTitledBorder("TESTE DE PERSONALIDADE D.I.S.C."));

		testeCs = new LinkedList<>();
		testeCTm = new TesteCTm(testeCs);

		table = new JTable();
		table.setModel(testeCTm);
		for (int c = 0; c < table.getColumnCount(); ++c) {
			table.setDefaultRenderer(table.getColumnClass(c), Tabela.getDefaultTableCellRenderer());
		}
		Tabela.configurarLarguraColunasTabela(table, TesteCTm.largura);
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

	public void atualizarGui(List<TesteC> testeCs) {
		testeCTm.settesteCList(testeCs);
		testeCTm.fireTableDataChanged();
	}

	public TesteCTm getTesteCTableModel() {
		return testeCTm;
	}

	public void iniciarControlador() {
		TesteCSel listener = new TesteCSel(table);
		table.getSelectionModel().addListSelectionListener(listener);
	}

	public int pesquisarRegistro(TesteC testeC) {
		testeCs = new LinkedList<>();
		try {
			testeCs = new LinkedList<>(TesteCFac.pesquisarRegistro(testeC));
		} catch (Exception e) {
			System.out.println(e);
		}
		atualizarGui(testeCs);
		return testeCs.size();
	}
}
