package erp.funcionario;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import arquitetura.gui.Tabela;

@SuppressWarnings("serial")
public final class PanelPesquisaFuncionario extends JPanel {

	private final FuncionarioTableModel agenciaTableModel;
	List<Funcionario> funcionarioList = null;
	private final JTable table;

	public PanelPesquisaFuncionario() {
		funcionarioList = new LinkedList<>();
		agenciaTableModel = new FuncionarioTableModel(funcionarioList);

		table = new JTable();
		table.setModel(agenciaTableModel);
		for (int c = 0; c < table.getColumnCount(); ++c) {
			table.setDefaultRenderer(table.getColumnClass(c), Tabela.getDefaultTableCellRenderer());
		}
		Tabela.configurarLarguraColunasTabela(table, FuncionarioTableModel.WIDTH);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.RIGHT);
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

	public void atualizarGui(List<Funcionario> funcionarios) {
		agenciaTableModel.setFuncionarioList(funcionarios);
		agenciaTableModel.fireTableDataChanged();
	}

	public FuncionarioTableModel getFuncionarioTableModel() {
		return agenciaTableModel;
	}

	public void iniciarHandle() {
		SelectionListener listener = new SelectionListener(table);
		table.getSelectionModel().addListSelectionListener(listener);
	}

	public int pesquisarRegistroFuncionario(Funcionario funcionario) {
		funcionarioList = new LinkedList<>();
		try {
			funcionarioList = new LinkedList<>(FuncionarioDaoFacade.pesquisarRegistro(funcionario));
		} catch (Exception e) {
			System.out.println(e);
		}
		atualizarGui(funcionarioList);
		return funcionarioList.size();
	}
}
